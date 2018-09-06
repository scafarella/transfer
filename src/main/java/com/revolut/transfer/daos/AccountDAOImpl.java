package com.revolut.transfer.daos;

import com.revolut.transfer.models.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountDAOImpl extends AbstractDAO implements AccountDAO {

    @Override
    public Account findByID(Long id) {
        return getEntityManager().find(Account.class, id);
    }

    @Override
    public void add(Account account, Double amount) {
        Double balance = account.getBalance();
        BigDecimal balanceDecimal = BigDecimal.valueOf(balance);
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        BigDecimal updatedBalanceDecimal = balanceDecimal.add(amountDecimal);
        account.setBalance(updatedBalanceDecimal.doubleValue());
        getEntityManager().merge(account);

    }

    @Override
    public void subtract(Account account, Double amount) {
        Double balance = account.getBalance();
        BigDecimal balanceDecimal = BigDecimal.valueOf(balance);
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        BigDecimal updatedBalanceDecimal = balanceDecimal.subtract(amountDecimal);
        account.setBalance(updatedBalanceDecimal.doubleValue());
        getEntityManager().merge(account);
    }

    @Override
    public List<Account> findAll() {
        return getEntityManager().createQuery("SELECT a from Account a").getResultList();
    }
}
