package io.badadal.sumanth.moneytransferapp.service;

import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Transaction;

public class DepositService implements AbstractTransferService {

    private TransactionDao transactionDao;

    public DepositService() {
        transactionDao = new TransactionDao();
    }

    @Override
    public Transaction transferMoney(Transaction transaction) {
        Transaction newTransaction = null;
        int transactionCount = transactionDao.getAllTransactions().size();
        if (transactionCount > 0) {
            transaction.setTransactionId(
                    transactionDao.getAllTransactions().get(transactionCount - 1).getTransactionId() + 1);
            newTransaction = transactionDao.addTransaction(transaction);
        } else {
            transaction.setTransactionId(1001L);
            newTransaction = transactionDao.addTransaction(transaction);
        }
        return newTransaction;
    }
}
