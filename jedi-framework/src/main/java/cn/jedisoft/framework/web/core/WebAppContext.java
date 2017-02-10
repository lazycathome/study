package cn.jedisoft.framework.web.core;

import cn.jedisoft.framework.core.AppContext;
import cn.jedisoft.framework.core.AutowireBeanFactory;

/**
 * AppContext 对象的扩展，用于适配 Web 服务的处理
 * 
 * @author lzm
 *
 */
public interface WebAppContext extends AppContext {

	public static final String JEDI_WEBAPP_CONTEXT = "JEDI_WEBAPP_CONTEXT";

	/**
	 * 获取自动注入 Bean 工厂对象
	 * @return
	 */
	public AutowireBeanFactory getAutowireBeanFactory();

	/**
	 * 根据 url 匹配到对应的 Api 处理对象
	 * @param url
	 * @param method
	 * @return
	 */
	public WebApiWrapper matchApi(String url, String method);
	
}
