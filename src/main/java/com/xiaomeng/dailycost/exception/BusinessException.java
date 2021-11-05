package com.xiaomeng.dailycost.exception;

import com.xiaomeng.dailycost.base.ReturnCode;

public class BusinessException extends BaseException{
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERROR_CODE = 510;

    public BusinessException(ReturnCode returnCode) {
        super(returnCode.getCode(), returnCode.getMessage());
    }

    public BusinessException(String errorMessage){
        super(DEFAULT_ERROR_CODE,errorMessage);
    }

    public BusinessException(int errorCode, String errorMessage){
        super(errorCode,errorMessage);
    }

    public BusinessException(String errorMessage, Throwable e){
        super(errorMessage,e);
    }

    public BusinessException(int errorCode, String errorMessage, Throwable e){
        super(errorCode,errorMessage,e);
    }
}
