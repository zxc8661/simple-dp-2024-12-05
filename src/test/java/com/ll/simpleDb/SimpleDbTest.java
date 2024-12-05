package com.ll.simpleDb;


import org.junit.jupiter.api.*;

import java.util.stream.IntStream;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class SimpleDbTest {

    private static SimpleDb simpleDb;

    @BeforeAll
    public static void beforeAll() {
        simpleDb = new SimpleDb("localhost", "root", "lldj123414", "simpleDb__test");

       createArticleTable();
    }

    @BeforeEach
    public void beforeEach(){
        truncateArticleTable();
        makeArticleTestData();
    }

    private static void createArticleTable() {
        simpleDb.run("DROP TABLE IF EXISTS article");

        simpleDb.run("""
                CREATE TABLE article (
                    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                    PRIMARY KEY(id),
                    createdDate DATETIME NOT NULL,
                    modifiedDate DATETIME NOT NULL,
                    title VARCHAR(100) NOT NULL,
                    `body` TEXT NOT NULL,
                    isBlind BIT(1) NOT NULL DEFAULT 0
                )
                """);
    }

    private void makeArticleTestData(){
        IntStream.rangeClosed(1,6).forEach(no->{
            boolean isBlind = no>3;
            String title = "제목%d".formatted(no);
            String body ="내용%d".formatted(no);

            simpleDb.run("""
                    insert into article
                    set createdDate = now(),
                    modifiedDate = now(),
                    title = ?,
                    `body` = ?,
                    isBlind = ?""",title,body,isBlind);
        });
    }

    private void truncateArticleTable(){
        simpleDb.run("truncate article");
    }

    @Test
    @DisplayName("데이터베이스 연결 테스트")
    public void t000(){

    }

    @Test
    @DisplayName("insert")
    public void t001() {
        Sql sql = simpleDb.genSql();
        /*
        == rawSql ==
        INSERT INTO article
        SET createdDate = NOW() ,
        modifiedDate = NOW() ,
        title = '제목 new' ,
        body = '내용 new'
        */
        sql.append("INSERT INTO article")
                .append("SET createdDate = NOW()")
                .append(", modifiedDate = NOW()")
                .append(", title = ?", "제목 new")
                .append(", body = ?", "내용 new");

        long newId = sql.insert(); // AUTO_INCREMENT 에 의해서 생성된 주키 리턴

        assertThat(newId).isGreaterThan(0);
    }
}

