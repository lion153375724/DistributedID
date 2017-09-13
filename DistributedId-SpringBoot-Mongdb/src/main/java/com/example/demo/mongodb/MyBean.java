package com.example.demo.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.example.demo.Customer;
import com.example.demo.mongodbIdTest;
import com.mongodb.DB;
import com.mongodb.DBCursor;

@Component
public class MyBean {
	private static final Logger logger = LoggerFactory.getLogger(mongodbIdTest.class);
	private final MongoDbFactory mongo;

    @Autowired
    public MyBean(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    // ...

    public void example() {
        DB db = mongo.getDb();
        DBCursor cursor = db.getCollection("customer").find();
        Customer customer = new Customer();
		while(cursor.hasNext()){
			logger.info("xxxx" + cursor.next().toString());
			customer = (Customer) cursor.next();
			logger.info("xxx{},{},{}",customer.getId(),customer.getFirstName(),customer.getLastName());
		}
    }


}
