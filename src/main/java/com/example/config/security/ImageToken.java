package com.example.config.security;

import java.io.Serializable;

public class ImageToken implements Serializable {
    public static final int EXPIRE = 1000 * 60 * 5; // 有效时间 5 分钟，单位毫秒
    public static final int SIZE = 40; // 被序列化以后占用多少个字节
    private static final long serialVersionUID = 1L;
    private long id;

    private int flags;

    private int uid;

    private long gid;

    private long expire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
