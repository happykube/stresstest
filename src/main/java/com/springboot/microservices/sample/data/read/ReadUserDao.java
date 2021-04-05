package com.springboot.microservices.sample.data.read;

/*
 * Data(Persistent) Layer: UserDao
 */

import java.util.List;

import com.springboot.microservices.sample.model.User;

public interface ReadUserDao {
	
	/**
	 * 사용자 전체 정보 가져오기 
	 * @return
	 * @throws Exception
	 */
	List<User> selectUser() throws Exception;	
	
	/**
	 * 아이디로 사용자 정보 조회하
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User selectUserById(String userId) throws Exception;	
	
}
			