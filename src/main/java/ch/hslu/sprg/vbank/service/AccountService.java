package ch.hslu.sprg.vbank.service;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.Transaction;
import ch.hslu.sprg.vbank.model.primitive.AccountNumber;
import ch.hslu.sprg.vbank.model.primitive.UserName;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountDetails> getAccountDetailsForUser(UserName userId);

    AccountDetails getAccountDetails(AccountNumber accountId);

    boolean transfer(Transaction transaction);

    List<Transaction> getTransactionHistory(AccountNumber accountNo);
}
