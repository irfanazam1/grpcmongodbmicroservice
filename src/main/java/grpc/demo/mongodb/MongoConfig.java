package grpc.demo.mongodb;

import org.springframework.beans.factory.annotation.Value;

//@Component
//@EnableMongoRepositories(basePackages = { "grpc.demo", "grpc.demo.repository", "grpc.demo.grpc"  })
public class MongoConfig  {

    @Value("${db.host}")
    private String databaseHost;

    @Value("${db.port}")
    private int databasePort;

    @Value("${db.name}")
    private String databaseName;

    //@Bean(name="mongoTemplate")
    public MongoDbTemplate createTemplate(){
        return new MongoDbTemplate(databaseHost,databasePort,databaseName);
    }

}