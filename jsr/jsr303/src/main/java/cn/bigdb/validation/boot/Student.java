package cn.bigdb.validation.boot;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

//通过script 属性指定进行校验的方法，传递校验的参数，
//依然可以通过groups属性指定分组名称
@ScriptAssert(lang="javascript",script="cn.bigdb.validation.boot.Student.checkParams(_this.name,_this.age,_this.classes)",groups=CHECK.class)
public class Student {

	@NotBlank(groups = NAME.class)
	private String name;
	@Min(value = 3, groups = AGE.class)
	private int age;
	@NotBlank
	private String classess;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getClassess() {
		return classess;
	}

	public void setClassess(String classess) {
		this.classess = classess;
	}

	public interface NAME {
	};

	public interface AGE {
	};

	// 注意进行校验的方法要写成静态方法，否则会出现
	// TypeError: xxx is not a function 的错误
	public static boolean checkParams(String name, int age, String classes) {
		if (name != null && age > 8 & classes != null) {
			return true;
		} else {
			return false;
		}

	}

}
