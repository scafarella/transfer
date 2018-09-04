package com.revolut.transfer.services;

public interface TransferService {
    void transfer(Long fromAccountNumber, Long toAccountNumber, Long amount);
}
