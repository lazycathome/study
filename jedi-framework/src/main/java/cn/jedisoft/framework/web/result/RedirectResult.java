package cn.jedisoft.framework.web.result;

/**
 * 封装 Redirect 处理
 * 
 * @author azhi
 *
 */
public class RedirectResult extends DefaultResult {

	public RedirectResult(String url) {
		super(WebResult.TYPE_REDIRECT, url);
	}

}
