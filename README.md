# Data Analysis System
This is an implementation of a data analysis system that imports flat files, reads and analyses the data, and outputs a report with a summary of the data. The system runs the first analysis when the application starts running, and then it repeats the analysis every time a new input file is available, writing a new report each time.

### About the input data
The input data is read from the directory {home}/data/in, where {home} indicates the user's home directory (identified by %HOMEPATH% on Windows systems or by $HOME on Linux systems, for example).
Besides, the data must be stored in flat files with ```.dat``` extension and must follow a specific format, shown in the table below:

| Data kind | Format ID | Line format                                                  |
|-----------|-----------|--------------------------------------------------------------|
| Salesman  | 001       | 001çCPFçNameçSalary                                          |
| Customer  | 002       | 002çCNPJçNameçBusiness Area                                  |
| Sale      | 003       | 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name |

### About the output data
After all the data has been processed and analysed, an output file will be created in the directory {home}/data/out. The content of this file will be the amount of clients and salesmen in the input files, the ID of the most expensive sale, and the name of the worst salesman ever (the salesman with fewer sales revenue).    
The summary will have the layout shown below, and if there weren't any sales in the input files, the values of the most expensive sale and the worst salesman will be empty strings.
```
Client amountçSalesman amountçMost expensive sale IDçWorst salesman
```
The reports are written in files with ```.done.dat``` extension and with the date and time of when the file was generated as the filename.

## Running the application
### Prerequisites
Make sure that you have installed the following software before running the system:
- Java 8    
Installation instructions: ```sudo apt install openjdk-8-jdk```
- Gradle    
Installation instructions: https://gradle.org/install/
- Docker    
Installation instructions: https://docs.docker.com/engine/install/ubuntu/

### Starting the application
To start the system, simply execute the command ```./gradlew bootRun``` inside the directory of this repository.    
However, to manage the application more easily, the use of the Bash script [run_data_analyser.sh](run_data_analyser.sh) is recommended. The available arguments of this script are shown below:
```bash
./run_data_analyser.sh start    # Running the application inside a Docker container
./run_data_analyser.sh stop     # Stopping the container
./run_data_analyser.sh status   # Checking the status of the container and the application (RUNNING or NOT RUNNING)
```
Moreover, by default, the application health checker will be available at port 8080 of the host. In order to change the port, set the port value to the environment variable SERVER_PORT before starting the application.

### Other information
- In order to check if the system is running as expected, run the script [smoke_test_analyser.sh](smoke_test_analyser.sh). It creates a new input file and checks if a new report was generated.
- To check if your input files are in the correct path, you can send a request to the application's health check endpoint, as in the example below. The response body will display the input directory and the output directory that the application is using. However, if you are running the application inside a container, the paths displayed will be the paths where your volumes were mounted in the container.    
    ```bash
    curl http://localhost:$SERVER_PORT/health
    ```
- To run the Bash scripts, you must first give them executable permission. This can be done with the following command: ```chmod +x script_name.sh```