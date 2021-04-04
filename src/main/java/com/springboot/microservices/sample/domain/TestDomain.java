package com.springboot.microservices.sample.domain;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
 * Domain(Business) Layer: TestDomain
 */

import com.springboot.microservices.sample.dao.UserDao;
import com.springboot.microservices.sample.model.User;

public class TestDomain {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao sampleUserDao;
	
	public ResponseEntity <String > createTestUsers(int startUserId, int userCount) throws Exception { 
		log.info("***** Start creating Test users "+userCount+"명");
		
		ArrayList<User> list = new ArrayList<User>();

		for(int i=0; i < userCount-startUserId + 1; i++) {
			User sampleUser = User.builder()
					.userId("user"+String.format("%08d", startUserId+i))
					.userNm("유저"+String.format("%08d", startUserId+i))
					.addr("")
					.cellPhone(String.format("%08d", startUserId+i))
					.birthDt(String.format("%08d", startUserId+i))
					.agreeInfo("")
					.build();
			
			list.add(sampleUser);
		}
		log.info("Added User object in list==>"+list.size());
		
		sampleUserDao.createTestUsers(list);
			
		log.info("***** End creating Test users "+userCount+"명");
		
		return new ResponseEntity<String> ("1", HttpStatus.OK);
	}
}
