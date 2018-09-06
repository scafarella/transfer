package com.revolut.transfer.daos;

import javax.persistence.EntityTransaction;

public interface DAO {
    EntityTransaction getTransaction();
}
