package ch.hslu.sprg.vbank.controller;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.primitive.AccountNumber;
import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountServiceResource {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private AccountService delegate;

    @RequestMapping(path = "/api/v1/account/{accountId}", produces = "application/json")
    public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable("accountId") String accountId) throws Exception {
        if (request.isUserInRole("ACCOUNT_HOLDER")) {
            AccountDetails accountDetails = delegate.getAccountDetails(new AccountNumber(accountId));
            String user = request.getUserPrincipal().getName();
            if (user.equals(accountDetails.getOwnerId().getValue())) {
                return ResponseEntity.ok(accountDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}