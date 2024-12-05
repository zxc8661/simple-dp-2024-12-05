package com.ll.simpleDb;


import lombok.RequiredArgsConstructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RequiredArgsConstructor   //final에 생성자가 생김
public class SimpleDb {
    private final String host;
    private final String username;
    private final String password;
    private final String dbname;

    private Connection getConnection() throws SQLException {
        // JDBC URL 구성
        String url = String.format("jdbc:mysql://%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", host, dbname);
        return DriverManager.getConnection(url, username, password);
    }

    public void run(String sql) {
        try (
             Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.execute(); // SQL 실행
        } catch (SQLException e) {
            e.printStackTrace(); // 에러 로그 출력
            throw new RuntimeException("DB 실행 중 오류 발생: " + e.getMessage());
        }
    }
}
