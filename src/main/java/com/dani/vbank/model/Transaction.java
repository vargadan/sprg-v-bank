package com.dani.vbank.model;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transaction")
@XmlType(name = "transaction", propOrder = {
        "fromAccountNo",
        "toAccountNo",
        "amount",
        "currency",
        "note"
})
public class Transaction {

    @XmlElement(required = true)
    String fromAccountNo;

    @XmlElement(required = true)
    String toAccountNo;

    @XmlElement(required = true)
    BigDecimal amount;

    @XmlElement(required = true)
    String currency;

    @XmlElement(required = true)
    String note;

    @XmlTransient
    boolean executed;

    public Transaction(String fromAccountNo, String toAccountNo, BigDecimal amount, String currency, String note, boolean executed) {
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
        this.amount = amount;
        this.currency = currency;
        this.note = note;
        this.executed = executed;
    }

    public Transaction() {
    }

    public String getFromAccountNo() {
        return this.fromAccountNo;
    }

    public String getToAccountNo() {
        return this.toAccountNo;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getNote() {
        return this.note;
    }

    public boolean isExecuted() {
        return this.executed;
    }

    public void setFromAccountNo(String fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    public void setToAccountNo(String toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
