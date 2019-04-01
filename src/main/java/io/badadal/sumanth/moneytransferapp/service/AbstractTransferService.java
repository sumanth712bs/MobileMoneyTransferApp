package io.badadal.sumanth.moneytransferapp.service;

import io.badadal.sumanth.moneytransferapp.model.Transaction;

public interface AbstractTransferService {

    Transaction transferMoney(Transaction transaction);
}
