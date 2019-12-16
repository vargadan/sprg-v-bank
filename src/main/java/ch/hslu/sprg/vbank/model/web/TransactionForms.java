package ch.hslu.sprg.vbank.model.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transactions")
public class TransactionForms {

    @XmlElement(required = true, name = "transaction")
    public List<TransactionForm> getTransactionList() {
        return transactions;
    }

    public void setTransactionList(List<TransactionForm> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionForm> transactions;

}
