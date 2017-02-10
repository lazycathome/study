package cn.jedisoft.framework.db;

/**
 * 数据库操作接口
 * 
 * @author lzm
 *
 */
public interface Database extends SimpleJdbc {

	/**
	 * 获取事务对象，用于批量执行 sql
	 * 
	 * @return
	 */
	public Transaction getTransaction();

}
