package cn.bigdb.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class MyAnnotation {
    
    /**
     * ע����
     * @author T4980D
     *
     */
    @Retention(RetentionPolicy.RUNTIME)  
    @Target(ElementType.TYPE)  
    public @interface MyClassAnnotation {  
        String uri();  
        String desc();  
    }  
    
    /** 
     * ���췽��ע�� 
     * @author T4980D 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.CONSTRUCTOR)   
    public @interface MyConstructorAnnotation {  
      
        String uri();  
        String desc();  
    }  
    
    /** 
     * �ҵķ���ע�� 
     * @author Owner 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.METHOD)  
    public @interface MyMethodAnnotation {  
      
        String uri();  
        String desc();  
    }  
    
    /** 
     * �ֶ�ע�ⶨ�� 
     * @author Owner 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.FIELD)   
    public @interface MyFieldAnnotation {  
      
        String uri();  
        String desc();  
    }  
    /**
     * 
     * ����ͬʱӦ�õ����Ϻͷ�����
     * @author T4980D
     *
     */
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Yts {
        // ����ö��
        public enum YtsType {
            util, entity, service, model
        }

        // ����Ĭ��ֵ
        public YtsType classType() default YtsType.util;
        
        // ����
        int[] arr() default {3, 7, 5};

        String color() default "blue";
    }
       
}