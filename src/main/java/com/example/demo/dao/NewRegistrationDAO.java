package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

@Repository
public class NewRegistrationDAO {
	private static final String JDBC_URL =
		"jdbc:postgresql://localhost:5432/groupb_project";
	private static final String DB_USER = "postgres";
	private static final String DB_PASS = "psql";
	
	public boolean create(Member member) {	
	try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

		String sql = "INSERT INTO members (member_id, password, member_name, postal_code, "
			  	+ "address, phone_number, birth_date, email, payment_method) "
			  	+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pStmt = conn.prepareStatement(sql);

		pStmt.setString(1, member.getMember_id());
		pStmt.setString(2, member.getPassword());
		pStmt.setString(3, member.getMember_name());
		pStmt.setString(4, member.getPostal_code());
		pStmt.setString(5, member.getAddress());
		pStmt.setString(6, member.getPhone_number());
		pStmt.setString(7, member.getBirth_date());
		pStmt.setString(8, member.getEmail());
		pStmt.setString(9, member.getPayment_method());
		
		
		int result =pStmt.executeUpdate();
		if (result != 1) {
			return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean existsByMemberId(String memberId) {
		// SELECT COUNT(*) を使って、指定されたIDが存在するか確認します
		String sql = "SELECT COUNT(*) FROM members WHERE member_id = ?";

		try (Connection conn = DriverManager.getConnection(
			JDBC_URL, DB_USER, DB_PASS);
		PreparedStatement pStmt = conn.prepareStatement(sql)) {

		pStmt.setString(1, memberId);

		try (ResultSet rs = pStmt.executeQuery()) {
		if (rs.next()) {
		// 件数が 0 より大きければ「重複あり(true)」
			return rs.getInt(1) > 0;
			}
		  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}