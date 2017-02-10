package cn.jedisoft.framework.app;

import org.apache.log4j.Logger;

import cn.jedisoft.framework.core.AppContext;
import cn.jedisoft.framework.core.conf.AppContextConf;

/**
 * 应用上下文加载器
 * 
 * @author lzm
 *
 */
public class AppContextLoader extends BaseContextLoader<AppContext> {

	private static final Logger log = Logger.getLogger(AppContextLoader.class);

	@Override
	public AppContext load(AppContextConf conf) {
		JediAppContext context = new JediAppContext();

		addDefinitionBeans(context, conf.getJediBean());

		scanPackages(context, conf.getJediScan());

		addDefinitionDss(context, conf.getJediDs());

		return context;
	}

}
