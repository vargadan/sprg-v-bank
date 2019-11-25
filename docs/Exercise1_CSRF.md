# Exercise 1 - CSRF

This exercise is to help you understand Cross Site Request Forgery and its most common mitigation

## Setup and Start Applications

1. start the application in debug mode
   * start the Maven configuration for the launch configuration "v-bank run and build" in DEBUG mode (with the green BUG icon next to the arrow;
   you should have created it as described in IntelliJSetup.md)
1. open v-bank application ati and log in 
   1. open http://localhost:8080/ in the browser
   1. login as 'Victim' with password 'Victim_01'
     * you may open the file _data.sql_ to see what accounts have been created on application start
   1. send some money (i.e. 100 CHF) to account 1-123456-02 with some intelligible note  
   1. go the transaction history page by clicking on the account number on the home page to see the transaction executed
1. now on behalf of the Attacker user we execute a CSRF attack against the Victim user. For the sake of simulation we will wear both hats.
  1. make sure that your are logged in as 'Victim' in the v-bank
  1. Now as the Victim user open the below page in the same browser where v-bank transaction history page is opened:
     * http://sprg-tools.el.eee.intern/csrf_local.html
     * open its source view
```html
     <body onload="document.forms.item(0).submit(); document.getElementById('h3').innerText='Your CSRF request has been successfully sent.'">
     <h2 class="hello-title">Thank you for visiting this page.</h2><h3 id="h3"></h3>
     <form action="http://localhost:8080/doTransfer" method="post" target="hiddenFrame" id="csrfTransferForm">
         <input name="fromAccountNo" type="hidden" value="1-123456-01">
         <input name="toAccountNo" type="hidden" value="1-123456-07">
         <input name="amount" type="hidden" value="1000">
         <input name="currency" type="hidden" value="CHF">
         <input name="note" type="hidden" value="You have been CSRF-d!">
     <!--    <input type="submit" value="CLICK HERE and win Millions $$$!" />-->
     </form>
```  
 * it contains a populated hidden form with the Victim's and the Attacker's account number.
 * it submits the form automatically on page load (body.onload attribute)
 * the Victim will not see anything from this as the response is targeted to a hidden frame.
  1. then go back to the transactions page and refresh it 
    * you should see that you are 1000 CHF worse off because you have been CSRF-ed
  
## Understand how the CSRF attack is working with 

Please only do it if you feel confident with IntelliJ and the Burp proxy tool

  1. setup tools for HTTP interception and Java debugging
     * start the Burp tool to intercept http calls
        * in the Proxy > Options tab change the proxy port from 8080 to 8181 so that it does not conflict with the v-bank application
        * change your browser's proxy to 127.0.0.1:8181 in its settings
        * make sure that Intercept is ON in the Burp tool (Proxy > Intercep tab)\
        Important! : please do not open any other pages in the Browser while doing this exercise as their requests will also be intercepted by the Burp proxy and it may easily confuse you. 
     * Place a debug breakpoint in *BankController.doTransfer(...)* (*BankController* class *doTransfer* method)
  1. refresh any v-bank application page (i.e. the home page at http://vbank.127.0.0.1.xip.io:8080/) and intercept the http request 
     * Please note the JSESSION cookie value in the Burp tool (this is the ID of Bob's current user session)
     * Forward this and all subsequent requests
  1. go to the csrf attack page and click on the button again so that the CSRF call gets interecpted by Burp
     * Please note the JSESSION cookie value in the Burp tool (it should be the same as the one with the previous application request)
     * Forward this and all subsequent requests
  1. Now execution should stop at the Breakpoint
     * if you expand the transaction method parameter its properties should be properly populated
     * if you resume the program (F9 in IntelliJ) the transaction will be properly executed
     
## Mitigations
* Possible mitigations against CSRF
  * protect session cookie with samesite attributes 
    * lax if normal GET requests are safe and modifications are behind POST (or PUT/DELETE)
    * strict otherwise
    * unfortunately depends on web-framework / server if possible
  * protect forms with CSRF token
    * additional token 
* for more detailed explanations please see: https://github.com/OWASP/CheatSheetSeries/blob/master/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.md

## Fix
* since same-site session cookies are not supported by the current application framework (JEE Servlet 2.3 and Spring 5) we have to revert to other methods
* Spring security support CSRF tokens out of the box, which is disabled in this exercise so that CSRF can be demonstrated
* we are going to add our own CSRF filter (filter means that it intercepts all incoming requests and responses and can block/change them) instead to understand how token based mitigation works against CSRF.\
The code of a simple anti CSRF filter:
```java
package ch.hslu.sprg.vbank.filter;

import javax.servlet.*;
import javax.servlet.http.*;

@org.springframework.stereotype.Component
@org.springframework.core.annotation.Order(1)
public class CsrfFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
        validate((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
        setToken((HttpServletRequest) request, (HttpServletResponse) response);
    }

    /**
     * Check the POST request if the container a valid _csrf parameter as csrf protection token
     *
     * @param request
     * @param response
     * @throws ServletException
     */
    private void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if ("POST".equals(request.getMethod().toUpperCase())) {
            String csrfTokenIn = request.getParameter("csrfToken");
            String csrfTokenExp = (String) request.getSession().getAttribute("csrfTokenAttribute");
            if (!csrfTokenExp.equals(csrfTokenIn)) {
                throw new ServletException("Invalid CSRF token!");
            }
        }
    }

    /**
     * Sets the csrf token value as a session attribute with name 'csrfProtectionToken'
     *
     * @param request
     * @param response
     */
    private void setToken(HttpServletRequest request, HttpServletResponse response) {
        String csrfProtectionToken = (String) request.getSession(true).getAttribute("csrfTokenAttribute");
        if (csrfProtectionToken == null) {
            csrfProtectionToken = java.util.UUID.randomUUID().toString();
            request.getSession().setAttribute("csrfTokenAttribute", csrfProtectionToken);
        }
    }
}
```  
This component is a so called filter intercepting all incoming HTTP requests and it does two things:
1. It checks if the CSRF token exists in the session. If not it creates a token and saves it in the session as a session attribute.\
The view component should read it from the session and place it in the form as a hidden value. (Template languages, such as JSP, usually provide easy ways to read session attributes.)
1. It checks all incoming POSTS requests if they contain a valid CSRF token by reading the token value from the request parameters and comparing it with the value saved in the session. 
(It checks only POSTS requests becayse those are meant to write resources, i.e. create transactions)
It throws an Exception if there is no valid CSRF token value coming in as a parameter with the POST request.
\
Add the a hidden input of name *_csrf* to the transfer form in *transfer.jsp* to make sure that the CSRF token value is sent as a parameter with the POST request when saving the transaction:\
` <input type="hidden" name="_csrf" value="${csrfProtectionToken}"/>`
\(in _transfer.jsp_)
### Apply fix
* Copy-paste above code into CsrfFilter.java (the placeholder should be prepared for you).
* Open *transfer.jsp* and verify that the token is being placed into the form 

## Verify fix
Having added the CsrfFilter and the token parameter to the form the CSRF request from the attacker site should be blocked.
You can verify it by placing a break-point in *CsrfFilter.validate(...)*
1. So please put a breakpoint at CsrfFilter.java:26 (line 26)
1. Open the CSRF page at http://sprg-tools.el.eee.intern/csrf_local.html
  * your break-point should be hit and you should see it block the request and throw _ServletException("Invalid CSRF token!")_
1. Send a transaction again via the transaction page
  * your break-point should be hit again and it should let the request be processed 