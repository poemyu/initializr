package com.croot.demo.comm;

import com.croot.framework.comm.IResponse;

/**
 * 返回状态枚举类
 * 
 * @author 1000~1999 业务级别
 */
public enum RespCode implements IResponse {
    DATE_TYPE_MISS(1000, "日期类型不存在");

    private int code;
    private String msg;

    RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
