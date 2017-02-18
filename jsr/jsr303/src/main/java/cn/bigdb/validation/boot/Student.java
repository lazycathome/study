package cn.bigdb.validation.boot;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

//ͨ��script ����ָ������У��ķ���������У��Ĳ�����
//��Ȼ����ͨ��groups����ָ����������
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

	// ע�����У��ķ���Ҫд�ɾ�̬��������������
	// TypeError: xxx is not a function �Ĵ���
	public static boolean checkParams(String name, int age, String classes) {
		if (name != null && age > 8 & classes != null) {
			return true;
		} else {
			return false;
		}

	}

}
