package com.revolut.transfer.daos;

import com.revolut.transfer.models.Account;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountDAOImplTest {

    @Inject
    private AccountDAO accountDAO;

    private EntityManager entityManager = mock(EntityManager.class);

    private final static Long ACCOUNT = 110L;
    private final static Long BALANCE = 1000L;

    private final static Long AMOUNT = 100L;

    private Account account;

    @Before
    public void setup(){
        Binder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(AccountDAOImpl.class).to(AccountDAO.class);

                bindFactory(new Factory<EntityManager>() {

                    @Override
                    public EntityManager provide() {
                        return entityManager;
                    }

                    @Override
                    public void dispose(EntityManager entityManager) {

                    }


                }).to(EntityManager.class);

            }
        };

        account = new Account();
        account.setBalance(BALANCE);
        account.setId(ACCOUNT);

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        ServiceLocatorUtilities.bind(locator, binder);
        locator.inject(this);
    }

    @Test
    public void shouldAddAmountToBalance() {
        accountDAO.add(account, AMOUNT);
        verify(entityManager).merge(account);
        assertEquals(BALANCE + AMOUNT, account.getBalance().longValue());

    }

    @Test
    public void shouldSubtractAmountToBalance() {
        accountDAO.subtract(account, AMOUNT);
        verify(entityManager).merge(account);
        assertEquals(BALANCE - AMOUNT, account.getBalance().longValue());

    }
}
