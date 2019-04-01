package io.badadal.sumanth.moneytransferapp.model;

public class Account {

    private long accountId;
    private double balance;

    public Account() {

    }

    public Account(long accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
