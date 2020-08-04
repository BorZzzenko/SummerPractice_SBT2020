package com.team3.collections.Database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component
public class CollectionsDataOperator {
    private final MongoCollection<Document> collection;

    public CollectionsDataOperator(@Value("${mongodb.host}") String host,
                              @Value("${mongodb.port}") int port,
                              @Value("${mongodb.databaseName}")  String databaseName,
                              @Value("${mongodb.collectionName}")  String collectionName) {

        // Connection to MongoDb
        MongoClient mongoClient = new MongoClient( host, port );
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection(collectionName);
    }

    /**
     * Creates mongoDB entry
     */
    public void insertJson(Document doc) {
        collection.insertOne(doc);
    }

    public Document findCollection(String collectionId) {
        Document found = collection.find(eq("collection_id", collectionId)).first();

        if (found != null) {
            found.remove("_id");
        }

        return found;
    }

    /**
     * Delete collection by ID
     * @param collectionId id of collection
     */
    public void deleteCollection(String collectionId) {
        collection.deleteOne(eq("collection_id", collectionId));
    }
}
