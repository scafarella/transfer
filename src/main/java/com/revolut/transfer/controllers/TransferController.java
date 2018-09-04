package com.revolut.transfer.controllers;

import com.revolut.transfer.services.TransferService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
public class TransferController {

    @Inject
    private TransferService transferService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("hello");
        System.out.println("transferService" + getTransferService());
        return "hello";
    }

    @POST
    @Path("/transfer")
    @Produces(MediaType.APPLICATION_JSON)
    public String transfer() {
    return null;
    }

    public TransferService getTransferService() {
        return transferService;
    }

}
