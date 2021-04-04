package com.springboot.microservices.sample.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservices.sample.dao.UserDao;
import com.springboot.microservices.sample.model.UpdateUser;
import com.springboot.microservices.sample.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Api(value="User API")
@RestController
public class UserController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao sampleUserDao;
	
	@GetMapping("/users")	
	@ApiOperation(value="사용자 정보 가져오기", notes="사용자 정보를 제공합니다. ")
	public ResponseEntity <List<User>> getUserList() { 
		
		List<User> list = null;
		try {
			log.info("Start db select");
			list = sampleUserDao.selectUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("user counts :"+list.size());
		
		return new ResponseEntity<List<User>> (list, HttpStatus.OK);
	}
	
	
	@GetMapping("/users/{userId}")
	@ApiOperation(value="아이디로 사용자 정보 가져오기 ")
	public ResponseEntity <User> getUsserById(
				@PathVariable (name="userId", required = true) String userId
			) { 
		User re = null;
		try {
			log.info("Start db select");
			re = sampleUserDao.selectUserById(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User> (re, HttpStatus.OK);
	}
	
	@PutMapping("/users/{userId}")
	@ApiOperation(value="사용자 정보 변경하기 ")
	public ResponseEntity <String > setUserUpdate(
			@PathVariable(name="userId",required = true ) String userId, 
			@RequestBody UpdateUser sampleUser
		) throws Exception { 
		
		log.info("Start db update==>"+userId);
		sampleUser.setUserId(userId);
		int re  = sampleUserDao.updateUser(sampleUser);
		log.debug("result :"+ re);
		
		return new ResponseEntity<String> (re+"", HttpStatus.OK);
	}
	
	@PostMapping("/users")
	@ApiOperation(value="사용자 정보 등록하기 ")
	public ResponseEntity <String > setUserInsert(
			@RequestBody User sampleUser
		) throws Exception { 
		
		log.info("Start db insert");
		int re  = sampleUserDao.insertUser(sampleUser);
		log.debug("result :"+ re);
		
		return new ResponseEntity<String> (re+"", HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{userId}")
	@ApiOperation(value="사용자 정보 삭제하기 ")
	public ResponseEntity <String > setUserDelete(
			@PathVariable(name="userId",required = true ) String userId
		) throws Exception { 
		
		log.info("Start db insert");
		int re  = sampleUserDao.deleteUser(userId);
		log.debug("result :"+ re);
		
		return new ResponseEntity<String> (re+"", HttpStatus.OK);
	}

	@GetMapping("/createtestusers/{startUserId}/{userCount}")
	@ApiOperation(value="테스트 사용자를 userCount명 등록하기 ")
	public ResponseEntity <String > createTestUsers(
			@PathVariable (name="startUserId", required = true) int startUserId,
			@PathVariable (name="userCount", required = true) int userCount
		) throws Exception { 
		
		log.info("***** Start creating Test users "+userCount+"명");
		
		ArrayList<User> list = new ArrayList<User>();

		for(int i=0; i < userCount-startUserId + 1; i++) {
			User sampleUser = User.builder()
					.userId("user"+String.format("%05d", startUserId+i))
					.userNm("유저"+String.format("%05d", startUserId+i))
					.addr("")
					.cellPhone(String.format("%05d", startUserId+i))
					.birthDt(String.format("%05d", startUserId+i))
					.agreeInfo("")
					.build();
			
			list.add(sampleUser);
		}
		log.info("Added User object in list");

		for(User sampleUser : list) {
			//log.info(">>>"+sampleUser.getUserNm());
			sampleUserDao.insertUser(sampleUser);
		}

		log.info("***** End creating Test users "+userCount+"명");
		
		return new ResponseEntity<String> ("1", HttpStatus.OK);
	}	
}