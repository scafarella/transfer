package com.revolut.transfer;

import com.revolut.transfer.controllers.TransferController;
import com.revolut.transfer.dtos.TransferRequestDTO;
import com.revolut.transfer.dtos.TransferResponseDTO;
import com.revolut.transfer.services.TransferService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferControllerTest  extends JerseyTest {

    private final TransferService transferService = mock(TransferService.class);

    @Before
    public void setup() {
        //when(transferService).thenReturn(new Object());

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
    public void ordersPathParamTest() {
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        Response response = target("transfer").request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(transferRequestDTO, MediaType.APPLICATION_JSON));
        TransferResponseDTO transferResponseDTO = response.readEntity(TransferResponseDTO.class);
        Assert.assertTrue("OK".equals(transferResponseDTO.getStatus()));
    }



}
