package net.cn.dao;

import java.io.Serializable;
import java.util.List;


public interface BaseDao<T> {
	
	/**
	 * @param o
	 * @return	存储对象实体
	 */
	public Serializable save(T o);

	/**
	 * @param o
	 * 删除对象实体
	 */
	public void delete(T o);

	/**
	 * @param o
	 * 更新对象实体
	 */
	public void update(T o);

	/**
	 * @param o
	 * 存储或更新对象实体
	 */
	public void saveOrUpdate(T o);

	/**
	 * @param hql
	 * @return	查询对象实体
	 */
	public List<T> find(String hql);
	
	/**
	 * @param hql
	 * @param param
	 * @return	查询对象实体
	 */
	public List<T> find(String hql,Object param);
	
	/**
	 * @param hql
	 * @param param
	 * @return	查询对象实体
	 */
	public List<T> find(String hql, Object[] param);

	/**
	 * @param hql
	 * @param param
	 * @return	查询对象实体
	 */
	public List<T> find(String hql, List<Object> param);

	/**
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return	查询对象实体
	 */
	public List<T> find(String hql, Object[] param, Integer page, Integer rows);

	/**
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return	查询对象实体
	 */
	public List<T> find(String hql, List<Object> param, Integer page,
			Integer rows);

	/**
	 * @param c
	 * @param id
	 * @return	根据对象标识查找实体对象
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * @param hql
	 * @param param
	 * @return	根据参数查找实体对象
	 */
	public T get(String hql, Object[] param);

	/**
	 * @param hql
	 * @param param
	 * @return	根据参数查找实体对象
	 */
	public T get(String hql, List<Object> param);

	/**
	 * @param hql
	 * @return	
	 */
	public Long count(String hql);

	/**
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, Object[] param);

	/**
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, List<Object> param);

	/**
	 * @param hql
	 * @return	执行hql语句
	 */
	public Integer executeHql(String hql);

	/**
	 * @param hql
	 * @param param
	 * @return	执行hql语句
	 */
	public Integer executeHql(String hql, Object[] param);

	/**
	 * @param hql
	 * @param param
	 * @return	执行hql语句
	 */
	public Integer executeHql(String hql, List<Object> param);

}
