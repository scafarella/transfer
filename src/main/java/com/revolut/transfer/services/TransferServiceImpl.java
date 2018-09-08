package com.revolut.transfer.services;


import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.exceptions.BalanceNotEnoughException;
import com.revolut.transfer.models.Account;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import java.util.Objects;

import static com.revolut.transfer.Constants.ACCOUNT_NOT_VALID;
import static com.revolut.transfer.Constants.BALANCE_NOT_ENOUGH;

public class TransferServiceImpl implements TransferService{

    @Inject
    private AccountDAO accountDAO;

    @Override
    public void transfer(final Long fromAccountNumber, final Long toAccountNumber, final Long amount) throws AccountNotFoundException, BalanceNotEnoughException {
        final EntityTransaction transaction = getAccountDAO().getTransaction();

        try {
            transaction.begin();

            Account fromAccount = getAccountDAO().findByID(fromAccountNumber);
            if(Objects.isNull(fromAccount)){
                throw new AccountNotFoundException(ACCOUNT_NOT_VALID);
            }

            Account toAccount = getAccountDAO().findByID(toAccountNumber);
            if(Objects.isNull(toAccount)) {
                throw new AccountNotFoundException(ACCOUNT_NOT_VALID);
            }

            if(Long.compare(fromAccount.getBalance(), amount) < 0){
                throw new BalanceNotEnoughException(BALANCE_NOT_ENOUGH);
            }

            getAccountDAO().add(toAccount, amount);
            getAccountDAO().subtract(fromAccount, amount);

            transaction.commit();
        }catch (Exception e ) {
            transaction.rollback();
            throw e;

        }

        return;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}
