package com.dani.vbank.service;

import com.dani.vbank.model.AccountDetails;
import com.dani.vbank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountDetails> getAccountDetailsForUser(String userId) throws Exception;

    AccountDetails getAccountDetails(String accountId) throws Exception;

    boolean transfer(String fromAcountId, String toAccountId, BigDecimal amount, String currency, String note) throws Exception;

    List<Transaction> getTransactionHistory(String accountNo) throws Exception;
}
