package net.cn.util;

import java.util.ArrayList;
import java.util.List;

import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPSearchResults;

public class LDAP {
	String MY_HOST = "172.20.20.200";// ����AD���IP��ַ
	int MY_PORT = 389;// �˿ںţ�Ĭ��Ϊ389
	int TIMEOUT = 10000;// ��ʱ����
	String searchBase = "dc=kinstalk,dc=com";// �������
	int searchScope = LDAPConnection.SCOPE_SUB;// ������Χ
	String filter = "(|(objectclass=person)(objectclass=user)(objectclass=organizationalPerson))";// ������

	/**
	 * @param uid
	 * @return ����uid��ȡ�û��ڵ�
	 * @throws Exception
	 */
	public List<String> getUserDN(String uid) throws Exception {
		List<String> result = new ArrayList<String>();

		LDAPConnection ld = new LDAPConnection();
		ld.setSocketTimeOut(TIMEOUT);
		ld.connect(MY_HOST, MY_PORT);
		ld.bind(null, null);// ������¼

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
	 * @return ���ݽڵ������û����볢������LDAP������
	 */
	public boolean userConnect(String userDN, String password) {
		if (password.equals("")) {
			return false;
		}
		try {
			LDAPConnection ld = new LDAPConnection();
			ld.setSocketTimeOut(TIMEOUT);// ���ó�ʱ
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
	 * @return �û���¼��֤
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
	 * @return ��ȡ�û��б�
	 */
	public List<String> getUsers() {
		List<String> result = new ArrayList<String>();
		try {
			LDAPConnection ld = new LDAPConnection();
			ld.setSocketTimeOut(TIMEOUT);
			ld.connect(MY_HOST, MY_PORT);
			ld.bind(null, null);// ������¼

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
