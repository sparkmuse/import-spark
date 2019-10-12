# Import spark

This is a simple project that will import a large set of data from your file system into a mysql database.

## Requirements
- java 8
- maven

## Getting the data

The data comes from a data set provided by google located bellow:
https://developers.google.com/freebase/#freebase-deleted-triples

### Manual Download

Download the data from the link provided, ungzip and merge all individual files.

### Script
Run the following script to have all the steps above executed automatically
```shell script
chmod +x import.sh

import.sh
```

## Set up Application
### Configuration File
Update the path to the data file by updating it's absolute path in the file bellow

```shell script
src/java/resources/application.properties
```

The file path can also be configured using the command line
```shell script
java -Dapp.file.path=<PATH_TO_FILE>.csv -jar target/import-spark-1.0.0.jar

```
**NOTE:** We recommend to use a smaller set of the data for testing purposes before switching to all the data.

### Database
Update the configuration corresponding to your mysql in the file
```shell script
src/java/resources/application.properties
```

Create the sql table by running the following statement
```mysql
CREATE DATABASE IF NOT EXISTS DataHorizontal;

USE DataHorizontal;

CREATE TABLE IF NOT EXISTS `deletions`
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    `creationDateTime` timestamp DEFAULT NULL,
    `creator`          varchar(250),
    `deletionDateTime` timestamp DEFAULT NULL,
    `deletor`          varchar(250),
    `subject`          varchar(250),
    `predicate`        varchar(250),
    `object`           varchar(250),
    `languageCode`     varchar(250)
) ENGINE=InnoDB;
```

### Compilation
```shell script
mvn clean package
```

### Running
```shell script
java --jar target/import-spark-1.0.0.jar
```