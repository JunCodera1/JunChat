# Jun Chat

Jun Chat là một ứng dụng chat desktop được phát triển bằng Java Swing. Ứng dụng này cho phép người dùng gửi và nhận tin nhắn trong thời gian thực, quản lý tài khoản người dùng, và lưu trữ lịch sử tin nhắn trong cơ sở dữ liệu.

## Tính Năng

- **Đăng ký và Đăng nhập**: Người dùng có thể tạo tài khoản mới và đăng nhập vào ứng dụng.
- **Chat Một-một**: Gửi và nhận tin nhắn giữa hai người dùng.
- **Lưu Trữ Tin Nhắn**: Lưu trữ lịch sử tin nhắn trong cơ sở dữ liệu.
- **Giao Diện Người Dùng Bằng Java Swing**: Giao diện thân thiện và dễ sử dụng.
- **Kết Nối Mạng**: Kết nối giữa các máy khách thông qua socket.
  

## Yêu Cầu Hệ Thống

- **Java Development Kit (JDK)** 8 trở lên
- **MySQL** hoặc bất kỳ hệ quản trị cơ sở dữ liệu nào hỗ trợ JDBC
- **Thư viện MySQL Connector/J** (nếu sử dụng MySQL)

## Hướng Dẫn Cài Đặt

### 1. Thiết Lập Cơ Sở Dữ Liệu

1. Tải và cài đặt MySQL.
2. Tạo cơ sở dữ liệu mới:
    ```sql
    CREATE DATABASE jun_chat;
    ```
3. Tạo bảng người dùng và tin nhắn:
    ```sql
    USE jun_chat;

    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(100) NOT NULL
    );

    CREATE TABLE messages (
        id INT AUTO_INCREMENT PRIMARY KEY,
        sender_id INT,
        receiver_id INT,
        message TEXT,
        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (sender_id) REFERENCES users(id),
        FOREIGN KEY (receiver_id) REFERENCES users(id)
    );
    ```

### 2. Cài Đặt Ứng Dụng

1. Clone hoặc tải xuống repository:
    ```sh
    git clone https://github.com/username/jun-chat.git
    cd jun-chat
    ```

2. Thêm thư viện MySQL Connector/J vào classpath.

3. Cấu hình kết nối cơ sở dữ liệu trong mã nguồn:
    Mở file `DatabaseConfig.java` và thay đổi thông tin kết nối phù hợp:
    ```java
    public class DatabaseConfig {
        public static final String URL = "jdbc:mysql://localhost:3306/jun_chat";
        public static final String USER = "your_username";
        public static final String PASSWORD = "your_password";
    }
    ```

4. Biên dịch và chạy ứng dụng:
    ```sh
    javac -cp ".:path_to_mysql_connector.jar" com/jun/chat/*.java
    java -cp ".:path_to_mysql_connector.jar" com.jun.chat.Main
    ```

## Hướng Dẫn Sử Dụng

1. Mở ứng dụng và đăng ký tài khoản mới nếu bạn chưa có.
2. Đăng nhập bằng tài khoản đã tạo.
3. Bắt đầu trò chuyện với các người dùng khác bằng cách nhập tên người dùng của họ và gửi tin nhắn.

## Cấu Trúc Thư Mục

- `src/`: Chứa mã nguồn của ứng dụng.
    

## Đóng Góp

Nếu bạn muốn đóng góp vào dự án, vui lòng fork repository này và gửi pull request. Chúng tôi hoan nghênh mọi ý kiến đóng góp và cải tiến.

## Giấy Phép

Dự án này được cấp phép theo giấy phép MIT. Xem file [LICENSE](LICENSE) để biết thêm chi tiết.
