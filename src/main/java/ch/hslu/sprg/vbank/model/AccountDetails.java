package ch.hslu.sprg.vbank.model;

import ch.hslu.sprg.vbank.model.domainprimitives.AccountNumber;
import ch.hslu.sprg.vbank.model.domainprimitives.Amount;
import ch.hslu.sprg.vbank.model.domainprimitives.Currency;
import ch.hslu.sprg.vbank.model.domainprimitives.UserName;

import java.math.BigDecimal;

public class AccountDetails {

    private AccountNumber accountNo;

    private UserName ownerId;

    private Amount balance;

    private Currency currency;

    public AccountDetails(String accountNo, String ownerId, BigDecimal balance, Currency currency) {
        this.accountNo = new AccountNumber(accountNo);
        this.ownerId = new UserName(ownerId);
        this.balance = new Amount(balance);
        this.currency = currency;
    }

    public AccountDetails() {
    }

    public AccountNumber getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(AccountNumber accountNo) {
        this.accountNo = accountNo;
    }

    public UserName getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UserName ownerId) {
        this.ownerId = ownerId;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
