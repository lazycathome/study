package cn.bigdb.validation.mvc;

import javax.validation.ConstraintValidator;  
import javax.validation.ConstraintValidatorContext;  
  
public class CannotContainSpacesValidator implements ConstraintValidator<CannotContainSpaces, String> {  
    private int len;  
    /** 
     * ��ʼ����,��ȡע����length��ֵ 
     */  
    @Override  
    public void initialize(CannotContainSpaces arg0) {  
        this.len = arg0.length();  
    }  
  
    @Override  
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {  
        if(str != null){  
            if(str.indexOf(" ") < 0){  
                return true;  
            }  
        }else{  
            constraintValidatorContext.disableDefaultConstraintViolation();//����Ĭ�ϵ�message��ֵ  
            //������Ӵ�����ʾ���  
            constraintValidatorContext  
            .buildConstraintViolationWithTemplate("�ַ�������Ϊ��").addConstraintViolation();  
        }  
        return false;  
    }  
  
}  