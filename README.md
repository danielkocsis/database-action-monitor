# Database Action Monitor

This is a Spring based Java application that monitors and reports certain events in a connected RDBMS.

## How to build the application?

You can build the application via calling

1. Clone the application from GitHub via running `git clone https://github.com/danielkocsis/database-action-monitor.git` from a local directory
2. Go to the source directory of the application via `cd database-action-monitor`
3. Build the application via calling`mvn clean install`


## How to run the application?

1. Run the following command from the home directory after you build the application

   `java -jar target/database-action-monitor-0.0.1-SNAPSHOT.jar `

## How to use the application?

1. Open a browser and go to URL: `localhost:8080` and click on the `Connect` button
2. Insert some data, but there are multiple ways to do that:
    1.  Through Swagger UI: go to URL `http://localhost:8080/swagger-ui.html`
    2.  Through H2 Console UI: go to URL: `http://localhost:8080/h2-console`
    3.  Send a POST request to `http://localhost:8080/api with JSON content` 
        ```
        {
          "name": "string",
          "new": true
        }
        ```

## Docker support

The application has Docker support, in order to run the application along with MySQL please run the following commands from the home directory:

1. `mvn clean deploy`
2. `docker-compose up`