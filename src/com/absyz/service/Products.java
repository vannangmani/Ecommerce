package com.absyz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.absyz.core.DbConnection;

public class Products {
	
	public static String add_produts(HttpServletRequest request)
	{
		Connection conn =null;
		PreparedStatement psInsert = null;
		ResultSet rsProducts = null;
		ResultSet rsProductsMaxId = null;
		Statement stSelectQuery = null;
		Statement stSelectMaxId = null;
		String strProduct = request.getParameter("productname");
		String strQuery = "Select * from products where productname = '"+strProduct+"'";
		System.out.println(strQuery);
		String strOutput="";
		int intProductId = 0;
		try {
			conn = DbConnection.getConnection();
			stSelectQuery = conn.createStatement();
			rsProducts = stSelectQuery.executeQuery(strQuery);
//			if(rsProducts.next())
//			{
				strQuery = "Select max(productid) productid from products";
				stSelectMaxId = conn.createStatement();
				rsProductsMaxId = stSelectMaxId.executeQuery(strQuery);
				if(rsProductsMaxId.next())
				{
					intProductId = rsProductsMaxId.getInt("productid")+1;
				}
				else
				{
					intProductId = 100;
				}
				String strBrand = request.getParameter("brand");
				int intStock = Integer.parseInt(request.getParameter("stock"));
				double dblPrice = Double.parseDouble(request.getParameter("price"));
				psInsert = conn.prepareStatement("Insert into products(productid,productname,stock,brandname,price)values(?,?,?,?,?)");
				psInsert.setInt(1, intProductId);
				psInsert.setString(2, strProduct);
				psInsert.setInt(3, intStock);
				psInsert.setString(4, strBrand);
				psInsert.setDouble(5, dblPrice);
				
				psInsert.executeUpdate();
				strOutput = "Record Inserted";
				
//			}
//			else
//			{
//				strOutput = "User Already Exists";
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			strOutput = "Record Not Inserted";
			e.printStackTrace();
		}
		System.out.println(strOutput);
		return strOutput;
	}

}