package cn.bigdb.validation.boot;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class JavaxValidation {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.setName("С��");
        d.setAge(2);
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Dog>> set = validator.validate(d);
        for (ConstraintViolation<Dog> constraintViolation : set) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}

class Dog {
    @NotNull(message = "����Ϊ��")
    private String name;

    @Min(value = 1, message = "����Ϊ1")
    @Max(value = 20, message = "���Ϊ20")
    private int age;

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
}