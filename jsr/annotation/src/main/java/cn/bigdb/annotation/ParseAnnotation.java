package cn.bigdb.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;

import cn.bigdb.annotation.MyAnnotation.MyClassAnnotation;
import cn.bigdb.annotation.MyAnnotation.MyMethodAnnotation;
import cn.bigdb.annotation.MyAnnotation.Yts;
import cn.bigdb.annotation.MyAnnotation.Yts.YtsType;

public class ParseAnnotation {

    /**
     * 解析方法注解
     * @param <T>
     * @param clazz
     */
    public static <T> void parseMethod(Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            for (Method method : clazz.getDeclaredMethods()) {
                MyMethodAnnotation methodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
                if (methodAnnotation!=null) {
                    //通过反射调用带有此注解的方法
                    method.invoke(obj, methodAnnotation.uri());
                }
                Yts yts = (Yts) method.getAnnotation(Yts.class);
                if (yts != null) {
                    if (YtsType.util.equals(yts.classType())) {
                        System.out.println("this is a util method");
                    } else {
                        System.out.println("this is a other method");
                    }
                    System.out.println(Arrays.toString(yts.arr())); //打印数组
                    System.out.println(yts.color()); //输出颜色
                }
                System.out.println("\t\t-----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析类注解
     * @param <T>
     * @param clazz
     */
    public static <T> void parseType(Class<T> clazz) {
        try {
            Yts yts = (Yts) clazz.getAnnotation(Yts.class);
            if (yts != null) {
                if (YtsType.util.equals(yts.classType())) {
                    System.out.println("this is a util class");
                } else {
                    System.out.println("this is a other class");
                }
            }
            MyClassAnnotation classAnnotation = (MyClassAnnotation) clazz.getAnnotation(MyClassAnnotation.class);
            if (classAnnotation != null) {
                System.err.println(" class info: "+classAnnotation.uri());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        parseMethod(TestAnnotation.class);
        parseType(TestAnnotation.class);
    }

}