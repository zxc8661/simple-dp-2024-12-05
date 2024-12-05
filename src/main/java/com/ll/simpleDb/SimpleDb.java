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
        String url = String.format(
                "jdbc:mysql://%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                host,
                dbname
        );
        return DriverManager.getConnection(url, username, password);
    }

    public void run(String sql, Object... params) {
        try (
                Connection connection = getConnection();                  // 연결 생성
                PreparedStatement statement = connection.prepareStatement(sql) // SQL 준비
        ) {
            // SQL에 포함된 `?`를 params 값으로 설정
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);  // PreparedStatement의 인덱스는 1부터 시작
            }

            // SQL 실행
          statement.execute();
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "SQL 실행중 오류 발생 : [%s]. 원본 SQL: [%s]",
                    e.getMessage(),
                    sql
            );
            throw new RuntimeException("DB 실행 중 오류 발생: " + e.getMessage());
        }
    }

    public Sql genSql() {
        return new Sql();
    }


}
