package com.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by yy on 10/29/15.
 */
public class ApiResult {
    public static final int SUCCESS = 0;
    public static final int ERR_PHONE_EXISTED = 1;

    @JsonProperty("return")
    private int returnCode;

    public ApiResult(int returnCode){
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
