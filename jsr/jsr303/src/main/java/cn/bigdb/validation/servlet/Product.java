package cn.bigdb.validation.servlet;

import org.hibernate.validator.constraints.NotEmpty;

public class Product { 
	 // ����ǿ�
	 @NotEmpty 
	 private String productName; 
	 // ������ 8000 �� 10000 �ķ�Χ��
	 // @Price ��һ�����ƻ��� constraint 
	 @Price 
	 private float price;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	} 

	 }