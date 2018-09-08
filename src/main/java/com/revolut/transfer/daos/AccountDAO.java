package com.revolut.transfer.daos;

import com.revolut.transfer.models.Account;

import java.util.List;

public interface AccountDAO extends DAO{
    Account findByID(Long id);

    void add(Account toAccount, Long amount);

    void subtract(Account fromAccount, Long amount);

    List<Account> findAll();
}
