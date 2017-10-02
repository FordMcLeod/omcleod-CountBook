package com.example.owenm_000.countbook2;

import java.util.Date;

/**
 * Created by owenm_000 on 9/30/2017.
 */

public class Counter implements CounterHandler {
    private String name;
    private int currValue;
    private int initValue;
    private Date lastEdit;
    private String comment;

    public Counter() {
        name ="";
        currValue = 0;
        initValue=0;
        lastEdit = new Date();
        comment = "";
    }

    public Counter(String name, int currValue, int initValue, String comment) {
        this.name = name;
        this.currValue = currValue;
        this.initValue = initValue;
        this.lastEdit = new Date();
        this.comment = comment;
    }

    public int getInitValue() {
        return initValue;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int getValue() {

        return currValue;
    }

    @Override
    public String toString() {
        return (String) (this.lastEdit + "\n" +this.name) ;
    }

    public String getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return this.lastEdit ;
    }
}
