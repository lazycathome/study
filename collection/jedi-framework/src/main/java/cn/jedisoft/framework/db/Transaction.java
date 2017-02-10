package cn.jedisoft.framework.db;

/**
 * 数据库操作接口
 * 
 * @author lzm
 *
 */
public interface Transaction extends SimpleJdbc {

	/**
	 * 开始事务
	 * 
	 * @return
	 */
	public boolean begin();

	/**
	 * 提交事务
	 * 
	 * @return
	 */
	public boolean commit();

}
