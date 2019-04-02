package io.badadal.sumanth.moneytransferapp;

import io.badadal.sumanth.moneytransferapp.model.Account;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author sbadadal
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionResourceTest extends JerseyTest {


    @Override
    protected Application configure() {
        return new ResourceConfig(TransactionResource.class);
    }


    @Test
    public void shouldReturn2XXWithAllTransactions() {

        Response response = target("/transaction").request().get();

        Assert.assertEquals("Should return 200 status", 200, response.getStatus());
        Assert.assertNotNull("Should return List of all transactions", response.getEntity());
    }
}
