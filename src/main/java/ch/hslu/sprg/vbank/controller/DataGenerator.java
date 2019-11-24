package ch.hslu.sprg.vbank.controller;

import ch.hslu.sprg.vbank.service.AccountService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class DataGenerator {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DataGenerator.class);

    public static String BOB_ACCOUNT_1 = "1-987654-1";
    public static String EVE_ACCOUNT_1 = "3-783702-2";
    public static String ALICE_ACCOUNT_1 = "2-54239-2";

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/addTransactions")
    public void addRandomTransactions(int records) {
        Random r = new Random();
        List<String> sqls = new ArrayList<>();
        r.ints().filter(i -> i > 0 && i < 10000).limit(records).forEach(
                amount -> {
                    boolean fromBob = r.nextBoolean();
                    String fromAccount = fromBob ? BOB_ACCOUNT_1 : EVE_ACCOUNT_1;
                    String toAccount = fromBob ?  EVE_ACCOUNT_1 : BOB_ACCOUNT_1;
                    String note = amount + " CHF from " + (fromBob ? "Bob to Eve" : "Eve to Bob");
                    log.info(note);
                    try {
						accountService.transfer(fromAccount, toAccount, new BigDecimal(amount), "CHF", note);
					} catch (Exception e) {
						log.error("Could not create transfer", e);
					}
                }
        );
    }

}
