package com.example.owenm_000.countbook2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by owenm_000 on 9/30/2017.
 */

public interface CounterHandler extends Serializable {
    public int getValue();
    public Date getDate();
    public String getName();
}
