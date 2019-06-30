# Transaction Analyser

## Design
The application is designed in a Model View Controller like pattern, the business logic is delegated from the Startup class to other classes like CSVTransactionParser and RelativeBalanceCalculator. 
The goal was to keep single reponsibility classes and structure methods to enable unit testing.

### To build project
mvn clean package

### To run project
mvn exec:java --quiet -Dexec.args="path_to_file"

### To only run tests
mvn test

## Limitations and assumptions
* All data provided in the CSV file is in the correct format, that includes dates in "dd/MM/yyyy HH:mm:ss" format
* Transactions are recorded in order

## TODO
* Test IOException(s) from BufferedReader in CSVTransactionParser (this should be easy since the method is already designed to enable this) 
* Create interface for the parser to enable more thorough testing of the main method (i.e IO exceptions) and extensibility (possibility of having more data formats)
* Add user friendly way to add "to" and "from" datetimes
* Wrap the java application in a bash script for a more user friendlier execution procedure
