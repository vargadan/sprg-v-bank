package ch.hslu.sprg.vbank;

import ch.hslu.sprg.vbank.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@SpringBootApplication
public class VBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(VBankApplication.class, args);
    }

}