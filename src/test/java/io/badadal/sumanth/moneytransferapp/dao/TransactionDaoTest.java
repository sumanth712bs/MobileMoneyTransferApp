package io.badadal.sumanth.moneytransferapp.dao;

import static org.junit.Assert.*;

import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

/**
 * @author sbadadal
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionDaoTest {

    @InjectMocks
    private TransactionDao transactionDao;

    @Test
    public void return_All_transactions_when_called_to_getAllTransactions() {
        //given
        Map<Long, Transaction> transactions = TransactionDatabase.getTransactions.get();
        Transaction transaction1 = new Transaction(1001L, 101L, 102L, 100 );
        Transaction transaction2 = new Transaction(1002L, 101L, 102L, 300 );
        transaction1.setTransactionType(TransactionType.TRANSFER);
        transaction2.setTransactionType(TransactionType.TRANSFER);
        transactions.put(1001L, transaction1);
        transactions.put(1002L, transaction2);

        //then
        List<Transaction> actualTransactions = transactionDao.getAllTransactions();

        assertEquals(2, actualTransactions.size());

        assertEquals(1001L, actualTransactions.get(0).getTransactionId(), 0);
        assertEquals(101L, actualTransactions.get(0).getFromAccountId(), 0);
        assertEquals(102L, actualTransactions.get(0).getToAccountId(), 0);
        assertEquals(100L, actualTransactions.get(0).getTransferAmount(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransactions.get(0).getTransactionType());

        assertEquals(1002L, actualTransactions.get(1).getTransactionId(), 0);
        assertEquals(101L, actualTransactions.get(1).getFromAccountId(), 0);
        assertEquals(102L, actualTransactions.get(1).getToAccountId(), 0);
        assertEquals(300L, actualTransactions.get(1).getTransferAmount(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransactions.get(1).getTransactionType());
    }


    @Test
    public void return_requested_transaction_when_called_to_getTransactionById() {

        //given
        Map<Long, Transaction> transactions = TransactionDatabase.getTransactions.get();
        Transaction transaction1 = new Transaction(1001L, 101L, 102L, 100 );
        Transaction transaction2 = new Transaction(1002L, 101L, 102L, 300 );
        transaction1.setTransactionType(TransactionType.TRANSFER);
        transaction2.setTransactionType(TransactionType.TRANSFER);
        transactions.put(1001L, transaction1);
        transactions.put(1002L, transaction2);

        //then
        Transaction actualTransaction1 = transactionDao.getTransactionById(1001L);
        Transaction actualTransaction2 = transactionDao.getTransactionById(1002L);

        assertEquals(1001L, actualTransaction1.getTransactionId(), 0);
        assertEquals(101L, actualTransaction1.getFromAccountId(), 0);
        assertEquals(102L, actualTransaction1.getToAccountId(), 0);
        assertEquals(100L, actualTransaction1.getTransferAmount(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransaction1.getTransactionType());

        assertEquals(1002L, actualTransaction2.getTransactionId(), 0);
        assertEquals(101L, actualTransaction2.getFromAccountId(), 0);
        assertEquals(102L, actualTransaction2.getToAccountId(), 0);
        assertEquals(300L, actualTransaction2.getTransferAmount(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransaction2.getTransactionType());
    }


    @Test
    public void return_null_when_called_to_getTransactionById_with_wrong_id() {
        //given
        Map<Long, Transaction> transactions = TransactionDatabase.getTransactions.get();
        Transaction transaction1 = new Transaction(1001L, 101L, 102L, 100 );
        Transaction transaction2 = new Transaction(1002L, 101L, 102L, 300 );
        transaction1.setTransactionType(TransactionType.TRANSFER);
        transaction2.setTransactionType(TransactionType.TRANSFER);
        transactions.put(1001L, transaction1);
        transactions.put(1002L, transaction2);

        //then
        Transaction actualTransaction = transactionDao.getTransactionById(0L);

        Assert.assertEquals(null, actualTransaction);
    }

    @Test
    public void return_newly_created_transaction_when_called_to_addTransaction(){
        //given
        Transaction transaction1 = new Transaction(1001L, 10L, 20L, 700 );
        transaction1.setTransactionType(TransactionType.TRANSFER);

        //then
        Transaction actualTransaction = transactionDao.addTransaction(transaction1);
        assertEquals(1001L, actualTransaction.getTransactionId(), 0);
        assertEquals(10L, actualTransaction.getFromAccountId(), 0);
        assertEquals(20L, actualTransaction.getToAccountId(), 0);
        assertEquals(700L, actualTransaction.getTransferAmount(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransaction.getTransactionType());
    }

    @Test
    public void return_updated_Transaction_when_called_to_updateTransaction() {
        //given
        Map<Long, Transaction> transactions = TransactionDatabase.getTransactions.get();
        Transaction transaction1 = new Transaction(1001L, 101L, 102L, 100 );
        transaction1.setTransactionType(TransactionType.TRANSFER);
        transactions.put(1001L, transaction1);

        Transaction updateTransaction =  new Transaction(1001L, 101L, 101L, 200 );
        updateTransaction.setTransactionType(TransactionType.DEPOSIT);

        //then
        Transaction updatedTransaction = transactionDao.updateTransaction(updateTransaction);

        assertEquals(1001L, updatedTransaction.getTransactionId(), 0);
        assertEquals(101L, updatedTransaction.getFromAccountId(), 0);
        assertEquals(101L, updatedTransaction.getToAccountId(), 0);
        assertEquals(200L, updatedTransaction.getTransferAmount(), 0);
        assertEquals(TransactionType.DEPOSIT, updatedTransaction.getTransactionType());
    }
}
