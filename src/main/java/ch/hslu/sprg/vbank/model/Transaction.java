package ch.hslu.sprg.vbank.model;

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

    @XmlElement(required = true)
    String fromAccountNo;

    @XmlElement(required = true)
    String toAccountNo;

    @XmlElement(required = true)
    BigDecimal amount;

    @XmlElement(required = true)
    String currency;

    @XmlElement(required = true)
    String comment;

    @XmlTransient
    boolean executed;

    public Transaction(String fromAccountNo, String toAccountNo, BigDecimal amount, String currency, String comment, boolean executed) {
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
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

    public String getComment() {
        return this.comment;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
