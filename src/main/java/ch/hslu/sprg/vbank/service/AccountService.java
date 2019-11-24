package ch.hslu.sprg.vbank.service;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountDetails> getAccountDetailsForUser(String userId) throws Exception;

    AccountDetails getAccountDetails(String accountId) throws Exception;

    boolean transfer(String fromAcountId, String toAccountId, BigDecimal amount, String currency, String note) throws Exception;

    List<Transaction> getTransactionHistory(String accountNo) throws Exception;
}
