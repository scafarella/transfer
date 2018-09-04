package com.revolut.transfer.di;

import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.daos.AccountDAOImpl;
import com.revolut.transfer.services.TransferService;
import com.revolut.transfer.services.TransferServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(TransferServiceImpl.class)
        .to(TransferService.class)
        .in(Singleton.class);

        bind(AccountDAOImpl.class)
        .to(AccountDAO.class)
        .in(Singleton.class);
    }
}
