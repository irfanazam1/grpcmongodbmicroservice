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
