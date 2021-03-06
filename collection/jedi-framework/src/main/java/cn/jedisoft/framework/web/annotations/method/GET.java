package cn.jedisoft.framework.web.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.jedisoft.framework.web.annotations.HttpMethod;

/**
 * 用于描述方法对应于 HTTP GET
 * 
 * @author azhi
 *
 */
@Target(value=ElementType.METHOD)
@Retention(value=RetentionPolicy.RUNTIME)
@HttpMethod(value=HttpMethod.METHOD_GET)
public @interface GET {

}
