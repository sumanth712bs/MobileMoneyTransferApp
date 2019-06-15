package io.badadal.sumanth.moneytransferapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import io.badadal.sumanth.moneytransferapp.dao.AccountDao;
import io.badadal.sumanth.moneytransferapp.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sbadadal
 */

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountDao accountDao;

    @Test
    public void return_list_of_all_Accounts_when_called_to_getAllAccounts() {
        // given
        Account account1 = new Account(101L, 200);
        Account account2 = new Account(102L, 400);

        List<Account> accounts = Stream.of(account1, account2).collect(Collectors.toList());

        // when
        when(accountDao.getAllAccounts()).thenReturn(accounts);

        // then
        List<Account> actualAccounts = accountService.getAllAccounts();

        assertEquals(2, actualAccounts.size());
        assertEquals(101L, actualAccounts.get(0).getAccountId());
        assertEquals(200, actualAccounts.get(0).getBalance(), 0);
        assertEquals(102L, actualAccounts.get(1).getAccountId());
        assertEquals(400, actualAccounts.get(1).getBalance(), 0);
    }

    @Test
    public void return_Account_of_request_accountId_when_called_to_getAccountById() {
        // given
        Account account1 = new Account(101L, 200);
        Account account2 = new Account(102L, 400);

        // when
        when(accountDao.getAccountById(101L)).thenReturn(account1);
        when(accountDao.getAccountById(102L)).thenReturn(account2);

        // then
        Account actualAccount1 = accountService.getAccountById(101L);
        Account actualAccount2 = accountService.getAccountById(102L);

        assertSame(account1, actualAccount1);
        assertSame(account2, actualAccount2);

        assertEquals(101L, actualAccount1.getAccountId());
        assertEquals(200, actualAccount1.getBalance(), 0);

        assertEquals(102L, actualAccount2.getAccountId());
        assertEquals(400, actualAccount2.getBalance(), 0);

    }

    @Test
    public void return_first_newly_created_account_when_called_to_addAccount() {

        // given
        Account account = new Account(11L, 200);

        // when
        when(accountDao.getAllAccounts()).thenReturn(Collections.emptyList());
        when(accountDao.addAccount(Matchers.any())).thenReturn(account);

        // then
        Account actualAccount = accountService.addAccount(account);
        assertEquals(101L, actualAccount.getAccountId());
        assertEquals(200, actualAccount.getBalance(), 0);
    }

    @Test
    public void return_second_newly_created_account_when_called_to_addAccount() {

        // given
        Account account = new Account(500L, 1000);

        // when
        when(accountDao.getAllAccounts()).thenReturn(Stream.of(account).collect(Collectors.toList()));
        when(accountDao.addAccount(Matchers.any())).thenReturn(account);

        // then
        Account actualAccount = accountService.addAccount(account);
        assertEquals(501L, actualAccount.getAccountId());
        assertEquals(1000, actualAccount.getBalance(), 0);
    }

    @Test
    public void return_updated_account_when_called_to_updateAccount() {
        // given
        Account account = new Account(500L, 1000);
        Account updateAccount = new Account(500L, 200);

        // when
        when(accountDao.getAccountById(500L)).thenReturn(account);
        when(accountDao.updateAccount(Matchers.any())).thenReturn(updateAccount);

        // then
        Account actualAccount = accountService.updateAccount(updateAccount);
        assertEquals(500L, actualAccount.getAccountId());
        assertEquals(200, actualAccount.getBalance(), 0);
    }

    @Test
    public void return_null_when_invalid_account_to_be_updated() {
        // given
        Account updateAccount = new Account(500L, 200);

        // when
        when(accountDao.getAccountById(500L)).thenReturn(null);
        // then
        Account actualAccount = accountService.updateAccount(updateAccount);

        assertEquals(null, actualAccount);
    }
}