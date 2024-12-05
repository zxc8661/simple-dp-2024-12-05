package com.ll.simpleDb;

public class SimpleDb {
    private String host;
    private String username;
    private String password;
    private String dbname;
    public SimpleDb(String host, String username, String password,String dbname){
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbname = dbname;
    }
}
