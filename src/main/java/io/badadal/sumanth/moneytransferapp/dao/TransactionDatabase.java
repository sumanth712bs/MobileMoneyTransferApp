package io.badadal.sumanth.moneytransferapp.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.badadal.sumanth.moneytransferapp.model.Transaction;

public class TransactionDatabase {
	
	private static Map<Long, Transaction> transactions = new HashMap<>();
	
	static Supplier<Map<Long, Transaction>> getTransactions = () -> transactions;

}
