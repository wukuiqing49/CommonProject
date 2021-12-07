package com.wu.network.model;




import java.io.Serializable;

public interface IBaseInfo<T> extends Serializable {
    String getCode();

    String getMessage();

    T getData();
}
