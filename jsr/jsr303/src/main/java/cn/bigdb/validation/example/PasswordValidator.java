package cn.bigdb.validation.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * �Զ�������У����
 * @author wdmcygah
 *
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

	//5~10λ����������ĸ���
	private static Pattern pattern = Pattern.compile("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,10}");
	
	public void initialize(Password constraintAnnotation) {
		//do nothing
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if( value==null ){
			return false;
		}
		Matcher m = pattern.matcher(value);
		return m.matches();
	}	
}