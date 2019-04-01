package io.badadal.sumanth.moneytransferapp.dao;

import io.badadal.sumanth.moneytransferapp.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDatabase {

    private static Map<Long, Account> accounts = new HashMap<>();

    public static Map<Long, Account> getAccounts() {
        return accounts;
    }

}
