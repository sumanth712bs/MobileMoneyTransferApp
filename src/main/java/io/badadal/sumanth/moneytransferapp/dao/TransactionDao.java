package io.badadal.sumanth.moneytransferapp.dao;

import io.badadal.sumanth.moneytransferapp.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionDao {

    private Map<Long, Transaction> transactions = TransactionDatabase.getTransactions.get();

    public TransactionDao() {
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    public Transaction getTransactionById(long transactionId) {
        return transactions.get(transactionId);
    }

    public Transaction addTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    public Transaction updateTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
        return transactions.get(transaction.getTransactionId());
    }

}
