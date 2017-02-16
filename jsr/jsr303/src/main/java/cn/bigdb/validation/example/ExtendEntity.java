package cn.bigdb.validation.example;

public class ExtendEntity {

	@Password
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}