package io.badadal.sumanth.moneytransferapp.dao;

import io.badadal.sumanth.moneytransferapp.model.Account;
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
public class AccountDaoTest {

    @InjectMocks
    private AccountDao accountDao;

    @Test
    public void shouldGetAllAccounts_whenCalled() {
        //given
        Map<Long, Account> accounts = AccountDatabase.getAccounts();
        accounts.put(101L, new Account(101L, 500));
        accounts.put(102L, new Account(102L, 1000));

        //then
        List<Account> accountList = accountDao.getAllAccounts();

        Assert.assertEquals(2, accountList.size());
        Assert.assertEquals(500, accountList.get(0).getBalance(), 0);
        Assert.assertEquals(1000, accountList.get(1).getBalance(), 0);
        Assert.assertEquals(101L, accountList.get(0).getAccountId());
        Assert.assertEquals(102L, accountList.get(1).getAccountId());
    }


    @Test
    public void return_requested_account_when_called_to_getAccountById() {

        //given
        Map<Long, Account> accounts = AccountDatabase.getAccounts();
        accounts.put(101L, new Account(101L, 500));
        accounts.put(102L, new Account(102L, 1000));

        //then
        Account account = accountDao.getAccountById(101L);

        Assert.assertEquals(101L, account.getAccountId());
        Assert.assertEquals(500, account.getBalance(), 0);

    }

    @Test
    public void return_null_when_called_to_getAccountById_with_wrong_Id() {

        //given
        Map<Long, Account> accounts = AccountDatabase.getAccounts();
        accounts.put(101L, new Account(101L, 500));
        accounts.put(102L, new Account(102L, 1000));

        //then
        Account account = accountDao.getAccountById(0L);

        Assert.assertEquals(null, account);
    }

    @Test
    public void return_newly_createdAccount_when_called_to_addAccount() {

        //given
        Account account = new Account(101L, 700);

        //then
        Account newAccount = accountDao.addAccount(account);

        Assert.assertEquals(101L, newAccount.getAccountId());
        Assert.assertEquals(700, newAccount.getBalance(), 0);
    }

    @Test
    public void return_updated_account_when_called_to_updateAccount() {
        //given
        Account account = new Account(101L, 700);

        //then
        Account updatedAccount = accountDao.updateAccount(account);

        Assert.assertEquals(101L, updatedAccount.getAccountId());
        Assert.assertEquals(700, updatedAccount.getBalance(), 0);

    }


}
