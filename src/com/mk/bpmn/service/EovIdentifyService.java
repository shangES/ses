package com.mk.bpmn.service;

/**
 * Created by IntelliJ IDEA.
 * User: zhangyu
 * Date: 12-6-28
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.identity.Authentication;

public class EovIdentifyService implements IdentityService {
	public User newUser(String userId) {
		System.out.println("newUser");
		return null;
	}

	public void saveUser(User user) {
		System.out.println("saveUser");
	}

	public UserQuery createUserQuery() {
		System.out.println("createUserQuery");
		return null;
	}

	public void deleteUser(String userId) {
		System.out.println("deleteUser");
	}

	public Group newGroup(String group) {
		return null;
	}

	public GroupQuery createGroupQuery() {
		return null;
	}

	public void saveGroup(Group group) {
	}

	public void deleteGroup(String group) {
	}

	public void createMembership(String userId, String groupId) {

	}

	public void deleteMembership(String userId, String groupId) {

	}

	/**
	 * Checks if the password is valid for the given user.
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String userId, String password) {
		return false;
	}

	/**
	 * @param authenticatedUserId
	 */
	public void setAuthenticatedUserId(String authenticatedUserId) {
		Authentication.setAuthenticatedUserId(authenticatedUserId);
	}

	/**
	 * @param userId
	 * @param picture
	 */
	public void setUserPicture(String userId, Picture picture) {
	}

	public Picture getUserPicture(String userId) {
		return null;
	}

	/**
	 * @param userId
	 * @param key
	 * @param value
	 */
	public void setUserInfo(String userId, String key, String value) {
	}

	/**
	 * @param userId
	 * @param key
	 * @return
	 */
	public String getUserInfo(String userId, String key) {
		System.out.println("getUserInfo" + userId);
		return null;
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<String> getUserInfoKeys(String userId) {
		System.out.println("getUserInfoKeys:" + userId);
		return null;
	}

	/**
	 * @param userId
	 * @param key
	 */
	public void deleteUserInfo(String userId, String key) {
		System.out.println("deleteUserInfo");

	}

	/**
	 * Store account information for a remote system
	 * 
	 * @param userId
	 * @param userPassword
	 * @param accountName
	 * @param accountUsername
	 * @param accountPassword
	 * @param accountDetails
	 */
	public void setUserAccount(String userId, String userPassword, String accountName, String accountUsername, String accountPassword, Map<String, String> accountDetails) {
		System.out.println("setUserAccount===");
	}

	/**
	 * Get account names associated with the given user
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserAccountNames(String userId) {
		System.out.println("getUserAccountNames:" + userId);
		return null;
	}

	/**
	 * @param userId
	 * @param accountName
	 */
	public void deleteUserAccount(String userId, String accountName) {
		System.out.println("deleteUserAccount" + userId + "==" + accountName);
	}
}
