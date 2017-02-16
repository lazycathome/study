package cn.bigdb.validation.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			 throws ServletException, IOException { 
			 HttpSession session = req.getSession(); 
			 // 从 request 中获取输入信息
			 String orderId = (String) req.getParameter("orderId"); 
			 String customer = (String) req.getParameter("customer"); 
			 String email = (String) req.getParameter("email"); 
			 String address = (String) req.getParameter("address"); 
			 String status = (String) req.getParameter("status"); 
			 String productName = (String) req.getParameter("productName"); 
			 String productPrice = (String) req.getParameter("productPrice"); 
			 // 将 Bean 放入 session 中
			 Order order = new Order(); 
			 order.setOrderId(orderId); 
			 order.setCustomer(customer); 
			 order.setEmail(email); 
			 order.setAddress(address); 
			 order.setStatus(status); 
			 order.setCreateDate(new Date()); 
			 Product product = new Product(); 
			 product.setProductName(productName); 
			 if(productPrice != null && productPrice.length() > 0) 
			 product.setPrice(Float.valueOf(productPrice)); 
			 order.setProduct(product); 
			 session.setAttribute("order", order); 
			 ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
			 Validator validator = factory.getValidator(); 
			 Set<ConstraintViolation<Order>> violations = validator.validate(order); 
			 if(violations.size() == 0) { 
			 session.setAttribute("order", null); 
			 session.setAttribute("errorMsg", null); 
			 resp.sendRedirect("creatSuccessful.jsp"); 
			 } else { 
			 StringBuffer buf = new StringBuffer(); 
			 ResourceBundle bundle = ResourceBundle.getBundle("messages"); 
			 for(ConstraintViolation<Order> violation: violations) { 
			 buf.append("-" + bundle.getString(violation.getPropertyPath().toString())); 
			 buf.append(violation.getMessage() + "<BR>\n"); 
			 } 
			 session.setAttribute("errorMsg", buf.toString()); 
			 resp.sendRedirect("createOrder.jsp"); 
			 } 
			 }
	
}
