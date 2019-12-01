package ch.hslu.sprg.vbank.controller;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountServiceResource {

//    @Autowired
//    HttpServletRequest request;

    @Autowired
    private AccountService delegate;

    @RequestMapping(path = "/api/v1/account/{accountId}", produces = "application/json")
    public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable("accountId") String accountId) throws Exception {
        AccountDetails accountDetails = delegate.getAccountDetails(accountId);
        return ResponseEntity.ok(accountDetails);
    }
}