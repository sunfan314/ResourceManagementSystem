package net.cn.util;

import java.util.ArrayList;
import java.util.List;

import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPSearchResults;

public class LDAP {
	String MY_HOST = "172.20.20.200";// 访问AD域的IP地址
	int MY_PORT = 389;// 端口号，默认为389
	int TIMEOUT = 10000;// 超时设置
	String searchBase = "dc=kinstalk,dc=com";// 域名入口
	int searchScope = LDAPConnection.SCOPE_SUB;// 搜索范围
	String filter = "(|(objectclass=person)(objectclass=user)(objectclass=organizationalPerson))";// 过滤器

	/**
	 * @param uid
	 * @return 根据uid获取用户节点
	 * @throws Exception
	 */
	public List<String> getUserDN(String uid) throws Exception {
		List<String> result = new ArrayList<String>();

		LDAPConnection ld = new LDAPConnection();
		ld.setSocketTimeOut(TIMEOUT);
		ld.connect(MY_HOST, MY_PORT);
		ld.bind(null, null);// 匿名登录

		LDAPSearchResults searchResults = ld.search(searchBase, searchScope, filter, null, false);

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
			LDAPConnection ld = new LDAPConnection();
			ld.setSocketTimeOut(TIMEOUT);// 设置超时
			ld.connect(MY_HOST, MY_PORT);
			ld.bind(userDN, password);
			return ld.isConnected();
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
			LDAPConnection ld = new LDAPConnection();
			ld.setSocketTimeOut(TIMEOUT);
			ld.connect(MY_HOST, MY_PORT);
			ld.bind(null, null);// 匿名登录

			LDAPSearchResults searchResults = ld.search(searchBase, searchScope, filter, null, false);

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

}
