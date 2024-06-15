package com.service;

import com.connection.DatabaseConnection;
import com.model.ModelMessage;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class ServiceChatHistory {

    private final Connection con;
    private final Gson gson;

    public ServiceChatHistory() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.gson = new Gson();
    }

    public void addMessage(ModelMessage message) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, receiver_id, message, timestamp, action, data) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, message.getSenderId());
            p.setInt(2, message.getReceiverId());
            p.setString(3, message.getMessage());
            p.setTimestamp(4, message.getTimestamp());
            p.setBoolean(5, message.isAction());
            String dataJson = gson.toJson(message.getData());
            p.setString(6, dataJson);
            p.executeUpdate();
        }
    }

    public List<ModelMessage> getMessages(int userId1, int userId2) throws SQLException {
        String sql = "SELECT message_id, sender_id, receiver_id, message, timestamp, action, data FROM chat_history WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp";
        List<ModelMessage> messages = new ArrayList<>();
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, userId1);
            p.setInt(2, userId2);
            p.setInt(3, userId2);
            p.setInt(4, userId1);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt("message_id");
                    int senderId = rs.getInt("sender_id");
                    int receiverId = rs.getInt("receiver_id");
                    String content = rs.getString("message");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    boolean action = rs.getBoolean("action");
                    String dataJson = rs.getString("data");
                    Object data = gson.fromJson(dataJson, Object.class);
                    ModelMessage message = new ModelMessage(messageId, senderId, receiverId, content, timestamp, action, data);
                    messages.add(message);
                }
            }
        }
        return messages;
    }
}
