package com.example;

import java.util.Map;

/**
 * Created by yy on 11/2/15.
 */
public class Api {
    public static final int SUCCESS = 0;
    public static final int ERR_PHONE_EXISTED = 1;
    public static class Result {
        private int returnCode;
        private Map<String, Object> params;
        private Result(int returnCode){
            this.returnCode = returnCode;
        }
        public static class ResultNeedValue {
            private Result result;
            private String lastParamName;
            private ResultNeedValue (Result result, String paramName){
                this.result = result;
                this.lastParamName = paramName;
            }
            public Result value(Object value){
                result.params.put(lastParamName, value);
                return result;
            }
        }
        public ResultNeedValue param(String name){
            return new ResultNeedValue(this, name);
        }
    }
    public static Result result(int returnCode){
        return new Result(returnCode);
    }
}
