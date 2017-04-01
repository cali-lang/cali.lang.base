![alt tag](cali.lang.base/docs/img/logo.png)

This is a base interpreter implementation of the Cali language. You can run the interpreter from the command line, but this package was built to be embedded.

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
TODO

## License
Cali is licensed under the Apache 2.0 license. See accompanying LICENSE file for details. Much thanks to the authors of the [CUP Parser Generator](http://www2.cs.tum.edu/projects/cup/install.php) and good people at Georgia Tech. See the java-cup.LICENSE.txt file for open source license details.

## TODO:
Probably quite a bit. Here's the current list.
* Implement security model.
