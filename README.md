# Getting Started
This demo presents how to make a SpringBoot2 microservice with Google gRPC server, and a MongoDB database backend.
The service defines a model with an Employee and Department relationship. It will initialize the MongoDB with
some sample records and then those can be queried via gRPC service via a gRPC client. 

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
- Intellij Or Eclipse to run the client.

## Database browser 
optional

http://robot3t.com/en/

#Technologies used
````
- Java 8
- Springboot2
- Springboot gRPC plugin
- Java MongoDB driver
- Springboot MongoDB data
- Lombok
- Maven
- Gradle
- Robo3T
````
## Compilation
 - mvn clean package
 - gradle clean build

## Running the service
````
java -jar target/grpcmongodbms-0.1-SNAPSHOT.jar

The service will run on localhost 9090 by default, or some other configured port via application.properties
"grpc.port" property.
````

IDE
====
````
For debugging, or running the gRPC client.
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
    	<parent>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-parent</artifactId>
    		<version>2.2.1.RELEASE</version>
    		<relativePath/> <!-- lookup parent from repository -->
    	</parent>
    	<groupId>grpc.demo</groupId>
    	<artifactId>grpcmongodbms</artifactId>
    	<version>0.1-SNAPSHOT</version>
    	<name>grpcmongodbspringbootmicroservice</name>
    	<description>Demo project to show to make a springboot microservice with GRPC and MongoDB</description>
    
    	<properties>
    		<java.version>1.8</java.version>
    		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    		<spring.version>3.2.1.RELEASE</spring.version>
    		<grpc.version>1.27.1</grpc.version>
    		<os.plugin.version>1.6.2</os.plugin.version>
    		<protobuf.plugin.version>0.6.1</protobuf.plugin.version>
    		<protoc.version>3.5.1</protoc.version>
    	</properties>
    
    	<dependencies>
    		<dependency>
    			<groupId>org.projectlombok</groupId>
    			<artifactId>lombok</artifactId>
    			<version>1.18.10</version>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-data-mongodb</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.mongodb</groupId>
    			<artifactId>mongo-java-driver</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>io.grpc</groupId>
    			<artifactId>grpc-netty-shaded</artifactId>
    			<version>1.27.1</version>
    		</dependency>
    		<dependency>
    			<groupId>io.grpc</groupId>
    			<artifactId>grpc-protobuf</artifactId>
    			<version>1.27.1</version>
    		</dependency>
    		<dependency>
    			<groupId>io.grpc</groupId>
    			<artifactId>grpc-stub</artifactId>
    			<version>1.27.1</version>
    		</dependency>
    		<dependency>
    			<groupId>javax.annotation</groupId>
    			<artifactId>javax.annotation-api</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.xolstice.maven.plugins</groupId>
    			<artifactId>protobuf-maven-plugin</artifactId>
    			<version>0.6.1</version>
    			<type>pom</type>
    		</dependency>
    
    	</dependencies>
    	<build>
    		<extensions>
    			<extension>
    				<groupId>kr.motd.maven</groupId>
    				<artifactId>os-maven-plugin</artifactId>
    				<version>${os.plugin.version}</version>
    			</extension>
    		</extensions>
    		<plugins>
    			<plugin>
    				<groupId>org.apache.maven.plugins</groupId>
    				<artifactId>maven-compiler-plugin</artifactId>
    				<configuration>
    					<source>8</source>
    					<target>8</target>
    				</configuration>
    			</plugin>
    			<plugin>
    				<groupId>org.springframework.boot</groupId>
    				<artifactId>spring-boot-maven-plugin</artifactId>
    			</plugin>
    			<plugin>
    				<groupId>kr.motd.maven</groupId>
    				<artifactId>os-maven-plugin</artifactId>
    				<version>${os.plugin.version}</version>
    			</plugin>
    			<plugin>
    				<groupId>org.xolstice.maven.plugins</groupId>
    				<artifactId>protobuf-maven-plugin</artifactId>
    				<version>${protobuf.plugin.version}</version>
    				<configuration>
    					<protocArtifact>com.google.protobuf:protoc:${protoc.version}:exe:${os.detected.classifier}</protocArtifact>
    					<pluginId>grpc-java</pluginId>
    					<pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
    				</configuration>
    				<executions>
    					<execution>
    						<goals>
    							<goal>compile-custom</goal>
    							<goal>compile</goal>
    						</goals>
    					</execution>
    				</executions>
    			</plugin>
    		</plugins>
    	</build>
    

```
#### gRPC client and server dependencies

```xml

    <dependency>
    	<groupId>io.grpc</groupId>
    	<artifactId>grpc-netty-shaded</artifactId>
    	<version>1.27.1</version>
    </dependency>
    <dependency>
    	<groupId>io.grpc</groupId>
    	<artifactId>grpc-protobuf</artifactId>
    	<version>1.27.1</version>
    </dependency>
    <dependency>
    	<groupId>io.grpc</groupId>
    	<artifactId>grpc-stub</artifactId>
    	<version>1.27.1</version>
    /dependency>

```
#### gRPC  Protobuf compiler dependencies

```xml

<dependency>
    <groupId>org.xolstice.maven.plugins</groupId>
	<artifactId>protobuf-maven-plugin</artifactId>
	<version>0.6.1</version>
	<type>pom</type>
</dependency>

``` 

#### Protobuf compilation setup

```xml
    <build>
		<extensions>
			<extension>
				<groupId>kr.motd.maven</groupId>
				<artifactId>os-maven-plugin</artifactId>
				<version>${os.plugin.version}</version>
			</extension>
		</extensions>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>8</source>
					<target>8</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>kr.motd.maven</groupId>
				<artifactId>os-maven-plugin</artifactId>
				<version>${os.plugin.version}</version>
			</plugin>
			<plugin>
				<groupId>org.xolstice.maven.plugins</groupId>
				<artifactId>protobuf-maven-plugin</artifactId>
				<version>${protobuf.plugin.version}</version>
				<configuration>
					<protocArtifact>com.google.protobuf:protoc:${protoc.version}:exe:${os.detected.classifier}</protocArtifact>
					<pluginId>grpc-java</pluginId>
					<pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>compile-custom</goal>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>

```
## gRPC setup

Following steps are required to setup a gRPC server and client

- Define Protobuf
- Implement the service
- Implement the server
- Implement the client
 
## Protobuf Definition

Protobufs can be defined in a single or multiple files either directly under src/main/ or some in some folder under /src/main.
The Protobuf definition files should have .proto extension.

```shell script

syntax = "proto3";
package grpc.demo;
//This structure defines the gRPC service
//Generally there should be Request and Response objects defined to encapsulate complex structure.
service EmployeeService {
  rpc findEmployeeById(FindByIdRequest) returns (FindEmployeeResponse);
  rpc findEmployeeByDepartmentId(FindByIdRequest) returns (FindEmployeeResponse);
  rpc findAllEmployees(Empty) returns (FindEmployeeResponse);
  rpc findAllDepartments(Empty) returns (FindDepartmentResponse);
  rpc findEmployeeByName(FindByNameRequest) returns (FindEmployeeResponse);
  rpc findDepartmentByName(FindByNameRequest) returns (FindDepartmentResponse);
}

//This structure is used to pass an empty request for functions like getAllEmployees
message Empty {
}

//Request structure to call a service which returns data by id.
message FindByIdRequest{
  string guid = 1;
}

//Request structure to call service which returns data by name.
message FindByNameRequest{
  string name = 1;
}

//Response structure to return employee protobuf data as a List
message FindEmployeeResponse{
  repeated Employee employee = 1;
}

//Response structure to return department protobuf data as a List
message FindDepartmentResponse{
  repeated Department department = 1;
}

//Employee protobuf definition
message Employee {
  string guid = 1;
  string name = 2;
  string address = 3;
  Department department = 4;
}

//Department protobuf defintion
message Department {
  string guid = 1;
  string name = 2;
  string description = 3;
}
```

#### Service definition

```java
package grpc.demo.grpc;

import grpc.demo.EmployeeServiceGrpc;
import grpc.demo.Protos;
import grpc.demo.repository.DatabaseRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private DatabaseRepository databaseRepository;

    @Override
    public void findEmployeeById(Protos.FindByIdRequest request, StreamObserver<Protos.FindEmployeeResponse> responseObserver) {
        try {
            Protos.FindEmployeeResponse response = Protos.FindEmployeeResponse.newBuilder()
                    .addEmployee(databaseRepository.findEmployeeById(request.getGuid())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void findEmployeeByDepartmentId(Protos.FindByIdRequest request, StreamObserver<Protos.FindEmployeeResponse> responseObserver) {
        try {
            Protos.FindEmployeeResponse response = Protos.FindEmployeeResponse.newBuilder()
                    .addAllEmployee(databaseRepository.findEmployeeByDepartmentId(request.getGuid())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void findAllEmployees(Protos.Empty request, StreamObserver<Protos.FindEmployeeResponse> responseObserver) {
        try {
            Protos.FindEmployeeResponse response = Protos.FindEmployeeResponse.newBuilder()
                    .addAllEmployee(databaseRepository.findAllEmployees()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void findAllDepartments(Protos.Empty request, StreamObserver<Protos.FindDepartmentResponse> responseObserver) {
        try {
            Protos.FindDepartmentResponse response = Protos.FindDepartmentResponse.newBuilder()
                    .addAllDepartment(databaseRepository.findAllDepartments()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void findEmployeeByName(Protos.FindByNameRequest request, StreamObserver<Protos.FindEmployeeResponse> responseObserver) {
        try {
            Protos.FindEmployeeResponse response = Protos.FindEmployeeResponse.newBuilder()
                    .addEmployee(databaseRepository.findEmployeeByName(request.getName())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void findDepartmentByName(Protos.FindByNameRequest request, StreamObserver<Protos.FindDepartmentResponse> responseObserver) {
        try {
            Protos.FindDepartmentResponse response = Protos.FindDepartmentResponse.newBuilder()
                    .addDepartment(databaseRepository.findDepartmentByName(request.getName())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch(Exception e){
            responseObserver.onError(e);
        }
    }
}
```

#### gRPC server definition

```java
package grpc.demo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class EmployeeServiceGrpcServer {
    @Autowired
    private Environment environment;

    @Autowired
    private EmployeeService employeeService;

    @PostConstruct
    private void runGrpcServer() throws Exception{
        Server server = ServerBuilder
                .forPort(Integer.parseInt(environment.getProperty("grpc.port")))
                .addService(employeeService).build();

        server.start();
        server.awaitTermination();
    }


}
```

### gRPC client definition

```java
package grpc.demo.grpc;

import grpc.demo.EmployeeServiceGrpc;
import grpc.demo.Protos;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class EmployeeServiceClient {
    public static void main(String[] args){
        EmployeeServiceClient client = new EmployeeServiceClient();
        List<Protos.Department> departments = client.findAllDepartments();
        List<Protos.Employee> employees = client.findAllEmployees();
        System.out.println(">>> All Departments");
        departments.forEach(department -> {
            System.out.println(department.getName());
            System.out.println(department.getDescription());
            System.out.println(department.getGuid());
            System.out.println("-------------------------------------");
        });

        System.out.println(">>> All Employees");
        employees.forEach(employee -> {
            System.out.println(employee.getName());
            System.out.println(employee.getAddress());
            System.out.println(employee.getGuid());
            System.out.println(employee.getDepartment().getName());
            System.out.println("-------------------------------------");
        });

        Protos.Employee employee = client.findEmployeeByName("Test 1");
        employee = client.findEmployeeById(employee.getGuid());

        System.out.println(">>> Employee By Id & Name");

        System.out.println(employee.getName());
        System.out.println(employee.getAddress());
        System.out.println(employee.getGuid());
        System.out.println(employee.getDepartment().getName());
        System.out.println("-------------------------------------");

        Protos.Department department = client.findDepartmentByName("Automation");

        System.out.println(">>> Department by Name");

        System.out.println(department.getDescription());
        System.out.println(department.getGuid());
        System.out.println("-------------------------------------");

        System.out.println(">>> All Employees By Department");
        employees = client.findEmployeeByDepartment(department.getGuid());
        employees.forEach(emp -> {
            System.out.println(emp.getName());
            System.out.println(emp.getAddress());
            System.out.println(emp.getGuid());
            System.out.println(emp.getDepartment().getName());
            System.out.println("-------------------------------------");
        });

        client.shutDown();
    }

    private ManagedChannel channel;
    private   EmployeeServiceGrpc.EmployeeServiceBlockingStub client;

    public EmployeeServiceClient(){
        channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        client = EmployeeServiceGrpc.newBlockingStub(channel);
    }

    public List<Protos.Employee> findAllEmployees(){
        Protos.FindEmployeeResponse findEmployeeResponse = client.findAllEmployees(Protos.Empty.newBuilder().build());
        return findEmployeeResponse.getEmployeeList();
    }

    public List<Protos.Department> findAllDepartments(){
        Protos.FindDepartmentResponse findDepartmentResponse = client.findAllDepartments(Protos.Empty.newBuilder().build());
        return findDepartmentResponse.getDepartmentList();
    }

    public Protos.Employee findEmployeeById(String id){
        Protos.FindByIdRequest findByIdRequest = Protos.FindByIdRequest.newBuilder().setGuid(id).build();
        List<Protos.Employee> employeeList = client.findEmployeeById(findByIdRequest).getEmployeeList();
        if(employeeList != null && employeeList.size() > 0){
            return employeeList.get(0);
        }
        return null;
    }

    public Protos.Employee findEmployeeByName(String name){
        Protos.FindByNameRequest findByNameRequest = Protos.FindByNameRequest.newBuilder().setName(name).build();
        List<Protos.Employee> employeeList = client.findEmployeeByName(findByNameRequest).getEmployeeList();
        if(employeeList != null && employeeList.size() > 0){
            return employeeList.get(0);
        }
        return null;
    }

    public Protos.Department findDepartmentByName(String name) {
        Protos.FindByNameRequest findByNameRequest = Protos.FindByNameRequest.newBuilder().setName(name).build();
        List<Protos.Department> departmentList = client.findDepartmentByName(findByNameRequest).getDepartmentList();
        if (departmentList != null && departmentList.size() > 0) {
            return departmentList.get(0);
        }
        return null;
    }

    public List<Protos.Employee> findEmployeeByDepartment(String id){
        Protos.FindByIdRequest findByIdRequest = Protos.FindByIdRequest.newBuilder().setGuid(id).build();
        return client.findEmployeeByDepartmentId(findByIdRequest).getEmployeeList();
    }

    public void shutDown(){
        channel.shutdown();
    }


}
```

## Mongo Config

Define the following properties in the application.properties

```properties
spring.data.mongodb.database=demo
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
```
Add following dependencies in the POM file.
```xml
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-mongodb</artifactId>
	</dependency>
	<dependency>
		<groupId>org.mongodb</groupId>
		<artifactId>mongo-java-driver</artifactId>
	</dependency>

```

```java
package grpc.demo.repository;

import grpc.demo.Protos;
import grpc.demo.model.DepartmentEntity;
import grpc.demo.model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseRepository {

    /**
     * Setting up the Springboot Mongodb starter and setting up the properties as defined in the application.properties
     * will auto define the MongoTemplate bean, which can be used to query the database.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Protos.Employee> findAllEmployees(){
        return convertEmployees(mongoTemplate.findAll(EmployeeEntity.class));
    }

    public List<Protos.Department> findAllDepartments(){
        return convertDepartments(mongoTemplate.find(new Query(), DepartmentEntity.class));
    }

    public Protos.Employee findEmployeeById(String id){
        return convertEmployee(mongoTemplate.find(new Query(Criteria.where("guid").is(id)), EmployeeEntity.class).stream().findFirst().orElse(null));
    }

    public List<Protos.Employee> findEmployeeByDepartmentId(String id){
        return convertEmployees(mongoTemplate.find(new Query(Criteria.where("department").is(id)), EmployeeEntity.class));
    }

    public Protos.Department findDepartmentById(String id){
        return convertDepartment(mongoTemplate.find(new Query(Criteria.where("guid").is(id)), DepartmentEntity.class).stream().findFirst().orElse(null));
    }

    public Protos.Department findDepartmentByName(String name){
        return convertDepartment(mongoTemplate.find(new Query(Criteria.where("name").is(name)), DepartmentEntity.class).stream().findFirst().orElse(null));
    }

    public Protos.Employee findEmployeeByName(String name){
        return convertEmployee(mongoTemplate.find(new Query(Criteria.where("name").is(name)), EmployeeEntity.class).stream().findFirst().orElse(null));
    }

    public void cleanDb(){
        mongoTemplate.remove(new Query(), EmployeeEntity.class);
        mongoTemplate.remove(new Query(), DepartmentEntity.class);
    }

    public void addDepartments(List<DepartmentEntity> departmentList){
        departmentList.forEach(department -> {mongoTemplate.save(department);});
    }

    public void addEmployees(List<EmployeeEntity> employeeList){
        employeeList.forEach(employee -> {mongoTemplate.save(employee);});
    }

    public Protos.Employee convertEmployee(EmployeeEntity employee) {
        if (employee != null) {
            Protos.Employee emp = Protos.Employee.newBuilder()
                    .setDepartment(findDepartmentById(employee.getDepartment()))
                    .setAddress(employee.getAddress())
                    .setGuid(employee.getGuid())
                    .setName(employee.getName())
                    .build();
            return emp;
        }
        return null;
    }

    public Protos.Department convertDepartment(DepartmentEntity department){
        if(department != null) {
            Protos.Department dept = Protos.Department.newBuilder()
                    .setName(department.getName())
                    .setGuid(department.getGuid())
                    .setDescription(department.getDescription())
                    .build();
            return dept;
        }
        return null;
    }

    public List<Protos.Employee> convertEmployees(List<EmployeeEntity> employees){
        List<Protos.Employee> result = new ArrayList<>();
        if(employees != null){
            employees.forEach(employee -> { result.add(convertEmployee(employee));});
        }
        return result;
    }

    public List<Protos.Department> convertDepartments(List<DepartmentEntity> departments){
        List<Protos.Department> result = new ArrayList<>();
        if(departments != null){
            departments.forEach(department -> { result.add(convertDepartment(department));});
        }
        return result;
    }

    @PostConstruct
    public void setupDB(){
       cleanDb();
        DepartmentEntity department = new DepartmentEntity();
        department.setName("Automation");
        department.setDescription("Test Automation");
        List<DepartmentEntity> departmentList = new ArrayList<>();
        departmentList.add(department);
        addDepartments(departmentList);

        List<EmployeeEntity> employeeList = new ArrayList<>();

        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("Test 1");
        employee.setAddress("Austin, TX");
        employee.setDepartment(department.getGuid());
        employeeList.add(employee);

        employee = new EmployeeEntity();
        employee.setName("Test 2");
        employee.setAddress("Dallas, TX");
        employee.setDepartment(department.getGuid());
        employeeList.add(employee);
        addEmployees(employeeList);

        findAllEmployees();
    }
}

```


## Properties

```properties
#gRPC server port
grpc.port=9090
#MongoDB database name
spring.data.mongodb.database=demo
#Database port
spring.data.mongodb.port=27017
#Database host
spring.data.mongodb.host=localhost

```

## Running the gRPC client

- Run the jar via command line or the springboot project via an IDE.
- Run the client from the IDE.

