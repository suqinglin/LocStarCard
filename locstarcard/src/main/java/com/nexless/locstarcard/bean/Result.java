package com.nexless.locstarcard.bean;

import com.nexless.locstarcard.Utils.Constants;

/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description:
 */
public class Result {

    private int result = Constants.STATUS_SUCC;

    public int getResultCode() {
        return result;
    }

    public void setResultCode(int result) {
        this.result = result;
    }
}
