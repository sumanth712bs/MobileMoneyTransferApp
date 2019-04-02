package io.badadal.sumanth.moneytransferapp;

import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;
import io.badadal.sumanth.moneytransferapp.service.TransactionService;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private TransactionService transactionService;

    public TransactionResource() {
        transactionService = new TransactionService();
    }

    @GET
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GET
    @Path("/{transactionId}")
    public Transaction getTransactionById(@PathParam("transactionId") long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @POST
    public Transaction addNewTransaction(Transaction transaction) {
        transaction.setTransactionType(TransactionType.TRANSFER);
        return transactionService.addNewTransaction(transaction);
    }

    @POST
    @Path("/deposit")
    public Transaction depositMoney(
            @QueryParam("accountId") long accountId,
            @QueryParam("amount") double depositAmount) {

        return transactionService.deposit(accountId, depositAmount);

    }

    @POST
    @Path("/withdraw")
    public Transaction withdraw(
            @QueryParam("accountId") long accountId,
            @QueryParam("amount") double withdrawAmount) {
        return transactionService.withdraw(accountId, withdrawAmount);
    }
}
