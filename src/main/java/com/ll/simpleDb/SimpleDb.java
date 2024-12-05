package com.ll.simpleDb;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor   //final에 생성자가 생김
public class SimpleDb {
    private final String host;
    private final String username;
    private final String password;
    private final String dbname;

}
