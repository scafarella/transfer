package com.revolut.transfer.daos;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AbstractDAO {
    @Inject
    private EntityManager entityManager;

    public AbstractDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountsPU");
        this.entityManager = factory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
