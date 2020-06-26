# Getting Started
This demo presents how to make a SpringBoot2 microservice with GraphQL query interface and a MongoDB database backend.
The service defines a model with an Employee and Department relationship. It will initialize the MongoDB with
some sample records and then those can be queried via GraphQL Playground. 

```java
@Data
@With
@RequiredArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    protected String guid;
    protected String name;
    protected String description;
}

@Data
@With
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    protected String guid;
    protected String name;
    protected String address;
    protected String department;
}

```

## Perquisites
Build tools, either of the following, installed and configured on the local machine.
- maven
- gradle

## Database
- MongoDB installed on the local or a remote machine accessible over the network.
https://www.mongodb.com/try/download/community

## Testing tool
- GraphQL playground
https://github.com/prisma-labs/graphql-playground/releases

## Database browser 
optional

http://robot3t.com/en/

#Technologies used
````
- Java 8
- Springboot2
- Springboot GraphQL WebMVC
- Java MongoDB driver
- Springboot MongoDB data
- Google Guava
- Lombok
- Maven
- Gradle
- Robo3T
````
## Compilation
 - mvn clean package
 - gradle clean build

## Running
````
java -jar target/graphqlmongoms-0.1-SNAPSHOT.jar

The service will run on localhost 8080 by default, or some other configured port via application.properties
"server.port" property. The query interface will be accessible via http://localhost:8080/graphql
````

IDE
====
````
Optional - for debugging or extended the application.
 - IntelliJ
 - Eclipse
````
## Maven POM explained

- Same is true for gradle
```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	<!-- Springboot2 Parent POM -->
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.2.1.RELEASE</version>
    		<relativePath/>
    	</parent>
    	<!-- Demo project artifact setup -->
    	<groupId>graphql.demo</groupId>
    	<artifactId>graphqlmongodbms</artifactId>
    	<version>0.1-SNAPSHOT</version>
    	<name>graphqlmongodbmicroservice</name>
    	<description>Demo project to show to make a springboot microservice with GraphQL and MongoDB</description>
    	<properties>
    		<java.version>1.8</java.version>
    		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    		<spring.version>3.2.1.RELEASE</spring.version>
    	</properties>
    	<dependencies>
    	    <!-- Springboot starter; will launch microservice running in Tomcat -->
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-web</artifactId>
    		</dependency>
    		<!-- Lombok data project -->
    		<dependency>
    			<groupId>org.projectlombok</groupId>
    			<artifactId>lombok</artifactId>
    			<version>1.18.10</version>
    		</dependency>
    		<!-- GraphQL Spring WebMVC; this will make the service to setup GraphQL -->
    		<dependency>
    			<groupId>com.graphql-java</groupId>
    			<artifactId>graphql-java-spring-boot-starter-webmvc</artifactId>
    			<version>1.0</version>
    		</dependency>
    		<dependency>
    			<groupId>com.graphql-java</groupId>
    			<artifactId>graphql-java</artifactId>
    			<version>11.0</version>
    		</dependency>
    		<!-- Springboot MongoDB and Java driver -->
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-data-mongodb</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.mongodb</groupId>
    			<artifactId>mongo-java-driver</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>com.google.guava</groupId>
    			<artifactId>guava</artifactId>
    			<version>26.0-jre</version>
    		</dependency>
    	</dependencies>
    	<build>
    		<plugins>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    			</plugin>
    		</plugins>
    	</build>
    </project>
```

 
## Java Code

The following section describes the java code.

## Mongo Config
```java
@Configuration
@EnableMongoRepositories(basePackages = { "graphql.demo", "graphql.demo.repository"  })
public class MongoConfig  {

    @Value("${db.host}")
    private String databaseHost;

    @Value("${db.port}")
    private int databasePort;

    @Value("${db.name}")
    private String databaseName;

    @Bean(name="mongoTemplate")
    public MongoDbTemplate createTemplate(){
        return new MongoDbTemplate(databaseHost,databasePort,databaseName);
    }
}

public class MongoDbTemplate implements MongoOperations, IndexOperationsProvider, ApplicationContextAware {
 //See the class file for implementation .. this is a long java file.
}

```
## GraphQL setup

Following steps are required to setup GraphQL

- Define Schema
- Define Data Provider
- Define Data Fectcher
 
## Schema Definition

Schema can be defined in a single or multiple files under src/main/resource with .graphqls extension

#### Query
- Query structure defines the query interface or the API contract. 
- It is like <api-name><(args)>:<return type>
- For returning a List use [Type] 

#### Type
- The type structure defines a type/model or the data contract.

```genericsql
type Query {
  findEmployeeById(guid: ID): Employee
  findEmployeeByDepartment(department:String): [Employee]
  findAllEmployees: [Employee]
  findAllDepartments: [Department]
  findEmployeeByName(name:String): Employee
  findDepartmentByName(name:String): Department
}

type Employee {
  guid: ID
  name: String
  address: String
  department: Department
}

type Department {
  guid: ID
  name: String
  description: String
}
``` 
## Data Provider

- It is used to build the schema from the resource file to java code and link it to the data fetchers.

```java
@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
    
    //Loads and builds the schema
    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }
    //Wires the schema to the fetchers.
    //There is separate data fetcher for each api defined in the Query
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("findEmployeeById", graphQLDataFetchers.findEmployeeById())
                        .dataFetcher("findAllEmployees", graphQLDataFetchers.findAllEmployees())
                        .dataFetcher("findAllDepartments", graphQLDataFetchers.findAllDepartments())
                        .dataFetcher("findEmployeeByDepartment", graphQLDataFetchers.findEmployeeByDepartment())
                        .dataFetcher("findEmployeeByName", graphQLDataFetchers.findEmployeeByName())
                        .dataFetcher("findDepartmentByName", graphQLDataFetchers.findDepartmentByName()))
                //A data fetcher can be linked directly to a type defined in the schema.
                //This data fetcher can be used to either load a single type or all of the types.
                .type(newTypeWiring("Employee")
                        .dataFetcher("department", graphQLDataFetchers.getDepartment()))
                .build();
    }
}

```

## Data Fetchers

- Data fetchers are used to grab data from a data source. The service uses mongodb but it can be anything, a file, db, in-memory cache, or call to another service.

```java
@Component
public class GraphQLDataFetchers {
    @Autowired
    private DatabaseRepository databaseRepository;

    public DataFetcher findAllEmployees(){
        return dataFetchingEnvironment -> {
            return databaseRepository.findAllEmployees();
        };
    }

    public DataFetcher findEmployeeById(){
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("guid");
            return databaseRepository.findEmployeeById(id);
        };
    }

    public  DataFetcher findEmployeeByDepartment(){
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("department");
            return databaseRepository.findEmployeeByDepartmentId (id);
        };
    }

    public DataFetcher findAllDepartments(){
        return dataFetchingEnvironment -> {
            return databaseRepository.findAllDepartments();
        };
    }

    public DataFetcher findDepartmentByName(){
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return databaseRepository.findDepartmentByName(name);
        };
    }

    public DataFetcher findEmployeeByName(){
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return databaseRepository.findEmployeeByName(name);
        };
    }


    public DataFetcher getDepartment(){
        return dataFetchingEnvironment -> {
            Employee employee = dataFetchingEnvironment.getSource();
            return databaseRepository.findDepartmentById(employee.getDepartment());
        };
    }
}
```

## Properties

```properties
#Database name
db.name=demo
#Database host address. Localhost if the MongoDB is installed locally.
db.host=localhost
#Default mongoport. Change the port if MongoDB is not installed on the default port.
db.port=27017
#DB base package
db.base=graphql
#Tomcat port. Change the port if some other service is running locally on port 8080
server.port=8080
```

## Running Queries (GraphQL Playground)

- Run the service like, java -jar target/graphqlmongoms-0.1-SNAPSHOT.jar or directly loading the project in 
some IDE like IntelliJ.

- Open GraphQL Playground and connect with URL http://localhost:8080
- Note: the guid's or the record ids will be different during each session as they are auto generated.

#### Query to find an Employee By Name

```json
    {
      findEmployeeByName(name:"Test 1"){
        guid
        name
        address
        department{
          name
          description
          guid
        }
      }
    }

Result:

    {
      "data": {
        "findEmployeeByName": {
          "guid": "5ef380082d20207bd1492c41",
          "name": "Test 1",
          "address": "Austin, TX",
          "department": {
            "name": "Automation",
            "description": "Test Automation",
            "guid": "5ef380082d20207bd1492c40"
          }
        }
      }
    }

``` 

#### Query to find out Employees by Department Id

- ** Use the department id from the previous result.
```json
    {
      findEmployeeByDepartment(department: "5ef380082d20207bd1492c40"){
        guid
        name
      	address
        department{
          name
          description
        }
      }
    }

Result:

    {
      "data": {
        "findEmployeeByDepartment": [
          {
            "guid": "5ef380082d20207bd1492c41",
            "name": "Test 1",
            "address": "Austin, TX",
            "department": {
              "name": "Automation",
              "description": "Test Automation"
            }
          },
          {
            "guid": "5ef380082d20207bd1492c42",
            "name": "Test 2",
            "address": "Dallas, TX",
            "department": {
              "name": "Automation",
              "description": "Test Automation"
            }
          }
        ]
      }
    }
```

#### Query to find an Employee by Id

- ** Use the employee id guid from any of the previous results.

```json
    {
      findEmployeeById(guid: "5ef380082d20207bd1492c42"){
        guid
        name
        address
        department{
          name
        }
      }
    }

Result:

    {
      "data": {
        "findEmployeeById": {
          "guid": "5ef380082d20207bd1492c42",
          "name": "Test 2",
          "address": "Dallas, TX",
          "department": {
            "name": "Automation"
          }
        }
      }
    }
```

#### Some other queries

```json
    {
      findDepartmentByName(name:"Automation"){
          name
          description
          guid
        
      }
    }

    {
      findAllEmployees{
        guid
        name
        address
        department{
          name
        }
      }
    }
    
    
    {
      findAllDepartments{
        guid
        name
      	description
      }
    }

```