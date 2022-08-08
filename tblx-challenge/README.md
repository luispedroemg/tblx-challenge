# TBLXChallenge
Solution proposal for the challenge using kotlin with javalin and a mongodb database

### Build
Required:
 - Maven (https://maven.apache.org/)

```
mvn clean package
```
This should output two ```.jar``` files in ```target``` folder:
- ```tblx-challenge-1.0.0.jar``` - Does not include all dependencies and does not contain a Manifest
- ```tblx-challenge-1.0.0-shaded.jar``` - Directly executable in a JVM

And also a ```.properties``` file that contains the api version



### Run
Required:
 - MongoDB (https://www.mongodb.com/)
 - Java 8 (https://www.java.com/en/)
```
Usage: java -jar tblx-challenge-<version>-shaded.jar options_list
Subcommands:
    load - Load data from CSV
    run - Start API Server

Options:
    --dbConnString, -db -> MongoDB Connection string (always required) { String }
    --propertiesFilePath, -properties -> tblx-challenge .properties file { String }
    --help, -h -> Usage info
```

Example for loading data:
 1. Build the project
 2. ```
    cd target
    java -jar .\tblx-challenge-1.0.0-shaded.jar load -db "mongodb+srv://<user>:<password>@cluster0.mfr5t.mongodb.net/?retryWrites=true&w=majority" --filePath .\ext-resources\dublin-dataset\siri.20121108.csv
    ```
The Loader only works with this data set: https://codechallengestracc.blob.core.windows.net/code-challenge/dublin-dataset.zip

Example for running the api server:
```
cd target
java -jar .\tblx-challenge-1.0.0-shaded.jar run -p 8000 -db "mongodb+srv://<user>:<password>@cluster0.mfr5t.mongodb.net/?retryWrites=true&w=majority"
```

### Use the API
Get raw data entries given a timeframe (large timeframes may exceed time limits and/or memory limits)
```
GET http://localhost:8000/api/v1/entry?timeframe[start]=2012-11-07T00:00:00Z&timeframe[end]=2012-11-07T00:01:00Z
```

Get operator vehicle id list given a timeframe
```
GET http://localhost:8000/api/v1/operator/D1/vehicle?timeframe[start]=2012-11-07T00:00:00Z&timeframe[end]=2012-11-07T23:01:00Z
```

Get vehicle position list given a timeframe
```
http://localhost:8000/api/v1/vehicle/33462/position?timeframe[start]=2012-11-07T00:00:00Z&timeframe[end]=2012-11-07T23:01:00Z
```