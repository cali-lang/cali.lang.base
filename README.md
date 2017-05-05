![alt tag](cali.lang.base/docs/img/logo.png)

This is a base interpreter implementation of the Cali language. You can run the interpreter from the command line, but this package was built to be embedded.

## [Wiki/Docs](https://github.com/cali-lang/cali.lang.base/wiki)
This README.md is just the high level of building/embedding. For details on building and Cali lang itself [checkout the Wiki](https://github.com/cali-lang/cali.lang.base/wiki).


## Building
Requires ANT and a recent JDK. (1.6 or newer)

```
shell> ant
```

That's it. In the dist directory, you should now have a .jar and a couple license files.

You can run the tests from the dist directory:
```
java -jar cali-0.8a.jar tests/interpreter.ca
```


## Embedding

Embedding starts with first cloning and building Cali (See Above). Once you have the Cali .jar, include it in your project like any other .jar.

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
code in memory. Below is an example of adding a code file called test.ca with an 
enum in it.

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

## License
Cali is licensed under the Apache 2.0 license. See accompanying LICENSE file for details. Much thanks to the authors of the [CUP Parser Generator](http://www2.cs.tum.edu/projects/cup/install.php) and good people at Georgia Tech. See the java-cup.LICENSE.txt file for open source license details.

## TODO:
Probably quite a bit. Here's the current list.
* Implement security model.
