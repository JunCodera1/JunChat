package com.controller.service;

import com.controller.connection.DatabaseConnection;
import com.model.encrypt.EncryDecry;
import com.model.ModelClient;
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

public class ServiceUserController {

    // Instance
    private final Connection con;
    private final EncryDecry encoder;
    String secretKeyPassword = "`Xb28If#-dDeIwr'7-UqbDwDn,F8D1d=PnG+0%?Py1*Pau+#~RoXqd:*[QF6y8U";

    public ServiceUserController() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.encoder = new EncryDecry();
    }

    public ModelMessage register(ModelRegister data) {
        ModelMessage message = new ModelMessage();
        PreparedStatement p = null;
        ResultSet r = null;

        try {
            // Tạo PreparedStatement để kiểm tra tên người dùng đã tồn tại chưa
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
                // Encrypt the password
                String passwordEncrypt = encoder.encrypt(data.getPassword(), secretKeyPassword);
                // Nếu tên người dùng chưa tồn tại, thêm người dùng mới vào cơ sở dữ liệu
                con.setAutoCommit(false);
                p = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, passwordEncrypt);
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
                    // Gửi tin nhắn xác nhận đăng ký thành công
                    message.setAction(true);
                    message.setMessage("Registration Successful");
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
                if (r != null) {
                    r.close();
                }
                if (p != null) {
                    p.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public ModelUserAccount login(ModelLogin data) {
        ModelUserAccount userAccount = null;
        PreparedStatement p = null;
        ResultSet r = null;

        try {
            // Mã hóa mật khẩu nhập vào trước khi so sánh
            String encryptedPassword = encoder.encrypt(data.getPassword(), secretKeyPassword);

            // Tạo PreparedStatement để kiểm tra thông tin đăng nhập
            p = con.prepareStatement(LOGIN, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            p.setString(1, data.getUserName());
            p.setString(2, encryptedPassword); // So sánh với mật khẩu đã mã hóa
            r = p.executeQuery();

            if (r.first()) {
                int userID = r.getInt(1);
                String userName = r.getString(2);
                String gender = r.getString(3);
                String image = r.getString(4);
                userAccount = new ModelUserAccount(userID, userName, gender, image, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
                if (p != null) {
                    p.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userAccount;
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
                list.add(new ModelUserAccount(userID, userName, gender, image, checkUserStatus(userID)));
            }
        } finally {
            if (r != null) {
                r.close();
            }
            if (p != null) {
                p.close();
            }
        }
        return list;
    }

    private boolean checkUserStatus(int userID) {
        List<ModelClient> clients = ServiceController.getInstance(null).getListClient();
        for (ModelClient c : clients) {
            if (c.getUser().getUserID() == userID) {
                return true;
            }
        }
        return false;
    }
    // SQL Queries
    private static final String INSERT_MESSAGES = "INSERT INTO messages (sender, recipient, message, timestamp) VALUES (?, ?, ?, NOW())";
    private static final String LOGIN = "SELECT UserID, user_account.UserName, Gender, ImageString FROM user JOIN user_account USING (UserID) WHERE user.UserNAME=BINARY(?) AND user.Password=BINARY(?) AND user_account.Status = '1'";
    private static final String SELECT_USER_ACCOUNT = "SELECT UserID, UserName, Gender, ImageString FROM user_account WHERE user_account.`Status`='1' AND UserID<>?";
    private static final String INSERT_USER = "INSERT INTO user (UserName, `Password`) VALUES (?,?)";
    private static final String INSERT_USER_ACCOUNT = "INSERT INTO user_account (UserID, UserName) VALUES (?,?)";
    private static final String CHECK_USER = "SELECT UserID FROM user WHERE UserName =? LIMIT 1";
}
