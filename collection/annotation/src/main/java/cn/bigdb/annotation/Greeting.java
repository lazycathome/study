package cn.bigdb.annotation;

import java.lang.annotation.Inherited;

/**
 * @Inherited Ԫע����һ�����ע�⣬@Inherited������ĳ������ע�������Ǳ��̳еġ����һ��ʹ����@Inherited���ε�annotation���ͱ�����һ��class�������annotation�������ڸ�class�����ࡣ
 * 
 * ����<br>ע�⣺@Inherited annotation�����Ǳ���ע����class���������̳С��ಢ��������ʵ�ֵĽӿڼ̳�annotation�������������������صķ����̳�annotation��
 * 
 * ����<br>��@Inherited annotation���ͱ�ע��annotation��Retention��RetentionPolicy.RUNTIME������API��ǿ�����ּ̳��ԡ��������ʹ��java.lang.reflectȥ��ѯһ��@Inherited annotation���͵�annotationʱ����������齫չ�����������class���丸�ֱ࣬������ָ����annotation���ͱ����֣����ߵ�����̳нṹ�Ķ��㡣
 * 
 *
 */
@Inherited
public @interface Greeting {
    public enum FontColor{ BULE,RED,GREEN};
    String name();
    FontColor fontColor() default FontColor.GREEN;
}