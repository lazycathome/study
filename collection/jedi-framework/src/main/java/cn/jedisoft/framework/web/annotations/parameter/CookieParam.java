package cn.jedisoft.framework.web.annotations.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.jedisoft.framework.web.annotations.HttpParam;

/**
 * 用于描述参数来自 Cookie
 * 
 * @author azhi
 *
 */
@Target(value={ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@HttpParam(value=HttpParam.SCOPE_COOKIE)
public @interface CookieParam {

	public String value() default "";

}
