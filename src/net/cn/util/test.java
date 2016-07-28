package net.cn.util;

import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LDAP ldap=new LDAP();
		List<String> list;
		try {
			list = ldap.getUsers();
			for (String str : list) {
				System.out.println(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
