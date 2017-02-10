package cn.jedisoft.framework.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.jedisoft.framework.core.DataSourceFactory;

public class DatabaseWrapper extends SimpleJdbcWrapper implements Database {

	private static final Logger log = Logger.getLogger(DatabaseWrapper.class);

	public DatabaseWrapper(DataSourceFactory dsf) {
		super(dsf);
	}

	@Override
	public Transaction getTransaction() {
		return new TransactionWrapper(dsf);
	}

	@Override
	public int insert(String sql) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql);

		} catch (SQLException e) {
			log.error(String.format("insert(%s) failed: ", sql), e);
		}

		return 0;
	}

	@Override
	public int insert(String sql, Object... params) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql, params);

		} catch (SQLException e) {
			log.error(String.format("insert(%s) failed: ", sql), e);
		}

		return 0;
	}

	@Override
	public <T> T selectOne(Class<T> clazz, String sql) {
		try {
			ResultSet rs = executeQuery(getConnection(SimpleJdbcWrapper.CONN_READ_ONLY), sql);

			return toBean(clazz, rs);

		} catch (SQLException e) {
			log.error(String.format("selectOne(%s) failed: ", sql), e);
		}

		return null;
	}

	@Override
	public <T> T selectOne(Class<T> clazz, String sql, Object... params) {
		try {
			ResultSet rs = executeQuery(getConnection(SimpleJdbcWrapper.CONN_READ_ONLY), sql, params);

			return toBean(clazz, rs);

		} catch (SQLException e) {
			log.error(String.format("selectOne(%s) failed: ", sql), e);
		}

		return null;
	}

	@Override
	public <T> List<T> select(Class<T> clazz, String sql) {
		try {
			ResultSet rs = executeQuery(getConnection(SimpleJdbcWrapper.CONN_READ_ONLY), sql);

			return toBeanList(clazz, rs);

		} catch (SQLException e) {
			log.error(String.format("select(%s) failed: ", sql), e);
		}

		return null;
	}

	@Override
	public <T> List<T> select(Class<T> clazz, String sql, Object... params) {
		try {
			ResultSet rs = executeQuery(getConnection(SimpleJdbcWrapper.CONN_READ_ONLY), sql, params);

			return toBeanList(clazz, rs);

		} catch (SQLException e) {
			log.error(String.format("select(%s) failed: ", sql), e);
		}

		return null;
	}

	@Override
	public int update(String sql) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql);

		} catch (SQLException e) {
			log.error(String.format("update(%s) failed: ", sql), e);
		}

		return 0;
	}

	@Override
	public int update(String sql, Object... params) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql, params);

		} catch (SQLException e) {
			log.error(String.format("update(%s) failed: ", sql), e);
		}

		return 0;
	}

	@Override
	public int delete(String sql) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql);

		} catch (SQLException e) {
			log.error(String.format("delete(%s) failed: ", sql), e);
		}

		return 0;
	}

	@Override
	public int delete(String sql, Object... params) {
		try {
			return executeUpdate(getConnection(SimpleJdbcWrapper.CONN_READ_WRITE), sql, params);

		} catch (SQLException e) {
			log.error(String.format("delete(%s) failed: ", sql), e);
		}

		return 0;
	}

}
