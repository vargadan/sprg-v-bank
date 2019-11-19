package com.dani.vbank.service.impl;

import com.dani.vbank.model.AccountDetails;
import com.dani.vbank.service.AccountService;
import com.dani.vbank.model.Transaction;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCAccountService implements AccountService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JDBCAccountService.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<AccountDetails> getAccountDetailsForUser(String userName) throws Exception {
        List<AccountDetails> accountDetailsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM ACCOUNT ACC " +
                    "WHERE ACC.USERNAME  = '" + userName + "'");
            while (resultSet.next()) {
                AccountDetails accountDetails = new AccountDetails(resultSet.getString("ACCOUNT_ID"),
                        resultSet.getString("USERNAME"),
                        resultSet.getBigDecimal("BALANCE"),
                        resultSet.getString("CURRENCY"));
                accountDetailsList.add(accountDetails);
                log.info("Account found : " + accountDetails);
            }
        }
        return accountDetailsList;
    }

    @Override
    public AccountDetails getAccountDetails(String accountNo) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT ACCOUNT_ID, USERNAME, BALANCE, CURRENCY " +
                    "FROM ACCOUNT ACC WHERE ACC.ACCOUNT_ID = '" + accountNo + "'");
            if (resultSet.next()) {
                AccountDetails accountDetails = new AccountDetails(resultSet.getString("ACCOUNT_ID"),
                        resultSet.getString("USERNAME"),
                        resultSet.getBigDecimal("BALANCE"),
                        resultSet.getString("CURRENCY"));
                log.info("Account found : " + accountDetails);
                return accountDetails;
            } else {
                log.info("No account found with account No " + accountNo);
                return null;
            }
        }
    }

    @Override
    public boolean transfer(String fromAccountId, String toAccountId, BigDecimal amount, String currency, String note) throws Exception {
        currency = currency.toUpperCase();

            AccountDetails toAccount = getAccountDetails(toAccountId);
            AccountDetails fromAccount = getAccountDetails(fromAccountId);
            //if both account exists we execute the transaction
            if (toAccount != null && fromAccount != null) {
                //update balance of fromAccount
                {
                    BigDecimal newBalance = fromAccount.getBalance().subtract(amount);
                    updateAccountBalance(fromAccountId, newBalance, currency);
                }
                //update balance of toAccount
                {
                    BigDecimal newBalance = toAccount.getBalance().add(amount);
                    updateAccountBalance(toAccountId, newBalance, currency);
                }
                // create transaction record
                {
                    insertTransaction(fromAccountId, toAccountId, amount, currency, note, false);
                }
                return true;
            } else {
                // create transaction record
                insertTransaction(fromAccountId, toAccountId, amount, currency, note, true);
                return false;
            }
    }

    private boolean insertTransaction(String fromAccountId, String toAccountId, BigDecimal amount, String currency, String note, boolean pending) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder insert = new StringBuilder("INSERT INTO TRANSACTION (FROM_ACCOUNT,TO_ACCOUNT,AMOUNT,CURRENCY,NOTE,EXECUTED) VALUES('")
                    .append(fromAccountId).append("','").append(toAccountId).append("',")
                    .append(amount).append(",'").append(currency).append("','")
                    .append(note).append("',").append(!pending).append(")");
            log.info("SQL creating transaction : " + insert.toString());
            return connection.createStatement().execute(insert.toString());
        }
    }

    private boolean updateAccountBalance(String accountId, BigDecimal amount, String currency) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder update = new StringBuilder("UPDATE ACCOUNT SET BALANCE = ").append(amount)
                    .append(" WHERE ACCOUNT_ID = '").append(accountId)
                    .append("' AND CURRENCY='").append(currency).append("'");
            log.info("SQL updating balance (-) : " + update.toString());
            return connection.createStatement().execute(update.toString());
        }
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNo) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM" +
                    " TRANSACTION WHERE FROM_ACCOUNT = '" + accountNo + "' OR TO_ACCOUNT = '" + accountNo + "'");
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getString("FROM_ACCOUNT"),
                        resultSet.getString("TO_ACCOUNT"),
                        resultSet.getBigDecimal("AMOUNT"),
                        resultSet.getString("CURRENCY"),
                        resultSet.getString("NOTE"),
                        resultSet.getBoolean("EXECUTED")
                );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

}