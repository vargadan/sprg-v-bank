package ch.hslu.sprg.vbank.model;

import ch.hslu.sprg.vbank.model.domainprimitives.AccountNumber;
import ch.hslu.sprg.vbank.model.domainprimitives.Amount;
import ch.hslu.sprg.vbank.model.domainprimitives.Currency;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transaction")
@XmlType(name = "transaction", propOrder = {
        "fromAccountNo",
        "toAccountNo",
        "amount",
        "currency",
        "comment"
})
public class Transaction {

    public AccountNumber getFromAccountNo() {
        return fromAccountNo;
    }

    public void setFromAccountNo(AccountNumber fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    @XmlElement(required = true)
    AccountNumber fromAccountNo;

    @XmlElement(required = true)
    AccountNumber toAccountNo;

    @XmlElement(required = true)
    Amount amount;

    @XmlElement(required = true)
    Currency currency;

    @XmlElement(required = true)
    String comment;

    @XmlTransient
    boolean executed;

    public Transaction(String fromAccountNo, String toAccountNo, BigDecimal amount, Currency currency, String comment, boolean executed) {
        this.fromAccountNo = new AccountNumber(fromAccountNo);
        this.toAccountNo = new AccountNumber(toAccountNo);
        this.amount = new Amount(amount);
        this.currency = currency;
        this.comment = comment;
        this.executed = executed;
    }

    public Transaction() {
    }

    public AccountNumber getToAccountNo() {
        return toAccountNo;
    }

    public void setToAccountNo(AccountNumber toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
