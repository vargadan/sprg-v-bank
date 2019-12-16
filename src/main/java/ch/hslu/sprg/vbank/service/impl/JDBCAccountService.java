package ch.hslu.sprg.vbank.service.impl;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.Transaction;
import ch.hslu.sprg.vbank.model.domainprimitives.*;
import ch.hslu.sprg.vbank.service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
    public List<AccountDetails> getAccountDetailsForUser(UserName userName) {
        try {
            List<AccountDetails> accountDetailsList = new ArrayList<>();
            try (Connection connection = dataSource.getConnection()) {
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM ACCOUNT ACC " +
                        "WHERE ACC.USERNAME  = '" + userName + "'");
                while (resultSet.next()) {
                    AccountDetails accountDetails = new AccountDetails(resultSet.getString("ACCOUNT_ID"),
                            resultSet.getString("USERNAME"),
                            resultSet.getBigDecimal("BALANCE"),
                            Currency.valueOf(resultSet.getString("CURRENCY")));
                    accountDetailsList.add(accountDetails);
                    log.info("Account found : " + accountDetails);
                }
            }
            return accountDetailsList;
        } catch (SQLException se) {
            log.error("Error when looking up transaction details", se);
            throw new RuntimeException(se);
        }
    }

    @Override
    public AccountDetails getAccountDetails(AccountNumber accountNo) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT ACCOUNT_ID, USERNAME, BALANCE, CURRENCY " +
                    "FROM ACCOUNT ACC WHERE ACC.ACCOUNT_ID = '" + accountNo + "'");
            if (resultSet.next()) {
                AccountDetails accountDetails = new AccountDetails(resultSet.getString("ACCOUNT_ID"),
                        resultSet.getString("USERNAME"),
                        resultSet.getBigDecimal("BALANCE"),
                        Currency.valueOf(resultSet.getString("CURRENCY")));
                log.info("Account found : " + accountDetails);
                return accountDetails;
            } else {
                log.info("No account found with account No " + accountNo);
                return null;
            }
        } catch (SQLException se) {
            log.error("Error when looking up account details", se);
            throw new RuntimeException(se);
        }
    }

    @Override
    public boolean transfer(Transaction transaction) {
        try {
            return this.transfer(transaction.getFromAccountNo(), transaction.getToAccountNo(), transaction.getAmount(), transaction.getCurrency(), transaction.getComment());
        } catch (SQLException se) {
            log.error("Error when creating transaction", se);
            throw new RuntimeException(se);
        }
    }

    private boolean transfer(AccountNumber fromAccountId, AccountNumber toAccountId, Amount amount, Currency currency, String comment) throws SQLException {

            AccountDetails toAccount = getAccountDetails(toAccountId);
            AccountDetails fromAccount = getAccountDetails(fromAccountId);
            //if both account exists we execute the transaction
            if (toAccount != null && fromAccount != null) {
                //update balance of fromAccount
                {
                    Amount newBalance = new Amount(fromAccount.getBalance().getValue().subtract(amount.getValue()));
                    updateAccountBalance(fromAccountId, newBalance, currency);
                }
                //update balance of toAccount
                {
                    Amount newBalance = new Amount(toAccount.getBalance().getValue().add(amount.getValue()));
                    updateAccountBalance(toAccountId, newBalance, currency);
                }
                // create transaction record
                {
                    insertTransaction(fromAccountId, toAccountId, amount, currency, comment, false);
                }
                return true;
            } else {
                // create transaction record
                insertTransaction(fromAccountId, toAccountId, amount, currency, comment, true);
                return false;
            }
    }

    private boolean insertTransaction(AccountNumber fromAccountId, AccountNumber toAccountId, Amount amount, Currency currency, String comment, boolean pending) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder insert = new StringBuilder("INSERT INTO TRANSACTION (FROM_ACCOUNT,TO_ACCOUNT,AMOUNT,CURRENCY,COMMENT,EXECUTED) VALUES('")
                    .append(fromAccountId).append("','").append(toAccountId).append("',")
                    .append(amount).append(",'").append(currency).append("','")
                    .append(comment).append("',").append(!pending).append(")");
            log.info("SQL creating transaction : " + insert.toString());
            return connection.createStatement().execute(insert.toString());
        }
    }

    private boolean updateAccountBalance(AccountNumber accountId, Amount amount, Currency currency) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            StringBuilder update = new StringBuilder("UPDATE ACCOUNT SET BALANCE = ").append(amount)
                    .append(" WHERE ACCOUNT_ID = '").append(accountId)
                    .append("' AND CURRENCY='").append(currency).append("'");
            log.info("SQL updating balance (-) : " + update.toString());
            return connection.createStatement().execute(update.toString());
        }
    }

    @Override
    public List<Transaction> getTransactionHistory(AccountNumber accountNo) {
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
                        Currency.valueOf(resultSet.getString("CURRENCY")),
                        resultSet.getString("COMMENT"),
                        resultSet.getBoolean("EXECUTED")
                );
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException se) {
            log.error("Error when looking up transaction history", se);
            throw new RuntimeException(se);
        }
    }

}