package com.revolut.transfer.services;

import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.exceptions.BalanceNotEnoughException;

public interface TransferService {
    void transfer(final Long fromAccountNumber, final Long toAccountNumber, final Long amount) throws AccountNotFoundException, BalanceNotEnoughException;
}
