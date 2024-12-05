package com.ll.simpleDb;

public class Sql {
    public Sql append(String sqlBit) {
        return this;
    }

    public Sql append(String sqlBit,Object ... prams){
        return this ;
    }

    public long insert() {
        return 1;
    }

    public int update() {
        return 3;
    }
}
