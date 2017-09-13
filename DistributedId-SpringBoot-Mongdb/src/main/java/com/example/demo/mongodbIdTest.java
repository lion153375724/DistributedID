package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.mongodb.MyBean;
import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class mongodbIdTest {
	
	private static final Logger logger = LoggerFactory.getLogger(mongodbIdTest.class);
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private MongoTemplate template;
	
	@Autowired
	private MyBean myBean;
	
	@Test
	public void mongodbIdTest(){
		Customer customer=new Customer("lxdxil2","dd");
	    customer=customerRepository.save(customer);
	    logger.info( "mongodbId:"+customer.getId()); //mongodb的objectid
	}
	
	@Test
	public void mongodbIdFindTest(){
	    List<Customer> list = customerRepository.findAll();
	    for(Customer customer : list){
	    	logger.info("{},{},{}",customer.getId(),customer.getFirstName(),customer.getLastName());
	    }
	}
	
	@Test
	public void mongodbIdFindTest3(){
		DBCursor cursor = template.getCollection("customer").find();
		MongoCursor<Customer> cursor2;
        Customer customer = new Customer();
        Gson gson = new Gson();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			logger.info("xxxx" + obj);
			logger.info("zzzz" + obj.get("_id"));
			customer = gson.fromJson(obj.toString(), Customer.class);
			logger.info("aaaa{},{},{}",customer.getId(),customer.getFirstName(),customer.getLastName());
		}
	    
	}
	
	@Test
	public void mongodbIdFindTest2(){
		myBean.example();
	}
}
