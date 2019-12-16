package ch.hslu.sprg.vbank.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Transactions {

    @XmlElement(required = true, name = "transaction")
    public List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
