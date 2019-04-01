package io.badadal.sumanth.moneytransferapp;

import io.badadal.sumanth.moneytransferapp.model.Account;
import io.badadal.sumanth.moneytransferapp.service.AccountService;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountService accountService;

    public AccountResource() {
        accountService = new AccountService();
    }

    @GET
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @POST
    public Account createAccount(Account account) {
        return accountService.addAccount(account);
    }

    @GET
    @Path("/{accountId}")
    public Account getAccountById(@PathParam("accountId") Long accountId) {
        return accountService.getAccountById(accountId);
    }

    @PUT
    public Account update(Account account) {
        return accountService.updateAccount(account);
    }
}
