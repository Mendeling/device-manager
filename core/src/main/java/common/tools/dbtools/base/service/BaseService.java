package common.tools.dbtools.base.service;

import java.util.List;
import java.util.Map;

import common.SysConst.SortBy;
import common.tools.dbtools.base.dao.BaseDao;
import common.tools.dbtools.base.utils.PageBean;

public abstract class BaseService<T> {

	public BaseService() {
	}

	/**
	 * 由业务类实现
	 * 
	 * @return
	 */
	public abstract BaseDao<T> getBaseDao();

	// protected BaseDao<T> baseDao;

	// public abstract void setBaseDao(BaseDao<T> baseDao);

	/**
	 * 根据id,查询单个
	 * 
	 * @param id
	 * @return
	 */
	public T get(int id) {
		return getBaseDao().get(id);
	}

	/**
	 * 插入单个对象
	 * 
	 * @param model
	 * @return
	 */
	public int create(T model) {
		return getBaseDao().insert(model);
	}
	
	/**
	 * 批量插入
	 * @param model
	 * @return
	 */
	public void createBatch(List<T> list) {
		getBaseDao().insertBatch(list);
	}
	/**
	 * 根据主键更新对象,更新对象所有字段
	 * 
	 * @param model
	 * @return
	 */
	public int update(T model) {
		return getBaseDao().update(model);
	}

	/**
	 * 根据主键更新对象,如果o属性不为空,则更新,为空则不更新
	 * 
	 * @param model
	 * @return
	 */
	public int update4Selective(T model) {
		return getBaseDao().update4Selective(model);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param o
	 * @return
	 */
	public int delete(int id) {
		return getBaseDao().delete(id);
	}

	/**
	 * 根据对象信息删除 例如,传入 对象 User, id=1,name=a , 则删除 id=1,name=a的记录 如果传入对象
	 * User,name=a, 则删除 name=a的记录
	 * 
	 * @param o
	 * @return
	 */
	public int delete(T model) {
		return getBaseDao().delete(model);
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return getBaseDao().findAll();
	}

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value) {
		return getBaseDao().findBy(name, value);
	}

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value, boolean isLike) {
		return getBaseDao().findBy(name, value, isLike);
	}

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value, String sortName, SortBy sortBy) {
		return getBaseDao().findBy(name, value, false, sortName, sortBy);
	}

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value, boolean isLike, String sortName, SortBy sortBy) {
		return getBaseDao().findBy(name, value, isLike, sortName, sortBy);
	}

	/**
	 * 根据map 查询对象
	 * 
	 * @param map
	 *            key=属性名,value=属性值
	 * @return 符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map) {
		return getBaseDao().findByMap(map);
	}

	/**
	 * 根据map 查询对象
	 * 
	 * @param map
	 *            key=属性名,value=属性值
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 * @return 符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map, boolean isLike) {
		return getBaseDao().findByMap(map, isLike);
	}

	/**
	 * 根据map 查询对象
	 * 
	 * @param map
	 *            key=属性名,value=属性值
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 * @return 符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map, String sortName, SortBy sortBy) {
		return getBaseDao().findByMap(map, sortName, sortBy);
	}

	/**
	 * 根据map 查询对象
	 * 
	 * @param map
	 *            key=属性名,value=属性值
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 * @return 符合条件的对象列表
	 */
	public List<T> findByMap(Map<String, Object> map, boolean isLike, String sortName, SortBy sortBy) {
		return getBaseDao().findByMap(map, isLike, sortName, sortBy);
	}

	/**
	 * 根据属性名和属性值查询单个对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueBy(String name, Object value) {
		return getBaseDao().findUniqueBy(name, value);
	}

	/**
	 * 根据map 查询单个对象
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 符合条件的唯一对象,如果有多条,则返回null
	 */
	public T findUniqueByMap(Map<String, Object> map) {
		return getBaseDao().findUniqueByMap(map);
	}

	/**
	 * 根据查询条件和分页信息,查询对象
	 * 
	 * @param pageBean
	 * @param map
	 */
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map) {
		getBaseDao().find4Page(pageBean, map);
	}

	/**
	 * 根据查询条件和分页信息,查询对象
	 * 
	 * @param pageBean
	 * @param map
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 */
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, boolean isLike) {
		getBaseDao().find4Page(pageBean, map, isLike);
	}

	/**
	 * 根据查询条件和分页信息,查询对象
	 * 
	 * @param pageBean
	 * @param map
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 */
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, String sortName, SortBy sortBy) {
		getBaseDao().find4Page(pageBean, map, sortName, sortBy);
	}

	/**
	 * 根据查询条件和分页信息,查询对象
	 * 
	 * @param pageBean
	 * @param map
	 * @param isLike
	 *            是否模糊匹配(只有属性类型为字符串,才生效)
	 * @param sortName
	 *            排序的字段(默认正序)
	 * @param sortBy
	 *            正序 or 倒序
	 */
	public void find4Page(PageBean<T> pageBean, Map<String, Object> map, boolean isLike, String sortName,
			SortBy sortBy) {
		getBaseDao().find4Page(pageBean, map, isLike, sortName, sortBy);
	}

	/**
	 * 根据ids,查询对象
	 * 
	 * @param ids
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年8月21日
	 */
	public List<T> findByIds(String ids) {
		return getBaseDao().findByIds(ids);
	}
}
