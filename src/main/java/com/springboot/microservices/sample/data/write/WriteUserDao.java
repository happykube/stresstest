package com.springboot.microservices.sample.data.write;

/*
 * Data(Persistent) Layer: UserDao
 */

import java.util.ArrayList;

import com.springboot.microservices.sample.model.UpdateUser;
import com.springboot.microservices.sample.model.User;

public interface WriteUserDao {
	/**
	 * 사용자 정보 변경
	 * @param sampleUser
	 * @return
	 * @throws Exception
	 */
	int updateUser(UpdateUser sampleUser) throws Exception;
	
	/**
	 * 사용자 등록하기 
	 * @param sampleUser
	 * @return
	 * @throws Exception
	 */
	int insertUser(User sampleUser) throws Exception;
	
	/**
	 * 사용자 정보 삭제하기 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int deleteUser(String userId) throws Exception;		
	
	/**
	 * 테스트 사용자 등록하기 
	 * @param ArrayList<User> users
	 * @return
	 * @throws Exception
	 */
	int createTestUsers(ArrayList<User> users) throws Exception;	

}
			