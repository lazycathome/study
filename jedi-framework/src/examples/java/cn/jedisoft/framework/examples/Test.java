package cn.jedisoft.framework.examples;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

import cn.jedisoft.framework.common.ConfigUtils;
import cn.jedisoft.framework.core.AppContext;
import cn.jedisoft.framework.core.ContextLoader;
import cn.jedisoft.framework.core.conf.AppContextConf;
import cn.jedisoft.framework.db.Database;
import cn.jedisoft.framework.db.Transaction;
import cn.jedisoft.framework.web.WebAppContextLoader;
import cn.jedisoft.framework.web.core.WebAppContext;

public class Test {

	protected static final Log log = LogFactory.getLog(Test.class);

	public static void main(String[] args) {
    	InputStream stream = Test.class.getClassLoader().getResourceAsStream(AppContext.JEDI_XML_CONF);
    	AppContextConf conf = ConfigUtils.load(AppContextConf.class, stream, ConfigUtils.TYPE_XML);
    	Gson g = new Gson();

    	log.info(g.toJson(conf));

//    	ContextLoader<AppContext> loader = new AppContextLoader();
//    	AppContext context = loader.load(conf);
    	ContextLoader<WebAppContext> loader = new WebAppContextLoader(null);
    	WebAppContext context = loader.load(conf);
    	
    	Database dba = context.getBean("database");
    	
//    	dba.insert("insert into adm_user(name, pwd) values('azhi', '123456');");
    	dba.delete("delete from adm_user where name='azhi';");
    	dba.update("update adm_user set pwd='goodoo' where name='admin';");
    	AdmUser adm = dba.selectOne(AdmUser.class, "select t.* from adm_user t where name=?;", "admin");
    	List<AdmUser> users = dba.select(AdmUser.class, "select * from adm_user;");
    	List<Image> imgs = dba.select(Image.class, "select g.title, g.total, i.id, i.url from pic_group g, pic_image i where g.id=? and i.group_id=g.id and length(i.url)>0;", 1);

    	log.info(g.toJson(adm));
    	log.info(g.toJson(users));
    	log.info(g.toJson(imgs));
    	
    	Transaction ts = dba.getTransaction();
    	
/*    	ts.begin();

    	for(int i = 0; i < 10; i ++) {
    		ts.insert("insert into adm_user(name, pwd) values('azhi', '123456');");
    	}

    	ts.insert("insert into adm_user(name, pwd, shit) values('azhi', '123456', 0);");

    	ts.commit();
*/	}

}
