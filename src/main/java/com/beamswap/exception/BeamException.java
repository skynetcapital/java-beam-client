package com.beamswap.exception;

public class BeamException extends RuntimeException {

    public BeamException(){
    }

    public BeamException(final String message) {
        super(message);
    }
}
