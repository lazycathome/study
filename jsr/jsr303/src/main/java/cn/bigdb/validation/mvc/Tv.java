package cn.bigdb.validation.mvc;

import java.util.Set;  
import javax.validation.ConstraintViolation;  
import javax.validation.Validation;  
import javax.validation.Validator;  
import javax.validation.ValidatorFactory;  
public class Tv {  
    public static void main(String[] args) {  
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();  
        Validator validator = factory.getValidator();  
  
        Entity entity = new Entity();  
        entity.setAge(12);  
        entity.setName("admin");  
        Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(entity);  
        for (ConstraintViolation<Entity> constraintViolation : constraintViolations) {  
            System.out.println("��������:"+constraintViolation.getPropertyPath());  
            System.out.println("���ʻ�key:"+constraintViolation.getMessageTemplate());  
            System.out.println("������Ϣ:"+constraintViolation.getMessage());  
        }  
  
    }  
}  