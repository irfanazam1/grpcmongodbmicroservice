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
