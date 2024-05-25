package com.service;

import com.connection.DatabaseConnection;
import com.model.ModelMessage;
import com.model.ModelRegister;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUser {

    private final Connection con;

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public ModelMessage register(ModelRegister data) {
        ModelMessage message = new ModelMessage();
        PreparedStatement checkUserStmt = null;
        ResultSet resultSet = null;

        try {
            String CHECK_USER = "SELECT UserID FROM Users WHERE UserName = ? LIMIT 1";
            checkUserStmt = con.prepareStatement(CHECK_USER);
            checkUserStmt.setString(1, data.getUserName());
            resultSet = checkUserStmt.executeQuery();

            if (resultSet.next()) {
                message.setAction(false);
                message.setMessage("User Already Exists");
            } else {
                String INSERT_USER = "INSERT INTO Users (UserName, Password) VALUES (?, ?)";
                try (PreparedStatement insertUserStmt = con.prepareStatement(INSERT_USER)) {
                    insertUserStmt.setString(1, data.getUserName());
                    insertUserStmt.setString(2, data.getPassword());
                    insertUserStmt.executeUpdate();
                    message.setAction(true);
                    message.setMessage("Ok");
                }
            }
        } catch (SQLException e) {
            message.setAction(false);
            message.setMessage("Server Error");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (checkUserStmt != null) checkUserStmt.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return message;
    }
}
