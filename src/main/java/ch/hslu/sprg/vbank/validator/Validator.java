package ch.hslu.sprg.vbank.validator;

import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;
import java.math.BigDecimal;

public class Validator {

    private static final String ACCOUNT_NO_PATTERN = "\\d{1}-\\d{6}-\\d{2}";

    public static final String USERNAME_PATTERN = "^\\w{3,30}?$";

    @Autowired
    private AccountService accountService;

    void validateAccountNumber(String accountNo, boolean mustExist) throws Exception {
        if (accountNo == null || accountNo.length() == 0) {
            //it cannot be null
            throw new ValidationException("Account is required");
        } if (accountNo.length() != 11) {
            //it should be 11 long
            throw new ValidationException("Account number should be 11 characters long");
        } else if (!accountNo.matches(ACCOUNT_NO_PATTERN)) {
            //it has to match patter
            throw new ValidationException("Account number is in invalid format");
        }
        else if (mustExist) {
            if (accountService.getAccountDetails(accountNo) == null) {
                //account does not exist
                throw new ValidationException("Account does not exists");
            }
        }
    }

    void validateCurrency(String currency) {
        if (!"CHF".equalsIgnoreCase(currency)) {
            throw new ValidationException("Unsupported currency");
        }
    }

    void validateAmount(BigDecimal amount) {
        if (amount.scale() > 2) {
            throw new ValidationException("Amount can have max 2 digits after the decimal place.");
        } else if (amount.doubleValue() <= 0) {
            throw new ValidationException("Amount must be positive");
        }
    }

    void validateUserName(String username) {
        if (username == null || username.length() == 0) {
            //it cannot be null
            throw new ValidationException("Username is required");
        } else if (username.length() < 3) {
            //it should be 11 long
            throw new ValidationException("Username should be at least 3 characters long");
        } else if (username.length() > 30) {
            //it should be 11 long
            throw new ValidationException("Username should be no longer than 30 characters");
        } else if (!username.matches(USERNAME_PATTERN)) {
            //it has to match patter
            throw new ValidationException("Username is in invalid format");
        }
    }
}
