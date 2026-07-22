package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.FormContact;

@Repository
public class FormContactDAO {

    /*
     * PostgreSQL接続情報
     */
    private static final String JDBC_URL =
            "jdbc:postgresql://localhost:5432/groupb_project";

    private static final String DB_USER =
            "postgres";

    private static final String DB_PASS =
            "psql";


    /**
     * お問い合わせ内容を登録する
     *
     * @param contact お問い合わせ情報
     * @return 登録成功：true
     *         登録失敗：false
     */
    public boolean insert(
            FormContact contact) {

        String sql =
                "INSERT INTO form_contact "
              + "(customer_name, subject, email, phone, message) "
              + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    contact.getCustomerName());

            ps.setString(
                    2,
                    contact.getSubject());

            ps.setString(
                    3,
                    contact.getEmail());

            ps.setString(
                    4,
                    contact.getPhone());

            ps.setString(
                    5,
                    contact.getMessage());

            int result =
                    ps.executeUpdate();

            return result == 1;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    /**
     * お問い合わせ内容をすべて取得する
     *
     * @return お問い合わせ一覧
     */
    public List<FormContact> findAll() {

        List<FormContact> contactList =
                new ArrayList<>();

        String sql =
                "SELECT "
              + "contact_id, "
              + "customer_name, "
              + "subject, "
              + "email, "
              + "phone, "
              + "message "
              + "FROM form_contact "
              + "ORDER BY contact_id DESC";

        try (
            Connection conn =
                    DriverManager.getConnection(
                            JDBC_URL,
                            DB_USER,
                            DB_PASS);

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                FormContact contact =
                        new FormContact();

                contact.setContactId(
                        rs.getInt("contact_id"));

                contact.setCustomerName(
                        rs.getString("customer_name"));

                contact.setSubject(
                        rs.getString("subject"));

                contact.setEmail(
                        rs.getString("email"));

                contact.setPhone(
                        rs.getString("phone"));

                contact.setMessage(
                        rs.getString("message"));

                contactList.add(contact);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return contactList;
    }
}