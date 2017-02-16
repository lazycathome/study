package cn.bigdb.validation.example;

import java.util.Date;

import org.junit.Assert;

import junit.framework.TestCase;


public class ValidationUtilsTest extends TestCase{

	  public void validateSimpleEntity() {
		  SimpleEntity se = new SimpleEntity();
		  se.setDate(new Date());
		  se.setEmail("123");
		  se.setName("123");
		  se.setPassword("123");
		  se.setValid(false);
		  ValidationResult result = ValidationUtils.validateEntity(se);
		  System.out.println("--------------------------");
		  System.out.println(result);
		  Assert.assertTrue(result.isHasErrors());
	  }
	  
	  public void validateSimpleProperty() {
		  SimpleEntity se = new SimpleEntity();
		  ValidationResult result = ValidationUtils.validateProperty(se,"name");
		  System.out.println("--------------------------");
		  System.out.println(result);
		  Assert.assertTrue(result.isHasErrors());
	  }
	  
	  public void validateExtendEntity() {
		  ExtendEntity ee = new ExtendEntity();
		  ee.setPassword("212");
		  ValidationResult result = ValidationUtils.validateEntity(ee);
		  System.out.println("--------------------------");
		  System.out.println(result);
		  Assert.assertTrue(result.isHasErrors());
	  }
	}