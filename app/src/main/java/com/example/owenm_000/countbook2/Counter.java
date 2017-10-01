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
        lastEdit = getDate();
        comment = "";
    }

    public Counter(String name, int currValue, int initValue, Date lastEdit, String comment) {
        this.name = name;
        this.currValue = currValue;
        this.initValue = initValue;
        this.lastEdit = lastEdit;
        this.comment = comment;
    }

    public int getInitValue() {
        return initValue;
    }

    public Date getLastEdit() {
        return lastEdit;
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
        return new Date() ;
    }
}
