package com.qlove.server.rms.util;

import com.novell.ldap.LDAPConnection;

public class LDAPConfig {
	
	/**
	 * LDAP服务器地址
	 */
	public static String HOST = "172.20.20.200";
	
	/**
	 * LDAP服务器端口
	 */
	public static int PORT = 389;
	
	/**
	 * 超时设置
	 */
	public static int TIMEOUT = 10000;
	
	/**
	 * 域名搜索入口
	 */
	public static String SEARCH_BASE="dc=kinstalk,dc=com";
	
	/**
	 * 职员域名搜索入口
	 */
	public static String USER_SEARCH_BASE="ou=People,dc=kinstalk,dc=com";
	
	/**
	 * 管理员域名搜索入口
	 */
	public static String ADMIN_SEARCH_BASE="cn=SZJY_RMS_ADMIN,ou=Groups,dc=kinstalk,dc=com";
	
	/**
	 * 经理域名搜索入口
	 */
	public static String MANAGER_SEARCH_BASE="cn=SZJY_RMS_MANAGER,ou=Groups,dc=kinstalk,dc=com";
	
	
	/**
	 * 用户搜索过滤器
	 */
	public static String USER_FILTER="(|(objectClass=inetOrgPerson)(objectClass=posixAccount)(objectClass=shadowAccount))";
	
	/**
	 * 群组搜索过滤器
	 */
	public static String GROUP_FILTER="(|(objectClass=posixGroup)(objectClass=top))";
	
	/**
	 * 搜索范围
	 */
	public static int SEARCH_SCOPE=LDAPConnection.SCOPE_SUB;
	
	
	
	
}
