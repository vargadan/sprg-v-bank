package ch.hslu.sprg.vbank.model.web;

import ch.hslu.sprg.vbank.model.Transaction;
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
public class TransactionForm {

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

    public String getFromAccountNo() {
        return fromAccountNo;
    }

    public void setFromAccountNo(String fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    public String getToAccountNo() {
        return toAccountNo;
    }

    public void setToAccountNo(String toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Transaction toTransaction() {
        return new Transaction(fromAccountNo, toAccountNo, amount, Currency.valueOf(currency), comment, false);
    }
}
