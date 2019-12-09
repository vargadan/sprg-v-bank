package ch.hslu.sprg.vbank.validator;

import ch.hslu.sprg.vbank.model.Transaction;
import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Validator {

    private static final Logger logger = Logger.getAnonymousLogger();


    private static final String ACCOUNT_NO_PATTERN = "\\d{1}-\\d{6}-\\d{2}";

    public static final String USERNAME_PATTERN = "^\\w{3,30}?$";

    @Autowired
    private AccountService accountService;

    public void validateUserName(String username) {
        throw new UnsupportedOperationException();
    }

    public void validateAccountNumber(String accountNo, boolean mustExist) {
        throw new UnsupportedOperationException();
    }

    public void validateCurrency(String currency) {
        throw new UnsupportedOperationException();
    }

    public void validateAmount(BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    public void validateTransaction(Transaction transaction) {
        this.validateAccountNumber(transaction.getFromAccountNo(), true);
        this.validateAccountNumber(transaction.getToAccountNo(), true);
        this.validateAmount(transaction.getAmount());
        this.validateCurrency(transaction.getCurrency());
    }
}
