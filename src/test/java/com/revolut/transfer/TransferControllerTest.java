package com.revolut.transfer;

import com.revolut.transfer.controllers.TransferController;
import com.revolut.transfer.dtos.TransferRequestDTO;
import com.revolut.transfer.dtos.TransferResponseDTO;
import com.revolut.transfer.exceptions.AccountNotFoundException;
import com.revolut.transfer.services.TransferService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.revolut.transfer.Constants.*;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class TransferControllerTest  extends JerseyTest {

    private final TransferService transferService = mock(TransferService.class);

    private static final Long ACCOUNT_NUMBER_NOT_VALID = 113l;
    @Before
    public void setup() {
        try {
            doNothing().when(transferService).transfer(anyLong(),anyLong(), anyLong());
        } catch (Exception e) {

        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TransferController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bindFactory(new Factory<TransferService>() {

                            @Override
                            public TransferService provide() {
                                return transferService;
                            }

                            @Override
                            public void dispose(TransferService transferService) {

                            }
                        }).to(TransferService.class);
                    }
                });
    }

    @Test
    public void shouldReturnStatusOK() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setAmount(10l);
        transferRequestDTO.setFromAccount(112l);
        transferRequestDTO.setToAccount(111l);

        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        assertTrue(TRANSFER_STATUS_OK.equals(transferResponseDTO.getStatus()));
    }

    @Test
    public void shouldReturnErrorWhenAccountIsNotValid() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setAmount(10l);
        transferRequestDTO.setFromAccount(null);
        transferRequestDTO.setToAccount(111l);

        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        assertTrue(TRANSFER_STATUS_KO.equals(transferResponseDTO.getStatus()));
        assertTrue(ACCOUNT_NOT_VALID.equals(transferResponseDTO.getError()));
    }

    @Test
    public void shouldReturnErrorWhenAmountIsNotValid() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setAmount(0l);
        transferRequestDTO.setFromAccount(112l);
        transferRequestDTO.setToAccount(111l);

        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        assertTrue(TRANSFER_STATUS_KO.equals(transferResponseDTO.getStatus()));
        assertTrue(AMOUNT_NOT_VALID.equals(transferResponseDTO.getError()));
    }

    @Test
    public void shouldReturnErrorWhenSameAccount() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setAmount(10l);
        transferRequestDTO.setFromAccount(111l);
        transferRequestDTO.setToAccount(111l);

        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        assertTrue(TRANSFER_STATUS_KO.equals(transferResponseDTO.getStatus()));
        assertTrue(ACCOUNT_NOT_VALID.equals(transferResponseDTO.getError()));
    }

    @Test
    public void shouldReturnErrorWhenAccountNotExists() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setAmount(10l);
        transferRequestDTO.setFromAccount(ACCOUNT_NUMBER_NOT_VALID);
        transferRequestDTO.setToAccount(114l);

        try {
            doThrow(new AccountNotFoundException(ACCOUNT_NOT_VALID))
                    .when(transferService)
                    .transfer(ACCOUNT_NUMBER_NOT_VALID, 114l, 10l);
        } catch (Exception e) {

        }

        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        assertTrue(TRANSFER_STATUS_KO.equals(transferResponseDTO.getStatus()));
        assertTrue(ACCOUNT_NOT_VALID.equals(transferResponseDTO.getError()));
    }


}
