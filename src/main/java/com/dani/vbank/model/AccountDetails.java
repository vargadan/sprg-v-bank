package com.dani.vbank.model;

import java.math.BigDecimal;

public class AccountDetails {

    private String accountNo;

    private String ownerId;

    private BigDecimal balance;

    private String currency;

    public AccountDetails(String accountNo, String ownerId, BigDecimal balance, String currency) {
        this.accountNo = accountNo;
        this.ownerId = ownerId;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDetails() {
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
