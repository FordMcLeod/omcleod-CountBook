package com.example.owenm_000.countbook2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by owenm_000 on 9/30/2017.
 */

public interface CounterHandler extends Serializable {
    // neccesary interface to have us pass the current counter to DetailsActivity
    // Could be developed further in the future to seperate the underlying counter object from it's interactions.
    public int getValue();
    public Date getDate();
    public String getName();
}
