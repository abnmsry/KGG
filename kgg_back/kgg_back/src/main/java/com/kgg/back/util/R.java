package com.kgg.back.util;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    public R ok(){
        this.setMsg("success");
        this.setCode(200);
        this.setData(null);
        return this;
    }

    public R<T> ok(T data){
        this.setMsg("success");
        this.setCode(200);
        this.setData(data);
        return this;
    }

    public R<T> error(Integer code, String msg){
        this.setMsg(msg);
        this.setCode(code);
        this.setData(null);
        return this;
    }
}
