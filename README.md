# Microkernel Library App
## Second project for IFBA's INF008 OOP class. 

## Setup and execution

### Build the project

```bash
make install

// or

mvn install
```

### Execute the project

```bash
make init

// or

mvn exec:java -pl app

// you can also build and execute with

make clean-install
```

## New plugin development instructions:

1. Create your plugin directory in "plugins"
2. Add you new plugin submodule in the main pom.xml:

    <modules>
        <module>interfaces</module>
        <module>app</module>
        <module>plugins/borrowed-books-summary</module>
        <module>plugins/late-return-fee</module>
    </modules>
    
3. Create your new plugin's pom.xml
4. Remember to use plugin's package conventions:

    br/edu/ifba/inf008/plugins/<YourPluginNameInCamelCase>.java
    
5. Build and execute the project.
