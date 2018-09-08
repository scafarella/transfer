package com.revolut.transfer.services;

import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.exceptions.BalanceNotEnoughException;
import com.revolut.transfer.models.Account;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;


import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    private final AccountDAO accountDAO = mock(AccountDAO.class);
    private final EntityTransaction entityTransaction = mock(EntityTransaction.class);

    @Inject
    private TransferService transferService;

    private final static Long ACCOUNT_WITH_NO_FUNDS = 111L;
    private final static Long FROM_ACCOUNT = 110L;
    private final static Long TO_ACCOUNT = 112L;

    private Account fromAccountWithNoFunds;
    private Account fromAccount;
    private Account toAccount;

    @Before
    public void setup(){
        when(accountDAO.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityTransaction).begin();

        Binder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(TransferServiceImpl.class).to(TransferService.class);

                bindFactory(new Factory<AccountDAO>() {

                    @Override
                    public AccountDAO provide() {
                        return accountDAO;
                    }

                    @Override
                    public void dispose(AccountDAO accountDAO) {

                    }

                }).to(AccountDAO.class);

            }
        };

        fromAccountWithNoFunds = new Account();
        fromAccountWithNoFunds.setBalance(100l);
        fromAccountWithNoFunds.setId(ACCOUNT_WITH_NO_FUNDS);

        fromAccount = new Account();
        fromAccount.setBalance(10000l);
        fromAccount.setId(FROM_ACCOUNT);

        toAccount = new Account();
        toAccount.setBalance(10000l);
        toAccount.setId(TO_ACCOUNT);

        when(accountDAO.findByID(ACCOUNT_WITH_NO_FUNDS)).thenReturn(fromAccountWithNoFunds);
        when(accountDAO.findByID(TO_ACCOUNT)).thenReturn(toAccount);
        when(accountDAO.findByID(FROM_ACCOUNT)).thenReturn(fromAccount);

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        ServiceLocatorUtilities.bind(locator, binder);
        locator.inject(this);
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowAccountNotValid() throws AccountNotFoundException, BalanceNotEnoughException {
        transferService.transfer(0l ,0l,0l);

    }

    @Test(expected = BalanceNotEnoughException.class)
    public void shouldThrowABalanceNotEnough() throws AccountNotFoundException, BalanceNotEnoughException {
        transferService.transfer(ACCOUNT_WITH_NO_FUNDS ,TO_ACCOUNT,1000l);

    }

    @Test
    public void shouldTransferAmount() throws AccountNotFoundException, BalanceNotEnoughException {
        Long amount = 100l;
        transferService.transfer(FROM_ACCOUNT ,TO_ACCOUNT,amount);
        verify(accountDAO, times(1)).add(toAccount, amount);
        verify(accountDAO, times(1)).subtract(fromAccount, amount);
        verify(entityTransaction, times(1)).commit();

    }
}
