package es.alfred.kmanager.core.db.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import es.alfred.kmanager.core.resources.TheResources
import java.util.concurrent.TimeUnit

/**
 * @author Alfredo Sanz
 * @time 2025
 */
object MongoConn {

    private lateinit var mongoClient: MongoClient

    fun getClient(): MongoClient {
        if (!this::mongoClient.isInitialized) {
             mongoClient = MongoClient.create(
                MongoClientSettings.builder()
                    .applyConnectionString(ConnectionString(TheResources.getResources().mongo.connection))
                    .applyToConnectionPoolSettings{ builder ->
                        builder
                            .maxWaitTime(5, TimeUnit.SECONDS)
                            .maxSize(200)
                    }
                    .build()
            )
        }
        return this.mongoClient
    }
}