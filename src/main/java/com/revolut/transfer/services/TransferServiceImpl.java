package com.revolut.transfer.services;


import com.revolut.transfer.daos.AccountDAO;

import javax.inject.Inject;

public class TransferServiceImpl implements TransferService{

    @Inject
    private AccountDAO accountDAO;

    @Override
    public void transfer(Long fromAccountNumber, Long toAccountNumber, Long amount) {
        return;
    }
}
