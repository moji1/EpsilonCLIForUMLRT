# EpsilonCLIForUMLRT

This  is a standalone version of Epsilon which is configured to run EOL script for UMLRT.
You may extend it for UML modles with certain profiles.

## Dependencies
Maven


## Build
cd UMLRTTransformer
mvn clean package 

## Run
java -jar target/UMLRTTransformer-1.0-SNAPSHOT-jar-with-dependencies.jar

```
Usage: UMLRTTransformer [options]
  Options:
    -args             Args for the script, (Optional)
    -helermodel, -hm  Path of Helpers models and their aliases e.g., hm1:ha1,hm2:ha2, (Optional) (default: [])
    -language, -l     The language of the script (Optional, default is EOL) (default: EOL)
    -outplace         In outplace transformation mode, a new output model is created, default mode is outplace, (Optional) (default: true)
  * -script, -s       Path of epsilon script
  * -umlrtmodel, -m   Path of UMLRTModel models and their alias e.g., m1:a1
  ```
