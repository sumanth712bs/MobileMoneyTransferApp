package io.badadal.sumanth.moneytransferapp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sbadadal
 */

@RunWith(MockitoJUnitRunner.class)
public class WithdrawServiceTest {

    @InjectMocks
    private WithdrawService withdrawService;

    @Mock
    private TransactionDao transactionDao;


    @Test
    public void return_first_transaction_of_Type_deposit_when_called_to_transferMoney() {

        //given
        Transaction transaction = new Transaction(22L, 101L, null, 1000L);
        transaction.setTransactionType(TransactionType.WITHDRAW);

        //when
        when(transactionDao.getAllTransactions()).thenReturn(Collections.emptyList());
        when(transactionDao.addTransaction(Matchers.any())).thenReturn(transaction);

        //then
        Transaction actualTransaction = withdrawService.transferMoney(transaction);
        assertEquals(1001L, actualTransaction.getTransactionId(), 0);
        assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        assertEquals(null, actualTransaction.getToAccountId());
        assertEquals(1000L, actualTransaction.getTransferAmount(), 0);
        assertEquals(TransactionType.WITHDRAW, actualTransaction.getTransactionType());
    }


    @Test
    public void return_second_transaction_of_Type_deposit_when_called_to_transferMoney() {

        //given
        Transaction transaction = new Transaction(22L, 101L, null, 1000L);
        transaction.setTransactionType(TransactionType.WITHDRAW);


        //when
        when(transactionDao.getAllTransactions())
                .thenReturn(Stream.of(transaction).collect(Collectors.toList()));
        when(transactionDao.addTransaction(Matchers.any())).thenReturn(transaction);

        Transaction actualTransaction = withdrawService.transferMoney(transaction);
        assertEquals(23L, actualTransaction.getTransactionId(), 0);
        assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        assertEquals(null, actualTransaction.getToAccountId());
        assertEquals(1000L, actualTransaction.getTransferAmount(), 0);
        assertEquals(TransactionType.WITHDRAW, actualTransaction.getTransactionType());

    }
}
