package cn.jedisoft.framework.app;

import javax.sql.DataSource;

import cn.jedisoft.framework.core.AppContext;
import cn.jedisoft.framework.core.DataSourceFactory;

/**
 * AppContext 包装类
 * 
 * @author lzm
 *
 */
public class JediAppContext extends JediBeanFactory implements AppContext, DataSourceFactory {

	protected DataSource readOnlyDataSource = null;
	protected DataSource readWriteDataSource = null;

	public void setReadOnlyDataSource(DataSource ds) {
		readOnlyDataSource = ds;
	}

	@Override
	public DataSource getReadOnlyDataSource() {
		return readOnlyDataSource;
	}

	public void setReadWriteDataSource(DataSource ds) {
		readWriteDataSource = ds;
	}

	@Override
	public DataSource getReadWriteDataSource() {
		return readWriteDataSource;
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
