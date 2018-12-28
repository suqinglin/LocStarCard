package com.nexless.locstarcard.bean;

/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description: 接口返回结果基类
 */
public class Result {

    public static final int STATUS_SUCC             = 10000; // Successful
    public static final int STATUS_AUTH_FAIL        = 20000; // The password is error
    public static final int STATUS_NO_CARD_FOUND    = 20001; // No card was found
    public static final int STATUS_DATA_LOSE        = 20002; // Read data lose
    public static final int STATUS_WRITE_FAIL       = 20003; // Write data failure

    private int result;

    /**
     * 获取结果编号
     * @return 结果编号
     */
    public int getResultCode() {
        return result;
    }

    public void setResultCode(int result) {
        this.result = result;
    }
}
