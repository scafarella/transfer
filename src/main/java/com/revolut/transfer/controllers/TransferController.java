package com.revolut.transfer.controllers;

import com.revolut.transfer.dtos.TransferRequestDTO;
import com.revolut.transfer.dtos.TransferResponseDTO;
import com.revolut.transfer.services.TransferService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
public class TransferController {

    @Inject
    private TransferService transferService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) {
        System.out.println(transferRequestDTO);
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setStatus("OK");
        return transferResponseDTO;
    }

    public TransferService getTransferService() {
        return transferService;
    }

}
