package cn.bigdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Table {
    /**
     * ���ݱ�����ע�⣬Ĭ��ֵΪ������
     * @return
     */
    public String tableName() default "className";
}

