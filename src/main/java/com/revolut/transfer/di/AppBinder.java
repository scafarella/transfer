package com.revolut.transfer.di;

import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.daos.AccountDAOImpl;
import com.revolut.transfer.services.TransferService;
import com.revolut.transfer.services.TransferServiceImpl;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(TransferServiceImpl.class)
        .to(TransferService.class)
        .in(Singleton.class);

        bind(AccountDAOImpl.class)
        .to(AccountDAO.class)
        .in(Singleton.class);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountsPU");
        final EntityManager entityManager = factory.createEntityManager();

        bindFactory(new Factory<EntityManager>() {

            @Override
            public EntityManager provide() {
                return entityManager;
            }

            @Override
            public void dispose(EntityManager entityManager) {
                entityManager.close();
            }
        })
        .to(EntityManager.class)
        .in(Singleton.class);
    }
}
