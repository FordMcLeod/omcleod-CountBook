package com.example.owenm_000.countbook2;

import java.util.Date;

/**
 * Created by owenm_000 on 9/30/2017.
 */


// Counter class which will represent a counter

public class Counter implements CounterHandler {

    // attributes
    private String name;
    private int currValue;
    private int initValue;
    private Date lastEdit;
    private String comment;

    // Base constructor used for making a new Counter
    public Counter() {
        name ="";
        currValue = 0;
        initValue=0;
        lastEdit = new Date();
        comment = "";
    }


    // Constructor with inputs
    public Counter(String name, int currValue, int initValue, String comment) {
        this.name = name;
        this.currValue = currValue;
        this.initValue = initValue;
        this.lastEdit = new Date();
        this.comment = comment;
    }


    // Getters
    public int getInitValue() {
        return initValue;
    }

    public String getComment() {
        return comment;
    }


    public String getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return this.lastEdit ;
    }

    @Override
    public int getValue() {

        return currValue;
    }


    // Utilized when printing to the screen
    // In the future should be transitioned to the CounterHandler
    // To seperate formatting and the underlying data
    @Override
    public String toString() {
        return (String) (this.lastEdit + "\n" +this.name + "\n Count:" + this.currValue) ;
    }

}
