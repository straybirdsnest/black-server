package com.example.exceptions;

public class PersistEntityException extends SystemException {

    public PersistEntityException(Class entityClass) {
        super("无法持久化实体：" + entityClass.getName());
    }

}
