package cn.jedisoft.framework.core;

import cn.jedisoft.framework.core.conf.AppContextConf;

/**
 * 上下文加载器
 * 
 * @author lzm
 *
 * @param <T>
 */
public interface ContextLoader<T> {

	/**
	 * 从配置文件加载应用上下文
	 * @param confPath
	 * @return
	 */
	public T load(String confPath);

	/**
	 * 从配置对象加载应用上下文
	 * @param conf
	 * @return
	 */
	public T load(AppContextConf conf);

}
