package io.badadal.sumanth.moneytransferapp;

import io.badadal.sumanth.moneytransferapp.model.Account;
import io.badadal.sumanth.moneytransferapp.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author sbadadal
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AccountResource.class);
    }

    @Test
    public void return_all_Accounts_when_called_to_getAllAccounts() {
        //then
        Response response = target("/account").request().get();

        Assert.assertEquals("Should return 200 status", 200, response.getStatus());
        Assert.assertNotNull("Should return List of all accounts", response.getEntity());
    }

    @Test
    public void shouldReturn2XXStatusWhenNewAccountCreated() {
        Account account = new Account(101L, 500);
        Response response = target("/account").request().post(Entity.entity(account, MediaType.APPLICATION_JSON));

        Assert.assertEquals("Should return 200 status", 200, response.getStatus());
        Assert.assertNotNull("Should return new Account", response.getEntity());
    }


    @Test
    public void shouldNotReturnAccountWith204Status() {
        //then
        Response response = target("/account/101").request().get();

        Assert.assertEquals("Should return 204 status", 204, response.getStatus());
    }
}
