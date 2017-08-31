![alt tag](cali.lang.base/docs/img/logo.png)

This is a base interpreter implementation of the Cali language. You can run the interpreter from the command line, but this package was built to be embedded and provides only the most common language and standard library functionality.

## [Wiki/Docs](https://github.com/cali-lang/cali.lang.base/wiki)
This README.md is just the high level of building/embedding. For details on building and Cali lang itself [checkout the Wiki](https://github.com/cali-lang/cali.lang.base/wiki).


## Embedding

The easiest way to embed Cali interpreter is to add it as a Maven dependency. For the most recent version [search for cali-lang in Maven Central](https://search.maven.org/#search%7Cga%7C1%7Ccali-lang).

```
<dependency>
    <groupId>com.cali-lang</groupId>
    <artifactId>cali.lang.base</artifactId>
    <version>1.0.0</version>
</dependency>
```

Alternately you can just download the JAR from Maven or you can [clone this repository and build (See Below)](#building). Once you have the Cali JAR, include it in your project like any other JAR.

Here are the most basic steps in creating a new Cali Engine object, parsing a source code file, and then running it.
```
import com.cali.Engine;
import com.cali.ast.caliException;

...

// Create new Cali-Lang Engine.
Engine eng = new Engine();

// Parse Cali code file.
eng.parseFile("cali-src/test.ca");

// Run the program. This will start execution in the main function.
eng.run();
```

Currently the easiest way to add code to the interpreter to be available
to be included from Cali code is by using the Lang singleton object that holds
code in memory. Below is an example of adding a code file called test.ca with an enum in it.

```
import com.cali.stdlib.Lang;

...

Lang.get().langIncludes.put("test.ca", "enum tenum { one; two; three; }");
```

When working in Cali code with the above test.ca include set, you could do something like this. We use the include statement to actually parse the test.ca included source file. Then we can use the enum (tenum) if we like.

```
include test;
...
en_val = tenum.one;
...
```

A security manager implementation has been added to control security at the Engine level. The security manager is defined in the SecurityManagerInt interface. When the default Engine constructor is called, a SecurityManagerImpl which implements SecurityManagerInt is instantiated and added to the Engine as the default manager. In order to provide your own implementation of the security manager just implement SecurityManagerImpl or extend SecurityManagerInt and customize as you like. Then provide an instance of the new object in the constructor of the engine.

```
import com.cali.SecurityManagerImpl;
...

public class MySecurityManager extends SecurityManagerImpl {
  public MySecurityManager() {
    // Add your custom security manager properties here ...
    this.props.put("dir.current.read", true);
    this.props.put("dir.current.write", false);
    this.props.put("remote.log.write", true);
  }
  // And override functions here if you like ...
}

...

// Create an instance of my security manager.
MySecurityManager mySecMan = new MySecurityManager();

// Create new Cali-Lang Engine with our custom security manager.
Engine eng = new Engine(mySecMan);
```

## Building
Requires Maven and a recent JDK. (1.6 or newer)

```
> cd cali.lang.base/
> mvn clean package
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building cali-lang.jar 1.0.0
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ cali.lang.base ---

...


[INFO] --- maven-assembly-plugin:2.4.1:single (make-assembly) @ cali.lang.base ---
[INFO] Building jar: /home/austin/git/cali.lang.base/cali.lang.base/target/cali.lang.base-1.0.0-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6.812 s
[INFO] Finished at: 2017-08-28T17:21:11-07:00
[INFO] Final Memory: 24M/294M
[INFO] ------------------------------------------------------------------------
```

That's it. In the target directory, you should now have an executable JAR file
named cali.lang.base-1.0.0-jar-with-dependencies.jar.

You can run the tests like this:
```
> java -jar target/cali.lang.base-1.0.0-jar-with-dependencies.jar tests/interpreter.ca
Running Test [ Cali-Lang Interpreter ]:
 *** (local assignment) Assign variable null. ... PASSED
 *** (local assignment) Assign variable bool. ... PASSED
 *** (local assignment) Assign variable int. ... PASSED

...


*** (lang secman) Security manager get map of values. ... PASSED
 *** (lang secman) Security manager instantiate. ... PASSED
 *** (lang secman) Security manager set property. ... PASSED
 *** (lang secman) Security manager set map. ... PASSED

TOTAL: 319 RAN: 319 PASSED: 319
Elapsed: 0.293s
```

## License
Cali is licensed under the Apache 2.0 license. See accompanying LICENSE file for details.

## Credits

Much thanks to the authors of the [CUP Parser Generator](http://www2.cs.tum.edu/projects/cup/install.php) and good people at Georgia Tech. Cali base interpreter also relies upon [json-simple - https://code.google.com/archive/p/json-simple/](https://code.google.com/archive/p/json-simple/).
