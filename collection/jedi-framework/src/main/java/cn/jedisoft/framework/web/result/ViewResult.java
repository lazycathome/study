package cn.jedisoft.framework.web.result;

/**
 * 封装视图格式的调用返回
 * 
 * @author azhi
 *
 */
public class ViewResult extends DefaultResult {

	public ViewResult(String text) {
		super(WebResult.TYPE_VIEW, text);
	}

}
