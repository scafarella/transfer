package com.revolut.transfer.daos;

import com.revolut.transfer.models.Account;

import java.util.List;

public interface AccountDAO extends DAO{
    Account findByID(Long id);

    void add(Account toAccount, Double amount);

    void subtract(Account fromAccount, Double amount);

    List<Account> findAll();
}
