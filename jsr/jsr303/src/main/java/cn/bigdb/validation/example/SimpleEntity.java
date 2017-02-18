package cn.bigdb.validation.example;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SimpleEntity {

	@NotBlank(message="���ֲ���Ϊ�ջ��߿մ�")
	@Length(min=2,max=10,message="���ֱ�����2~10�������")
	private String name;
	
	@Past(message="ʱ�䲻�����ڵ�ǰʱ��")
	private Date date;
	
	@Email(message="�����ʽ����ȷ")
	private String email;
	
	@Pattern(regexp="(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,10}",message="���������5~10λ���ֺ���ĸ�����")
	private String password;
	
	@AssertTrue(message="�ֶα���Ϊ��")
	private boolean valid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
  
       //get set����ʡ�ԣ��Լ����
}