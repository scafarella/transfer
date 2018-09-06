package com.revolut.transfer.daos;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class AbstractDAO implements DAO {
    @Inject
    private EntityManager entityManager;

    public AbstractDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountsPU");
        this.entityManager = factory.createEntityManager();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }
}
