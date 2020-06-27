package com.beamswap.exception;

public class BeamException extends RuntimeException {

    private double errorCode;
    private String errorData;

    public BeamException(){
    }

    public BeamException(final String message) {
        super(message);
    }

    public BeamException(final String message, final double errorCode, final String errorData) {
        super(message);
        this.errorCode = errorCode;
        this.errorData = errorData;
    }

    public double getErrorCode() {
        return errorCode;
    }

    public String getErrorData() {
        return errorData;
    }
}
