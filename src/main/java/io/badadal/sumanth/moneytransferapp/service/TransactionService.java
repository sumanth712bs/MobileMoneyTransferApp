package io.badadal.sumanth.moneytransferapp.service;

import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

    private TransactionDao transactionDao;
    private TransferService transferService;

    private Map<TransactionType, AbstractTransferService> typeOfService = new HashMap<>();

    public TransactionService() {
        transactionDao = new TransactionDao();
        transferService = new TransferService();
        typeOfService.put(TransactionType.DEPOSIT, new DepositService());
        typeOfService.put(TransactionType.WITHDRAW, new WithdrawService());
        typeOfService.put(TransactionType.TRANSFER, transferService);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public Transaction getTransactionById(long transactionId) {
        return transactionDao.getTransactionById(transactionId);
    }

    public Transaction addNewTransaction(Transaction transaction) {
        Transaction newTransaction;
        newTransaction = typeOfService.get(transaction.getTransactionType()).transferMoney(transaction);
        return newTransaction;
    }

    public Transaction deposit(Long accountId, double depositAmount) {
        Transaction newTransaction = null;
        newTransaction = transferService.deposit(accountId, depositAmount);
        if (null != newTransaction) {
            addNewTransaction(newTransaction);
        }
        return newTransaction;
    }

    public Transaction withdraw(Long accountId, double amount) {
        Transaction newTransaction = null;
        newTransaction = transferService.withdraw(accountId, amount);
        if (null != newTransaction) {
            addNewTransaction(newTransaction);
        }
        return newTransaction;
    }
}
