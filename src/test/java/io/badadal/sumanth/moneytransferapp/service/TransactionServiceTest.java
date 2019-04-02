package io.badadal.sumanth.moneytransferapp.service;

import static org.mockito.Mockito.when;

import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sbadadal
 */

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private TransferService transferService;

    @Test
    public void return_all_transaction_when_called_to_getAllTransactions() {
        //given
        Transaction transaction1 = new Transaction(22L, 101L, 102L, 1000);
        transaction1.setTransactionType(TransactionType.TRANSFER);

        Transaction transaction2 = new Transaction(23L, 101L, 102L, 1000);
        transaction2.setTransactionType(TransactionType.TRANSFER);

        //when
        when(transactionDao.getAllTransactions())
                .thenReturn(Stream.of(transaction1, transaction2).collect(Collectors.toList()));

        //then
        List<Transaction> actualTransactions = transactionService.getAllTransactions();
        Assert.assertEquals(2, actualTransactions.size());
        Assert.assertEquals(22L, actualTransactions.get(0).getTransactionId(), 0);
        Assert.assertEquals(101L, actualTransactions.get(0).getFromAccountId(), 0);
        Assert.assertEquals(102L, actualTransactions.get(0).getToAccountId(), 0);
        Assert.assertEquals(1000, actualTransactions.get(0).getTransferAmount(), 0);

        Assert.assertEquals(23L, actualTransactions.get(1).getTransactionId(), 0);
        Assert.assertEquals(101L, actualTransactions.get(1).getFromAccountId(), 0);
        Assert.assertEquals(102L, actualTransactions.get(1).getToAccountId(), 0);
        Assert.assertEquals(1000, actualTransactions.get(1).getTransferAmount(), 0);
    }

    @Test
    public void return_requested_transaction_when_called_to_getTransactionById() {
        //given
        Transaction transaction1 = new Transaction(22L, 101L, 102L, 1000);
        transaction1.setTransactionType(TransactionType.TRANSFER);

        //when
        Mockito.when(transactionDao.getTransactionById(22L)).thenReturn(transaction1);

        //then
        Transaction actualTransaction = transactionService.getTransactionById(22L);
        Assert.assertEquals(22L, actualTransaction.getTransactionId(), 0);
        Assert.assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        Assert.assertEquals(102L, actualTransaction.getToAccountId(), 0);
        Assert.assertEquals(1000, actualTransaction.getTransferAmount(), 0);
    }

    @Test
    public void return_null_when_called_to_getTransactionById_with_invalid_id() {
        //given

        //when
        Mockito.when(transactionDao.getTransactionById(23L)).thenReturn(null);

        //then
        Transaction actualTransaction = transactionService.getTransactionById(23L);

        Assert.assertNull(actualTransaction);
    }

  /*  @Test
    public void return_first_new_transaction_when_called_to_addNewTransaction() {

        //given
        Transaction transaction1 = new Transaction(22L, 101L, 102L, 1000);
        transaction1.setTransactionType(TransactionType.TRANSFER);

        Transaction updatedTransaction = new Transaction(1001L, 101L, 102L, 1000);
        updatedTransaction.setTransactionType(TransactionType.TRANSFER);

        Map<TransactionType, AbstractTransferService> typeOfService = new HashMap<>();
        typeOfService.put(TransactionType.TRANSFER, transferService);

        //when
   //     Mockito.when(typeOfService.get(transaction1.getTransactionType())).thenReturn(transferService);
        Mockito.when(transferService.transferMoney(transaction1)).thenReturn(updatedTransaction);

        //then
        Transaction actualTransaction = transactionService.addNewTransaction(transaction1);

        Assert.assertEquals(1001L, actualTransaction.getTransactionId(), 0);
        Assert.assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        Assert.assertEquals(102L, actualTransaction.getToAccountId(), 0);
        Assert.assertEquals(1000, actualTransaction.getTransferAmount(), 0);
        Assert.assertEquals(TransactionType.TRANSFER, actualTransaction.getTransactionType());
    }*/

    @Test
    public void return_deposit_transaction_when_called_to_deposit() {

        //given
        Long accountId = 101L;
        double depositAmount = 1000;
        Transaction depositTransaction = new Transaction(1001L, 101L, null, 1000);
        depositTransaction.setTransactionType(TransactionType.DEPOSIT);

        //when
        Mockito.when(transferService.deposit(accountId, depositAmount)).thenReturn(depositTransaction);

        Transaction actualTransaction = transactionService.deposit(accountId, depositAmount);
        Assert.assertEquals(TransactionType.DEPOSIT, actualTransaction.getTransactionType());
    }
    @Test
    public void return_withdraw_transaction_when_called_to_withdarw() {

        //given
        Long accountId = 101L;
        double withdrawAmount = 1000;
        Transaction withdrawTransaction = new Transaction(1001L, 101L, null, 1000);
        withdrawTransaction.setTransactionType(TransactionType.WITHDRAW);

        //when
        Mockito.when(transferService.withdraw(accountId, withdrawAmount)).thenReturn(withdrawTransaction);

        Transaction actualTransaction = transactionService.withdraw(accountId, withdrawAmount);
        Assert.assertEquals(TransactionType.WITHDRAW, actualTransaction.getTransactionType());
    }
}
