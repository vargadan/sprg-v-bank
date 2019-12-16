package ch.hslu.sprg.vbank.service.rmi;

import ch.hslu.sprg.vbank.model.AccountDetails;
import ch.hslu.sprg.vbank.model.domainprimitives.UserName;
import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.util.List;

@SpringBootApplication
@SpringBootConfiguration()
public class AccountServiceRmiClient {

    static {
        System.setProperty("server.port", "9999");
    }

    @Bean
    public RmiProxyFactoryBean accountService() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://127.0.0.1:" + VBankRmiExporterFactory.RMI_PORT + "/X");
        rmiProxyFactory.setServiceInterface(AccountService.class);
        return rmiProxyFactory;
    }

    public static void main(String... args) throws Exception {
        AccountService accountService = SpringApplication.run(AccountServiceRmiClient.class, args).getBean(AccountService.class);
        List<AccountDetails> accountDetails = accountService.getAccountDetailsForUser(new UserName("Victim_01"));
        System.out.println("AccountDetails : ");
        System.out.println(accountDetails);
    }
}
