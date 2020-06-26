package grpc.demo.mongodb;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexOperationsProvider;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.CloseableIterator;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class MongoDbTemplate implements MongoOperations, IndexOperationsProvider, ApplicationContextAware {

    private MongoTemplate proxiedTemplate;
    private String databaseHost;
    private int databasePort;
    private String databaseName;

    private MongoDbTemplate() {

    }

    public MongoDbTemplate(String databaseHost, int databasePort, String databaseName) {
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseName = databaseName;
        this.proxiedTemplate = createMongoTemplate(databaseName,databaseHost,databasePort);
    }

    public void switchTemplate(String databaseHost, int databasePort, String databaseName) {
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseName = databaseName;
        this.proxiedTemplate = createMongoTemplate(databaseName,databaseHost,databasePort);
    }



    public MongoTemplate getProxiedTemplate() {
        return proxiedTemplate;
    }

    public void setProxiedTemplate(MongoTemplate proxiedTemplate) {
        this.proxiedTemplate = proxiedTemplate;
    }

    @Override
    public SessionScoped withSession(Supplier<ClientSession> sessionProvider) {
        return proxiedTemplate.withSession(sessionProvider);
    }

    @Override
    public <T> List<T> findDistinct(String field, Class<?> entityClass, Class<T> resultClass) {
        return proxiedTemplate.findDistinct(field, entityClass, resultClass);
    }

    @Override
    public <T> List<T> findDistinct(Query query, String field, String collection, Class<T> resultClass) {
        return proxiedTemplate.findDistinct(query, field, collection, resultClass);
    }

    @Override
    public <T> T findAndReplace(Query query, T replacement) {
        return proxiedTemplate.findAndReplace(query, replacement);
    }

    @Override
    public <T> T findAndReplace(Query query, T replacement, String collectionName) {
        return proxiedTemplate.findAndReplace(query, replacement, collectionName);
    }

    @Override
    public <T> T findAndReplace(Query query, T replacement, FindAndReplaceOptions options) {
        return proxiedTemplate.findAndReplace(query, replacement, options);
    }

    @Override
    public <T> T findAndReplace(Query query, T replacement, FindAndReplaceOptions options, String collectionName) {
        return proxiedTemplate.findAndReplace(query, replacement, options, collectionName);
    }

    @Override
    public <T> T findAndReplace(Query query, T replacement, FindAndReplaceOptions options, Class<T> entityType, String collectionName) {
        return proxiedTemplate.findAndReplace(query, replacement, options, entityType, collectionName);
    }

    @Override
    public <S, T> T findAndReplace(Query query, S replacement, FindAndReplaceOptions options, Class<S> entityType, Class<T> resultType) {
        return proxiedTemplate.findAndReplace(query, replacement, options, entityType, resultType);
    }

    @Override
    public String getCollectionName(Class<?> aClass) {
        return proxiedTemplate.getCollectionName(aClass);
    }

    @Override
    public Document executeCommand(String s) {
        return proxiedTemplate.executeCommand(s);
    }

    @Override
    public Document executeCommand(Document document) {
        return proxiedTemplate.executeCommand(document);
    }

    @Override
    public Document executeCommand(Document document, ReadPreference readPreference) {
        return proxiedTemplate.executeCommand(document, readPreference);
    }

    @Override
    public void executeQuery(Query query, String s, DocumentCallbackHandler documentCallbackHandler) {
        proxiedTemplate.executeQuery(query, s, documentCallbackHandler);
    }

    @Override
    public <T> T execute(DbCallback<T> dbCallback) {
        return proxiedTemplate.execute(dbCallback);
    }

    @Override
    public <T> T execute(Class<?> aClass, CollectionCallback<T> collectionCallback) {
        return proxiedTemplate.execute(aClass, collectionCallback);
    }

    @Override
    public <T> T execute(String s, CollectionCallback<T> collectionCallback) {
        return proxiedTemplate.execute(s, collectionCallback);
    }

    @Override
    public SessionScoped withSession(ClientSessionOptions clientSessionOptions) {
        return proxiedTemplate.withSession(clientSessionOptions);
    }

    @Override
    public MongoOperations withSession(ClientSession clientSession) {
        return proxiedTemplate.withSession(clientSession);
    }

    @Override
    public <T> CloseableIterator<T> stream(Query query, Class<T> aClass) {
        return proxiedTemplate.stream(query, aClass);
    }

    @Override
    public <T> CloseableIterator<T> stream(Query query, Class<T> aClass, String s) {
        return proxiedTemplate.stream(query, aClass, s);
    }

    @Override
    public <T> MongoCollection<Document> createCollection(Class<T> aClass) {
        return proxiedTemplate.createCollection(aClass);
    }

    @Override
    public <T> MongoCollection<Document> createCollection(Class<T> aClass, CollectionOptions collectionOptions) {
        return proxiedTemplate.createCollection(aClass, collectionOptions);
    }

    @Override
    public MongoCollection<Document> createCollection(String s) {
        return proxiedTemplate.createCollection(s);
    }

    @Override
    public MongoCollection<Document> createCollection(String s, CollectionOptions collectionOptions) {
        return proxiedTemplate.createCollection(s, collectionOptions);
    }

    @Override
    public Set<String> getCollectionNames() {
        return proxiedTemplate.getCollectionNames();
    }

    @Override
    public MongoCollection<Document> getCollection(String s) {
        return proxiedTemplate.getCollection(s);
    }

    @Override
    public <T> boolean collectionExists(Class<T> aClass) {
        return proxiedTemplate.collectionExists(aClass);
    }

    @Override
    public boolean collectionExists(String s) {
        return proxiedTemplate.collectionExists(s);
    }

    @Override
    public <T> void dropCollection(Class<T> aClass) {
        proxiedTemplate.dropCollection(aClass);
    }

    @Override
    public void dropCollection(String s) {
        proxiedTemplate.dropCollection(s);
    }

    @Override
    public IndexOperations indexOps(String s) {
        return proxiedTemplate.indexOps(s);
    }

    @Override
    public IndexOperations indexOps(Class<?> aClass) {
        return proxiedTemplate.indexOps(aClass);
    }

    @Override
    public ScriptOperations scriptOps() {
        return proxiedTemplate.scriptOps();
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode bulkMode, String s) {
        return proxiedTemplate.bulkOps(bulkMode, s);
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode bulkMode, Class<?> aClass) {
        return proxiedTemplate.bulkOps(bulkMode, aClass);
    }

    @Override
    public BulkOperations bulkOps(BulkOperations.BulkMode bulkMode, Class<?> aClass, String s) {
        return proxiedTemplate.bulkOps(bulkMode, aClass, s);
    }

    @Override
    public <T> List<T> findAll(Class<T> aClass) {
        return proxiedTemplate.findAll(aClass);
    }

    @Override
    public <T> List<T> findAll(Class<T> aClass, String s) {
        return proxiedTemplate.findAll(aClass, s);
    }

    @Override
    public <T> GroupByResults<T> group(String s, GroupBy groupBy, Class<T> aClass) {
        return proxiedTemplate.group(s, groupBy, aClass);
    }

    @Override
    public <T> GroupByResults<T> group(Criteria criteria, String s, GroupBy groupBy, Class<T> aClass) {
        return proxiedTemplate.group(criteria, s, groupBy, aClass);
    }

    @Override
    public <O> AggregationResults<O> aggregate(TypedAggregation<?> typedAggregation, String s, Class<O> aClass) {
        return proxiedTemplate.aggregate(typedAggregation, s, aClass);
    }

    @Override
    public <O> AggregationResults<O> aggregate(TypedAggregation<?> typedAggregation, Class<O> aClass) {
        return proxiedTemplate.aggregate(typedAggregation, aClass);
    }

    @Override
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<?> aClass, Class<O> aClass1) {
        return proxiedTemplate.aggregate(aggregation, aClass, aClass1);
    }

    @Override
    public <O> AggregationResults<O> aggregate(Aggregation aggregation, String s, Class<O> aClass) {
        return proxiedTemplate.aggregate(aggregation, s, aClass);
    }

    @Override
    public <O> CloseableIterator<O> aggregateStream(TypedAggregation<?> typedAggregation, String s, Class<O> aClass) {
        return proxiedTemplate.aggregateStream(typedAggregation, s, aClass);
    }

    @Override
    public <O> CloseableIterator<O> aggregateStream(TypedAggregation<?> typedAggregation, Class<O> aClass) {
        return proxiedTemplate.aggregateStream(typedAggregation, aClass);
    }

    @Override
    public <O> CloseableIterator<O> aggregateStream(Aggregation aggregation, Class<?> aClass, Class<O> aClass1) {
        return proxiedTemplate.aggregateStream(aggregation, aClass, aClass1);
    }

    @Override
    public <O> CloseableIterator<O> aggregateStream(Aggregation aggregation, String s, Class<O> aClass) {
        return proxiedTemplate.aggregateStream(aggregation, s, aClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(String s, String s1, String s2, Class<T> aClass) {
        return proxiedTemplate.mapReduce(s, s1, s2, aClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(String s, String s1, String s2, MapReduceOptions mapReduceOptions, Class<T> aClass) {
        return proxiedTemplate.mapReduce(s, s1, s2, mapReduceOptions, aClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String s, String s1, String s2, Class<T> aClass) {
        return proxiedTemplate.mapReduce(query, s, s1, s2, aClass);
    }

    @Override
    public <T> MapReduceResults<T> mapReduce(Query query, String s, String s1, String s2, MapReduceOptions mapReduceOptions, Class<T> aClass) {
        return proxiedTemplate.mapReduce(query, s, s1, s2, mapReduceOptions, aClass);
    }

    @Override
    public <T> GeoResults<T> geoNear(NearQuery nearQuery, Class<T> aClass) {
        return proxiedTemplate.geoNear(nearQuery, aClass);
    }

    @Override
    public <T> GeoResults<T> geoNear(NearQuery nearQuery, Class<T> aClass, String s) {
        return proxiedTemplate.geoNear(nearQuery, aClass, s);
    }

    @Override
    public <T> T findOne(Query query, Class<T> aClass) {
        return proxiedTemplate.findOne(query, aClass);
    }

    @Override
    public <T> T findOne(Query query, Class<T> aClass, String s) {
        return proxiedTemplate.findOne(query, aClass, s);
    }

    @Override
    public boolean exists(Query query, String s) {
        return proxiedTemplate.exists(query, s);
    }

    @Override
    public boolean exists(Query query, Class<?> aClass) {
        return proxiedTemplate.exists(query, aClass);
    }

    @Override
    public boolean exists(Query query, Class<?> aClass, String s) {
        return proxiedTemplate.exists(query, aClass, s);
    }

    @Override
    public <T> List<T> find(Query query, Class<T> aClass) {
        return proxiedTemplate.find(query, aClass);
    }

    @Override
    public <T> List<T> find(Query query, Class<T> aClass, String s) {
        return proxiedTemplate.find(query, aClass, s);
    }

    @Override
    public <T> T findById(Object o, Class<T> aClass) {
        return proxiedTemplate.findById(o, aClass);
    }

    @Override
    public <T> T findById(Object o, Class<T> aClass, String s) {
        return proxiedTemplate.findById(o, aClass, s);
    }

    @Override
    public <T> List<T> findDistinct(Query query, String s, Class<?> aClass, Class<T> aClass1) {
        return proxiedTemplate.findDistinct(query,s,aClass,aClass1);
    }

    @Override
    public <T> List<T> findDistinct(Query query, String s, String s1, Class<?> aClass, Class<T> aClass1) {
        return proxiedTemplate.findDistinct(query,s,s1,aClass,aClass1);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, Class<T> aClass) {

        return proxiedTemplate.findAndModify(query,update,aClass);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, Class<T> aClass, String s) {

        return proxiedTemplate.findAndModify(query,update,aClass,s);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> aClass, String s) {
        return proxiedTemplate.findAndModify(query,update,findAndModifyOptions,aClass,s);
    }

    @Override
    public <S, T> T findAndReplace(Query query, S s, FindAndReplaceOptions findAndReplaceOptions, Class<S> aClass, String s1, Class<T> aClass1) {
        return proxiedTemplate.findAndReplace(query,s,findAndReplaceOptions,aClass,s1,aClass1);
    }

    @Override
    public <T> T findAndRemove(Query query, Class<T> aClass) {

        return proxiedTemplate.findAndRemove(query,aClass);
    }

    @Override
    public <T> T findAndRemove(Query query, Class<T> aClass, String s) {

        return proxiedTemplate.findAndRemove(query, aClass, s);
    }

    @Override
    public long count(Query query, Class<?> aClass) {

        return proxiedTemplate.count(query, aClass);
    }

    @Override
    public long count(Query query, String s) {

        return proxiedTemplate.count(query, s);
    }

    @Override
    public long count(Query query, Class<?> aClass, String s) {

        return proxiedTemplate.count(query, aClass);
    }

    @Override
    public <T> T insert(T t) {

        return proxiedTemplate.insert(t);
    }

    @Override
    public <T> T insert(T t, String s) {

        return proxiedTemplate.insert(t, s);
    }

    @Override
    public <T> Collection<T> insert(Collection<? extends T> collection, Class<?> aClass) {

        return proxiedTemplate.insert(collection,aClass);
    }

    @Override
    public <T> Collection<T> insert(Collection<? extends T> collection, String s) {

        return proxiedTemplate.insert(collection, s);
    }

    @Override
    public <T> Collection<T> insertAll(Collection<? extends T> collection) {

        return proxiedTemplate.insertAll(collection);
    }

    @Override
    public <T> T save(T t) {

        return proxiedTemplate.save(t);
    }

    @Override
    public <T> T save(T t, String s) {

        return proxiedTemplate.save(t, s);
    }

    @Override
    public UpdateResult upsert(Query query, Update update, Class<?> aClass) {

        return proxiedTemplate.upsert(query, update, aClass);
    }

    @Override
    public UpdateResult upsert(Query query, Update update, String s) {

        return proxiedTemplate.upsert(query, update, s);
    }

    @Override
    public UpdateResult upsert(Query query, Update update, Class<?> aClass, String s) {

        return proxiedTemplate.upsert(query, update, aClass, s);
    }

    @Override
    public UpdateResult updateFirst(Query query, Update update, Class<?> aClass) {

        return proxiedTemplate.updateFirst(query, update, aClass);
    }

    @Override
    public UpdateResult updateFirst(Query query, Update update, String s) {

        return proxiedTemplate.updateFirst(query, update, s);
    }

    @Override
    public UpdateResult updateFirst(Query query, Update update, Class<?> aClass, String s) {

        return proxiedTemplate.updateFirst(query, update, aClass, s);
    }

    @Override
    public UpdateResult updateMulti(Query query, Update update, Class<?> aClass) {

        return proxiedTemplate.updateMulti(query, update, aClass);
    }

    @Override
    public UpdateResult updateMulti(Query query, Update update, String s) {

        return proxiedTemplate.updateMulti(query, update, s);
    }

    @Override
    public UpdateResult updateMulti(Query query, Update update, Class<?> aClass, String s) {

        return proxiedTemplate.updateMulti(query, update, aClass, s);
    }

    @Override
    public DeleteResult remove(Object o) {

        return proxiedTemplate.remove(o);
    }

    @Override
    public DeleteResult remove(Object o, String s) {

        return proxiedTemplate.remove(o, s);
    }

    @Override
    public DeleteResult remove(Query query, Class<?> aClass) {

        return proxiedTemplate.remove(query, aClass);
    }

    @Override
    public DeleteResult remove(Query query, Class<?> aClass, String s) {

        return proxiedTemplate.remove(query, aClass, s);
    }

    @Override
    public DeleteResult remove(Query query, String s) {

        return proxiedTemplate.remove(query, s);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, String s) {

        return proxiedTemplate.findAllAndRemove(query, s);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, Class<T> aClass) {

        return proxiedTemplate.findAllAndRemove(query, aClass);
    }

    @Override
    public <T> List<T> findAllAndRemove(Query query, Class<T> aClass, String s) {

        return proxiedTemplate.findAllAndRemove(query, aClass, s);
    }

    @Override
    public MongoConverter getConverter() {

        return proxiedTemplate.getConverter();
    }

    @Override
    public <T> ExecutableFind<T> query(Class<T> aClass) {

        return proxiedTemplate.query(aClass);
    }

    @Override
    public <T> ExecutableAggregation<T> aggregateAndReturn(Class<T> aClass) {

        return proxiedTemplate.aggregateAndReturn(aClass);
    }

    @Override
    public <T> ExecutableInsert<T> insert(Class<T> aClass) {

        return proxiedTemplate.insert(aClass);
    }

    @Override
    public <T> MapReduceWithMapFunction<T> mapReduce(Class<T> aClass) {

        return proxiedTemplate.mapReduce(aClass);
    }

    @Override
    public <T> ExecutableRemove<T> remove(Class<T> aClass) {

        return proxiedTemplate.remove(aClass);
    }

    @Override
    public <T> ExecutableUpdate<T> update(Class<T> aClass) {

        return proxiedTemplate.update(aClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        proxiedTemplate.setApplicationContext(applicationContext);
    }

    private MongoClient mongoClient(String mongoHost, int mongoPort) {
        return  new MongoClient(mongoHost, mongoPort);
    }

    private MongoDbFactory mongoDbFactory(String mongoDBName, String mongoDBHost, int mongoDBPort) {
        return new SimpleMongoDbFactory(mongoClient(mongoDBHost,mongoDBPort), mongoDBName);
    }

    private MongoTemplate createMongoTemplate(String mongoDbName, String mongoDbHost, int mongoDbPort){
        return  new MongoTemplate(mongoDbFactory(mongoDbName,mongoDbHost,mongoDbPort));
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}