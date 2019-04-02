package io.badadal.sumanth.moneytransferapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import io.badadal.sumanth.moneytransferapp.dao.AccountDao;
import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Account;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sbadadal
 */

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private AccountDao accountDao;


    @Test
    public void return_second_new_transaction_when_called_to_transfer() {

        //given
        Account account1 = new Account(101L, 2000);
        Account account2 = new Account(102L, 300);
        List<Account> accounts = Stream.of(account1, account2).collect(Collectors.toList());


        Transaction transaction = new Transaction(22L, 101L, 102L, 1000);
        transaction.setTransactionType(TransactionType.TRANSFER);

        Transaction updatedTransaction = new Transaction(23L, 101L, 102L, 1000);
        updatedTransaction.setTransactionType(TransactionType.TRANSFER);

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(101L)).thenReturn(account1);
        when(accountDao.getAccountById(102L)).thenReturn(account2);
        when(transactionDao.getAllTransactions())
                .thenReturn(Stream.of(transaction).collect(Collectors.toList()));
        when(transactionDao.addTransaction(any())).thenReturn(updatedTransaction);


        //then
        Transaction actualTransaction = transferService.transferMoney(transaction);

        assertEquals(23L, actualTransaction.getTransactionId(), 0);
        assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        assertEquals(1000L, actualTransaction.getTransferAmount(), 0);
        assertEquals(102L, actualTransaction.getToAccountId(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransaction.getTransactionType());
    }

    @Test
    public void return_first_new_transaction_when_called_to_transfer() {

        //given
        Account account1 = new Account(101L, 2000);
        Account account2 = new Account(102L, 300);
        List<Account> accounts = Stream.of(account1, account2).collect(Collectors.toList());


        Transaction transaction = new Transaction(22L, 101L, 102L, 1000);
        transaction.setTransactionType(TransactionType.TRANSFER);

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(101L)).thenReturn(account1);
        when(accountDao.getAccountById(102L)).thenReturn(account2);
        when(transactionDao.addTransaction(any())).thenReturn(transaction);


        //then
        Transaction actualTransaction = transferService.transferMoney(transaction);

        assertEquals(1001L, actualTransaction.getTransactionId(), 0);
        assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        assertEquals(1000L, actualTransaction.getTransferAmount(), 0);
        assertEquals(102L, actualTransaction.getToAccountId(), 0);
        assertEquals(TransactionType.TRANSFER, actualTransaction.getTransactionType());
    }


    @Test
    public void test_deposit_method() {

        //given
        Account account1 = new Account(101L, 2000);
        Account account2 = new Account(102L, 300);
        Account updateAccount = new Account(102L, 1000);

        List<Account> accounts = Stream.of(account1, account2).collect(Collectors.toList());

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(101L)).thenReturn(account1);
        when(accountDao.getAccountById(102L)).thenReturn(account2);
        when(accountDao.updateAccount(any())).thenReturn(updateAccount);

        //then

        Transaction actualTransaction = transferService.deposit(102L, 700);
        assertNull(actualTransaction.getTransactionId());
        assertEquals(102L, actualTransaction.getFromAccountId(), 0);
        assertEquals(700, actualTransaction.getTransferAmount(), 0);
        assertNull(actualTransaction.getToAccountId());
        assertEquals(TransactionType.DEPOSIT, actualTransaction.getTransactionType());
    }

    @Test
    public void test_deposit_method_with_negativeValues() {

        //given
        Account account = new Account(102L, 300);
        Account updateAccount = new Account(102L, 300);

        List<Account> accounts = Stream.of(account).collect(Collectors.toList());

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(102L)).thenReturn(account);
        when(accountDao.updateAccount(any())).thenReturn(updateAccount);

        //then
        Transaction actualTransaction = transferService.deposit(102L, -1000);
        assertNull(actualTransaction);

    }

    @Test
    public void test_withdraw_method() {

        //given
        Account account1 = new Account(101L, 2000);
        Account account2 = new Account(102L, 300);
        Account updateAccount = new Account(101L, 1000);

        List<Account> accounts = Stream.of(account1, account2).collect(Collectors.toList());

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(101L)).thenReturn(account1);
        when(accountDao.getAccountById(102L)).thenReturn(account2);
        when(accountDao.updateAccount(any())).thenReturn(updateAccount);

        //then

        Transaction actualTransaction = transferService.withdraw(101L, 1000);
        assertNull(actualTransaction.getTransactionId());
        assertEquals(101L, actualTransaction.getFromAccountId(), 0);
        assertEquals(1000, actualTransaction.getTransferAmount(), 0);
        assertNull(actualTransaction.getToAccountId());
        assertEquals(TransactionType.WITHDRAW, actualTransaction.getTransactionType());
    }


    @Test
    public void test_withdraw_method_with_more_than_balance() {

        //given
        Account account = new Account(102L, 300);
        Account updateAccount = new Account(102L, 300);

        List<Account> accounts = Stream.of(account).collect(Collectors.toList());

        //when
        when(accountDao.getAllAccounts()).thenReturn(accounts);
        when(accountDao.getAccountById(102L)).thenReturn(account);
        when(accountDao.updateAccount(any())).thenReturn(updateAccount);

        //then
        Transaction actualTransaction = transferService.withdraw(102L, 500);
        assertNull(actualTransaction);

    }

}
