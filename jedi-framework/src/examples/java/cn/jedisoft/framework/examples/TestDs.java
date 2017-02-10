package cn.jedisoft.framework.examples;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

public class TestDs {

	public static void main(String[] args) {
		DruidDataSource ds = new DruidDataSource();
		
		ds.setDriverClassName("org.sqlite.JDBC");
		ds.setUrl("jdbc:sqlite:E:/azhi/zhennvshen/src/main/resources/zns.db");

		try {
			PreparedStatement ps = ds.getConnection().prepareStatement("select * from adm_user");
//			ps.setString(1, "azhi");
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					System.out.println(String.format("NAME: %s", rs.getString("name")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
