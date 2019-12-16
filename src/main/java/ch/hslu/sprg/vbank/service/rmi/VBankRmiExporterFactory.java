package ch.hslu.sprg.vbank.service.rmi;

import ch.hslu.sprg.vbank.service.AccountService;
import ch.hslu.sprg.vbank.service.impl.JDBCAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Component;

@Component
public class VBankRmiExporterFactory {

    public static final int RMI_PORT = 1099;

    @Autowired
    JDBCAccountService implementation;

    @Bean
    RmiServiceExporter exporter() {
        Class<AccountService> serviceInterface = AccountService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName("AccountService");
        exporter.setRegistryPort(RMI_PORT);
        return exporter;
    }

}