package io.badadal.sumanth.moneytransferapp.service;

import io.badadal.sumanth.moneytransferapp.dao.AccountDao;
import io.badadal.sumanth.moneytransferapp.dao.TransactionDao;
import io.badadal.sumanth.moneytransferapp.model.Account;
import io.badadal.sumanth.moneytransferapp.model.Transaction;
import io.badadal.sumanth.moneytransferapp.model.TransactionType;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;

public class TransferService implements AbstractTransferService {

    private TransactionDao transactionDao;
    private AccountDao accountDao;

    private Predicate<Long> isAccountPresent = accountId -> accountDao.getAllAccounts().stream()
            .anyMatch(account -> accountId.equals(account.getAccountId()));

    private BiPredicate<Long, Long> isBothAccountPresent =
            (from, to) -> null != from && null != to && isAccountPresent.test(from)
                    && isAccountPresent.test(to);

    private LongFunction<Account> getAccountById = accountId -> accountDao.getAccountById(accountId);
    private BiPredicate<Account, Double> checkIfSufficientAmountIsPresent = (account, amount) -> amount > 0
            && amount <= account.getBalance();


    private Predicate<Long> isValidAccount = accountId -> accountDao.getAllAccounts().stream()
            .anyMatch(account -> accountId.equals(account.getAccountId()));

    private Predicate<Double> isValidAmountToDeposit = amount -> amount > 0;

    private BiPredicate<Double, Double> isValidAmountToWithdraw = (balance, amount) -> amount > 0 && amount <= balance;

    private Function<Long, Double> getBalanceFromAccountId =
            accountId -> accountDao.getAccountById(accountId).getBalance();

    private ToDoubleBiFunction<Account, Double> depositFunction =
            (account, depositAmount) -> account.getBalance() + depositAmount;

    private ToDoubleBiFunction<Account, Double> withdrawFunction =
            (account, withdrawAmount) -> account.getBalance() - withdrawAmount;


    public TransferService() {
        transactionDao = new TransactionDao();
        accountDao = new AccountDao();
    }

    @Override
    public Transaction transferMoney(Transaction transaction) {
        Transaction newTransaction = null;

        boolean accountsPresent = isBothAccountPresent.test(transaction.getFromAccountId(),
                transaction.getToAccountId());
        if (accountsPresent) {
            boolean isSufficientAmountPresent = checkIfSufficientAmountIsPresent
                    .test(getAccountById.apply(transaction.getFromAccountId()), transaction.getTransferAmount());
            if (isSufficientAmountPresent) {
                int transactionCount = transactionDao.getAllTransactions().size();
                if (transactionCount > 0) {
                    transaction.setTransactionId(
                            transactionDao.getAllTransactions().get(transactionCount - 1).getTransactionId() + 1);
                    newTransaction = transactionDao.addTransaction(transaction);

                    updateAccountAfterTransaction(transaction);

                } else {
                    transaction.setTransactionId(1001L);
                    newTransaction = transactionDao.addTransaction(transaction);
                    updateAccountAfterTransaction(transaction);
                }
            }
        }

        return newTransaction;
    }


    public Transaction deposit(Long accountId, double depositAmount) {

        Transaction transaction = null;
        Account account = null;
        boolean isAccountValid = isValidAccount.test(accountId);
        if (isAccountValid) {
            boolean isAmountValid = isValidAmountToDeposit.test(depositAmount);

            if (isAmountValid) {
                account = accountDao.getAccountById(accountId);
                transaction = updateAccountAndProvideTransaction(account, depositAmount, depositFunction,
                        TransactionType.DEPOSIT);
            }
        }

        return transaction;
    }

    public Transaction withdraw(Long accountId, double withdrawAmount) {

        Account account = null;
        Transaction transaction = null;
        boolean isAccountValid = isValidAccount.test(accountId);
        if (isAccountValid) {
            double balance = getBalanceFromAccountId.apply(accountId);
            if (isValidAmountToWithdraw.test(balance, withdrawAmount)) {
                account = accountDao.getAccountById(accountId);
                transaction = updateAccountAndProvideTransaction(account, withdrawAmount, withdrawFunction,
                        TransactionType.WITHDRAW);
            }
        }
        return transaction;
    }

    private Transaction updateAccountAndProvideTransaction(
            Account account,
            double amount,
            ToDoubleBiFunction<Account, Double> biFunction,
            TransactionType transactionType) {
        Transaction transaction = new Transaction();
        account.setBalance(biFunction.applyAsDouble(account, amount));
        accountDao.updateAccount(account);
        transaction.setFromAccountId(account.getAccountId());
        transaction.setTransferAmount(amount);
        transaction.setTransactionType(transactionType);

        return transaction;
    }

    private void updateAccountAfterTransaction(Transaction transaction) {
        withdraw(transaction.getFromAccountId(), transaction.getTransferAmount());
        deposit(transaction.getToAccountId(), transaction.getTransferAmount());
    }
}
