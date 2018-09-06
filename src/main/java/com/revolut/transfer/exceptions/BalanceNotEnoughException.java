package com.revolut.transfer.exceptions;

public class BalanceNotEnoughException extends Exception{

    public BalanceNotEnoughException(String message) {
        super(message);
    }

}
