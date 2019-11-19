package com.dani.vbank.controller;

import com.dani.vbank.service.impl.JDBCAccountService;
import com.dani.vbank.model.AccountDetails;
import com.dani.vbank.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountServiceResource {

    @Autowired
    private JDBCAccountService delegate;

    @Autowired
    private DataGenerator generator;

    @RequestMapping(path = "/api/v1/account/{accountId}", produces = "application/json")
    public AccountDetails getAccountDetails(@PathVariable("accountId") String accountId) throws Exception {
        AccountDetails details = delegate.getAccountDetails(accountId);
        return details;
    }

    @RequestMapping(path = "/api/v1/account/{accountId}/transactions")
    @ResponseBody
    public List<Transaction> getTransactionHistory(@PathVariable String accountId) throws Exception {
        return delegate.getTransactionHistory(accountId);
    }

    @RequestMapping(path = "/api/v1/transfer", method = {RequestMethod.GET, RequestMethod.POST})
    public void transfer(@RequestParam String fromAcountId, @RequestParam String toAccountId,
                         @RequestParam BigDecimal amount, @RequestParam(defaultValue = "CHF") String currency,
                         @RequestParam String note) throws Exception {
        delegate.transfer(fromAcountId, toAccountId, amount, currency, note);
    }
}