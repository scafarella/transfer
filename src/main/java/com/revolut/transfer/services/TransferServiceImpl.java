package com.revolut.transfer.services;


import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.exceptions.BalanceNotEnoughException;
import com.revolut.transfer.models.Account;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.Objects;

import static com.revolut.transfer.Constants.ACCOUNT_NOT_VALID;
import static com.revolut.transfer.Constants.BALANCE_NOT_ENOUGH;

public class TransferServiceImpl implements TransferService{

    @Inject
    private AccountDAO accountDAO;

    @Override
    public void transfer(final Long fromAccountNumber, final Long toAccountNumber, final Double amount) throws AccountNotFoundException, BalanceNotEnoughException {

        final EntityTransaction transaction = accountDAO.getTransaction();

        try {
            transaction.begin();

            Account fromAccount = accountDAO.findByID(fromAccountNumber);
            if(Objects.isNull(fromAccount)){
                throw new AccountNotFoundException(ACCOUNT_NOT_VALID);
            }

            Account toAccount = accountDAO.findByID(toAccountNumber);
            if(Objects.isNull(toAccount)) {
                throw new AccountNotFoundException(ACCOUNT_NOT_VALID);
            }

            if(fromAccount.getBalance().compareTo(amount) < 0){
                throw new BalanceNotEnoughException(BALANCE_NOT_ENOUGH);
            }

            accountDAO.add(toAccount, amount);
            accountDAO.subtract(fromAccount, amount);

            transaction.commit();
        }catch (Exception e ) {
            transaction.rollback();
            throw e;

        }

        return;
    }
}
