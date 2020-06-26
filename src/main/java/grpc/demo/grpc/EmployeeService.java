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
