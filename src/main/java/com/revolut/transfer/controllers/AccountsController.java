package com.revolut.transfer.controllers;

import com.revolut.transfer.models.Account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
public class AccountsController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> all() {
        return null;
    }
}
