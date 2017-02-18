package cn.bigdb.validation.servlet;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Order {
	// ���벻Ϊ null, ��С�� 10
	@NotNull
	@Size(min = 10, max = 10)
	private String orderId;
	// ���벻Ϊ��
	@NotEmpty
	private String customer;
	// ������һ�����������ַ
	@Email
	private String email;
	// ���벻Ϊ��
	@NotEmpty
	private String address;
	// ���벻Ϊ null, �����������ĸ��ַ���'created', 'paid', 'shipped', 'closed'����֮һ
	// @Status ��һ�����ƻ��� contraint
	@NotNull
	@Status
	private String status;
	// ���벻Ϊ null
	@NotNull
	private Date createDate;
	// Ƕ����֤
	@Valid
	private Product product;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
