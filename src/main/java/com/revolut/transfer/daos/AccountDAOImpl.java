package com.revolut.transfer.daos;

import com.revolut.transfer.models.Account;

import java.util.List;

public class AccountDAOImpl extends AbstractDAO implements AccountDAO {

    @Override
    public Account findByID(Long id) {
        return getEntityManager().find(Account.class, id);
    }

    @Override
    public void add(Account account, Long amount) {
        Long balance = account.getBalance();
        account.setBalance(Long.sum(balance, amount));
        getEntityManager().merge(account);

    }

    @Override
    public void subtract(Account account, Long amount) {
        Long balance = account.getBalance();
        account.setBalance(balance - amount);
        getEntityManager().merge(account);
    }

    @Override
    public List<Account> findAll() {
        return getEntityManager().createQuery("SELECT a from Account a").getResultList();
    }
}
