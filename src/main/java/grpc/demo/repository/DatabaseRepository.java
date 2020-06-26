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
