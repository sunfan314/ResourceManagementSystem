package com.qlove.server.rms.util;

import java.util.ArrayList;
import java.util.List;

import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

@SuppressWarnings("all")
public class LDAP {
	
	private LDAPConnection connection;
	
	/**
	 * @return	创建连接
	 */
	public LDAPConnection getConnectionInstance(){
		if(connection==null){
			connection=new LDAPConnection();	
			//设置超时时间
			connection.setSocketTimeOut(LDAPConfig.TIMEOUT);
			//设置服务器地址和端口号
			try {
				connection.connect(LDAPConfig.HOST,LDAPConfig.PORT);
			} catch (LDAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}

	/**
	 * @param uid
	 * @return 根据uid获取用户节点
	 * @throws Exception
	 */
	public List<String> getUserDN(String uid) throws Exception {
		List<String> result = new ArrayList<String>();

		LDAPConnection conn = this.getConnectionInstance();
		//匿名登录
		conn.bind(null, null);

		LDAPSearchResults searchResults = conn.search(LDAPConfig.USER_SEARCH_BASE, LDAPConfig.SEARCH_SCOPE, LDAPConfig.USER_FILTER, null, false);

		while (searchResults.hasMore()) {
			LDAPEntry nextEntry = searchResults.next();
			LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
			String entryUid = attributeSet.getAttribute("uid").getStringValue();
			if (entryUid.equals(uid)) {
				result.add(nextEntry.getDN());
			}
		}
		return result;
	}

	/**
	 * @param userDN
	 * @param password
	 * @return 根据节点名和用户密码尝试连接LDAP服务器
	 */
	public boolean userConnect(String userDN, String password) {
		if (password.equals("")) {
			return false;
		}
		try {
			LDAPConnection conn = this.getConnectionInstance();
			conn.bind(userDN, password);
			return conn.isConnected();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	/**
	 * @param uid
	 * @param password
	 * @return 用户登录验证
	 */
	public boolean userVerify(String uid, String password) {
		try {
			if (!(getUserDN(uid).size() == 1)) {
				return false;
			}
			return userConnect(getUserDN(uid).get(0), password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @return 获取用户列表
	 */
	public List<String> getUsers() {
		List<String> result = new ArrayList<String>();
		try {
			LDAPConnection conn = this.getConnectionInstance();
			// 匿名登录
			conn.bind(null, null);

			LDAPSearchResults searchResults = conn.search(LDAPConfig.USER_SEARCH_BASE, LDAPConfig.SEARCH_SCOPE, LDAPConfig.USER_FILTER, null, false);

			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				String entryUid = attributeSet.getAttribute("uid").getStringValue();
				result.add(entryUid);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return result;
		}

	}
	
	/**
	 * @return	获取管理员列表
	 */
	public List<String> getAdmins(){
		List<String> result = new ArrayList<String>();
		try {
			LDAPConnection conn =this.getConnectionInstance();
			// 匿名登录
			conn.bind(null, null);

			LDAPSearchResults searchResults = conn.search(LDAPConfig.ADMIN_SEARCH_BASE, LDAPConfig.SEARCH_SCOPE, LDAPConfig.GROUP_FILTER, null, false);

			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				String entryUid = attributeSet.getAttribute("memberUid").getStringValue();
				result.add(entryUid);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return result;
		}
	}
	
	/**	
	 * @return	获取经理列表
	 */
	public List<String> getManagers(){
		List<String> result = new ArrayList<String>();
		try {
			LDAPConnection conn = this.getConnectionInstance();
			// 匿名登录
			conn.bind(null, null);

			LDAPSearchResults searchResults = conn.search(LDAPConfig.MANAGER_SEARCH_BASE, LDAPConfig.SEARCH_SCOPE, LDAPConfig.GROUP_FILTER, null, false);

			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				String entryUid = attributeSet.getAttribute("memberUid").getStringValue();
				result.add(entryUid);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return result;
		}
	}

}
