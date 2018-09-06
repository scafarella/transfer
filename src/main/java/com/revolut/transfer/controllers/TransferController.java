package com.revolut.transfer.controllers;

import com.revolut.transfer.dtos.TransferRequestDTO;
import com.revolut.transfer.dtos.TransferResponseDTO;
import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.exceptions.BalanceNotEnoughException;
import com.revolut.transfer.exceptions.TransferRequestDTONotValidException;
import com.revolut.transfer.helpers.TransferRequestDTOValidator;
import com.revolut.transfer.services.TransferService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.revolut.transfer.Constants.GENERIC_ERROR;
import static com.revolut.transfer.Constants.TRANSFER_STATUS_KO;
import static com.revolut.transfer.Constants.TRANSFER_STATUS_OK;

@Path("/transfer")
public class TransferController {

    @Inject
    private TransferService transferService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) {
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setStatus(TRANSFER_STATUS_OK);

        try {
            TransferRequestDTOValidator.validate(transferRequestDTO);
            transferService.transfer(
                    transferRequestDTO.getFromAccount(),
                    transferRequestDTO.getToAccount(),
                    transferRequestDTO.getAmount());
        } catch (TransferRequestDTONotValidException | AccountNotFoundException | BalanceNotEnoughException e) {
            transferResponseDTO.setStatus(TRANSFER_STATUS_KO);
            transferResponseDTO.setError(e.getMessage());
        } catch(Exception e) {
            transferResponseDTO.setStatus(TRANSFER_STATUS_KO);
            transferResponseDTO.setError(GENERIC_ERROR);
        }
        return transferResponseDTO;
    }

    public TransferService getTransferService() {
        return transferService;
    }

}
