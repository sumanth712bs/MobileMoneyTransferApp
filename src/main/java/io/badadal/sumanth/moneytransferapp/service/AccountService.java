package io.badadal.sumanth.moneytransferapp.service;

import io.badadal.sumanth.moneytransferapp.dao.AccountDao;
import io.badadal.sumanth.moneytransferapp.model.Account;

import java.util.List;

public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    public Account addAccount(Account account) {
        Account newAccount = null;
        int listSize = accountDao.getAllAccounts().size();
        if (listSize > 0) {
            account.setAccountId(accountDao.getAllAccounts().get(listSize - 1).getAccountId() + 1L);
            newAccount = accountDao.addAccount(account);
        } else {
            account.setAccountId(101L);
            newAccount = accountDao.addAccount(account);
        }
        return newAccount;
    }

    public Account getAccountById(long accountId) {
        return accountDao.getAccountById(accountId);

    }

    public Account updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }
}
