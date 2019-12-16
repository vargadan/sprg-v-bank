package ch.hslu.sprg.vbank.controller;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.Transaction;
import ch.hslu.sprg.vbank.model.Transactions;
import ch.hslu.sprg.vbank.model.domainprimitives.AccountNumber;
import ch.hslu.sprg.vbank.model.domainprimitives.UserName;
import ch.hslu.sprg.vbank.model.web.TransactionForm;
import ch.hslu.sprg.vbank.model.web.TransactionForms;
import ch.hslu.sprg.vbank.service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.ValidationException;
import javax.xml.bind.JAXBContext;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BankController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BankController.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    AccountService accountService;

    @Autowired
    DataSource dataSource;

    @RequestMapping("/")
    public String home(Model model) throws Exception {
        String user = request.getRemoteUser();
        List<AccountNumber> accountIds = new ArrayList<>();
        List<AccountDetails> accountDetailsList = accountService.getAccountDetailsForUser(new UserName(user));
        accountDetailsList.forEach(accountDetails -> accountIds.add(accountDetails.getAccountNo()));
        model.addAttribute("accountIds", accountIds);
        return "home";
    }

    @RequestMapping("/home_legacy")
    public String home_legacy(Model model) throws Exception {
        String user = request.getRemoteUser();
        List<AccountDetails> accountDetailsList = accountService.getAccountDetailsForUser(new UserName(user));
        model.addAttribute("accounts", accountDetailsList);
        return "home_legacy";
    }

    @RequestMapping("/history")
    public String history(Model model) throws Exception {
        String accountNo = request.getParameter("accountNo");
        List<Transaction> transactions = accountService.getTransactionHistory(new AccountNumber(accountNo));
        model.addAttribute("transactions", transactions);
        model.addAttribute("accountNo", accountNo);
        return "history";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public ModelAndView transaction(@ModelAttribute TransactionForm transaction, Model model) {
        return new ModelAndView("transfer", "transaction", new TransactionForm());
    }

    @RequestMapping(value = "/doTransfer", method = RequestMethod.POST)
    public ModelAndView doTransfer(@ModelAttribute TransactionForm transactionForm, ModelMap model) {
        //only execute transfer if the transaction data is valid
        try {
            Transaction transaction = transactionForm.toTransaction();
            //transaction is always in valid state if constructed due to its domain primitive types.
            //therefore, no need to validate.
            if (accountService.transfer(transaction)) {
                model.addAttribute("info", "Transaction was completed.");
            } else {
                model.addAttribute("info", "Transaction is pending.");
            }
            return new ModelAndView("redirect:/", model);
        } catch (ValidationException ve){
            model.addAttribute("error", ve.getMessage());
            return new ModelAndView("transfer", model);
        }
    }

    @PostMapping("/uploadTransactions")
    public ModelAndView uploadTransactions(@ModelAttribute MultipartFile file, ModelMap model) throws Exception {
        if (file == null || file.isEmpty()) {
            model.addAttribute("error", "File is missing.");
            return new ModelAndView("redirect:/", model);
        }
        JAXBContext context = JAXBContext.newInstance(TransactionForms.class, TransactionForm.class);
        TransactionForms transactionForms = (TransactionForms) context.createUnmarshaller()
                .unmarshal(file.getInputStream());
        if (!transactionForms.getTransactionList().isEmpty()) {
            transactionForms.getTransactionList().forEach(transactionForm -> {
                try {
                    accountService.transfer(transactionForm.toTransaction());
                } catch (Exception e) {
                    log.error("Could not execute transaction", e);
                }
            });
            int size = transactionForms.getTransactionList().size();
            model.addAttribute("info", size + " transactions uploaded.");
        }
        AccountNumber accountNumber = this.accountService.getAccountDetailsForUser(new UserName(request.getRemoteUser())).get(0).getAccountNo();
        return new ModelAndView("redirect:/history?accountNo=" + accountNumber.getValue(), model);
    }
}