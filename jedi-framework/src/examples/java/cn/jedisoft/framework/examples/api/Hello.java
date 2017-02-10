package cn.jedisoft.framework.examples.api;

import cn.jedisoft.framework.annotations.Autowired;
import cn.jedisoft.framework.examples.Something;
import cn.jedisoft.framework.web.annotations.Path;
import cn.jedisoft.framework.web.annotations.method.GET;
import cn.jedisoft.framework.web.annotations.parameter.PathParam;
import cn.jedisoft.framework.web.result.WebResult;
import cn.jedisoft.framework.web.result.HtmlResult;

@Path("/api/hello-{uname}/{count}")
public class Hello {

	@Autowired
	Something s;
	
	@GET
	public WebResult hello(@PathParam("uname")String uname, @PathParam("count")int count) {
		return new HtmlResult(String.format("Hello, %s. Are you lost %d %s?", uname, count, s.fuck()));
	}

}
