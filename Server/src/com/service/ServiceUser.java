package com.service;

import com.connection.DatabaseConnection;
import com.model.ModelLogin;
import com.model.ModelMessage;
import com.model.ModelRegister;
import com.model.ModelUserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public ModelMessage register(ModelRegister data) {
        ModelMessage message = new ModelMessage();
        PreparedStatement p = null;
        ResultSet r = null;

        try {
            // Create PreparedStatement with scrollable ResultSet
            p = con.prepareStatement(CHECK_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            p.setString(1, data.getUserName());
            r = p.executeQuery();
            if (r.first()) {
                message.setAction(false);
                message.setMessage("User Already Exists");
            } else {
                message.setAction(true);
            }
            r.close();
            p.close();
            if (message.isAction()) {
                con.setAutoCommit(false);
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                r = p.getGeneratedKeys();
                if (r.first()) {
                    int userID = r.getInt(1);
                    r.close();
                    p.close();
                    p = con.prepareStatement(INSERT_USER_ACCOUNT);
                    p.setInt(1, userID);
                    p.setString(2, data.getUserName());
                    p.execute();
                    p.close();
                    con.commit();
                    con.setAutoCommit(true);
                    message.setAction(true);
                    message.setMessage("Ok");
                    message.setData(new ModelUserAccount(userID, data.getUserName(), "", "", true));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if (!con.getAutoCommit()) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (r != null) r.close();
                if (p != null) p.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public ModelUserAccount login(ModelLogin login) throws SQLException {
        ModelUserAccount data = null;
        PreparedStatement p = null;
        ResultSet r = null;

        try {
            // Create PreparedStatement with scrollable ResultSet
            p = con.prepareStatement(LOGIN, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            p.setString(1, login.getUserName());
            p.setString(2, login.getPassword());
            r = p.executeQuery();

            if (r.first()) {
                int userID = r.getInt(1);
                String userName = r.getString(2);
                String gender = r.getString(3);
                String image = r.getString(4);
                data = new ModelUserAccount(userID, userName, gender, image, true);
            }
        } finally {
            if (r != null) r.close();
            if (p != null) p.close();
        }
        return data;
    }

    public List<ModelUserAccount> getUser(int exitUser) throws SQLException {
        List<ModelUserAccount> list = new ArrayList<>();
        PreparedStatement p = null;
        ResultSet r = null;

        try {
            p = con.prepareStatement(SELECT_USER_ACCOUNT);
            p.setInt(1, exitUser);
            r = p.executeQuery();

            while (r.next()) {
                int userID = r.getInt(1);
                String userName = r.getString(2);
                String gender = r.getString(3);
                String image = r.getString(4);
                list.add(new ModelUserAccount(userID, userName, gender, image, true));
            }
        } finally {
            if (r != null) r.close();
            if (p != null) p.close();
        }
        return list;
    }

    // SQL Queries
    private static final String LOGIN = "SELECT UserID, user_account.UserName, Gender, ImageString FROM user JOIN user_account USING (UserID) WHERE user.UserNAME=BINARY(?) AND user.Password=BINARY(?) AND user_account.Status = '1'";
    private static final String SELECT_USER_ACCOUNT = "SELECT UserID, UserName, Gender, ImageString FROM user_account WHERE user_account.`Status`='1' AND UserID<>?";
    private static final String INSERT_USER = "INSERT INTO user (UserName, `Password`) VALUES (?,?)";
    private static final String INSERT_USER_ACCOUNT = "INSERT INTO user_account (UserID, UserName) VALUES (?,?)";
    private static final String CHECK_USER = "SELECT UserID FROM user WHERE UserName =? LIMIT 1";

    // Instance
    private final Connection con;
}
