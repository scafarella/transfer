package com.revolut.transfer.controllers;

import com.revolut.transfer.daos.AccountDAO;
import com.revolut.transfer.models.Account;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
public class AccountsController {

    @Inject
    private AccountDAO accountDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> all() {
        return accountDAO.findAll();
    }
}
