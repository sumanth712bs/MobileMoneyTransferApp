package io.badadal.sumanth.moneytransferapp.dao;

import io.badadal.sumanth.moneytransferapp.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountDao {

    private Map<Long, Account> accounts = AccountDatabase.getAccounts();

    public AccountDao() {
        super();
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public Account addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }

    public Account getAccountById(long accountId) {
        return accounts.get(accountId);
    }

    public Account updateAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        return accounts.get(account.getAccountId());
    }
}
