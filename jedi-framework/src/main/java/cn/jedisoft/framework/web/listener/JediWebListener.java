package cn.jedisoft.framework.web.listener;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.jedisoft.framework.common.ConfigUtils;
import cn.jedisoft.framework.core.AppContext;
import cn.jedisoft.framework.core.ContextLoader;
import cn.jedisoft.framework.core.conf.AppContextConf;
import cn.jedisoft.framework.web.WebAppContextLoader;
import cn.jedisoft.framework.web.core.WebAppContext;

/**
 * Application Lifecycle Listener implementation class AppContextLoader
 *
 */
public class JediWebListener implements ServletContextListener {

	/**
     * Default constructor. 
     */
    public JediWebListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  {
    	InputStream stream = JediWebListener.class.getClassLoader().getResourceAsStream(AppContext.JEDI_XML_CONF);
    	AppContextConf conf = ConfigUtils.load(AppContextConf.class, stream, ConfigUtils.TYPE_XML);
    	ServletContext servletCtx = event.getServletContext();
    	ContextLoader<WebAppContext> loader = new WebAppContextLoader(servletCtx);
    	WebAppContext context = loader.load(conf);

    	if(context != null) {
    		servletCtx.setAttribute(WebAppContext.JEDI_WEBAPP_CONTEXT, context);
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  {
    	ServletContext servletCtx = event.getServletContext();
    	WebAppContext context = (WebAppContext)servletCtx.getAttribute(WebAppContext.JEDI_WEBAPP_CONTEXT);
    	
    	if(context != null) {
    		context.destroy();
    	}

    	servletCtx.setAttribute(WebAppContext.JEDI_WEBAPP_CONTEXT, null);
    }

}
