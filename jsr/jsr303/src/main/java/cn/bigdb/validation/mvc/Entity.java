package cn.bigdb.validation.mvc;

import javax.validation.constraints.Max;  
import org.hibernate.validator.constraints.Length;  
public class Entity {  
    @Max(value=3)//���ֵΪ3  
    private int age;  
    @Length(max=1) //�ַ����������Ϊ1,hibernate ��չ��  
    private String name;  
    public int getAge() {  
        return age;  
    }  
    public void setAge(int age) {  
        this.age = age;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
}
