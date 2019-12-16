package ch.hslu.sprg.vbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VBankApplication {

    static {
        System.setProperty("sun.rmi.registry.registryFilter", "*");
        System.setProperty("javax.xml.accessExternalSchema", "file,http");
        System.setProperty("javax.xml.accessExternalDTD", "file,http");
    }

    public static void main(String[] args) {
        SpringApplication.run(VBankApplication.class, args);
    }

}