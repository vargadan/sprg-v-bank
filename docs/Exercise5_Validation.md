# Exercise 5 - Validation

In this exercise we are going to implement very basic validation in the v-bank app. 

## Fields to validate
As you may have already seen in the previous exercises.  
A number of input fields are vulnerable to XSS and SQLi attacks.  
For example:
- username field on the login screen is vulnerable to SQL injection
- account number field on the transaction form is vulnerable to both SQLi and XSS
- comment field on the transaction form is vulnerable to both SQLi and XSS

We have seen already seen how escaping in the view template and using prepared statements can mitigate these vulnerabilities.
Yet, as an additional line of defences, validation can also mitigate these vulnerabilities. Moreover, proper validation is also
necessary for the successful execution of business logic; otherwise invalid input will lead to bugs.  
Therefore, when validation is done properly we can hit two birds with the one stone.

## Username validation
Since the username can be entered by anyone on the login screen before authentication, its validation is of top-most priority.

A username is considered valid when it adheres to the below rules:
  - not empty
  - min 3 max 30 characters long
  - only supports word characters [A-Za-z0-9_] or \\w

In our very simple design all validation takes place in the ``ch.hslu.sprg.vbank.validator.Validator`` class.

### Specify and Test
Please open ``ch.hslu.sprg.vbank.validator.UserNameValidationTest`` this is a simple unit test testing the above defined criterias.  
You can see that it tests not only valid and boundary values, but also possible SQLi and XSS payloads and inputs of extreme length. 
 
### Implement
Please implement the validation logic in method _Validator.validateUserName_.
You should be fine when the above test runs successfully.

```java
    public void validateUserName(String username) {
        if (username == null || username.length() == 0) {
            //it cannot be null
            throw new ValidationException("Username is required");
        } else if (username.length() < 3) {
            //it should be 11 long
            throw new ValidationException("Username should be at least 3 characters long");
        } else if (username.length() > 30) {
            //it should be 11 long
            throw new ValidationException("Username should be no longer than 30 characters");
        } else if (!username.matches(USERNAME_PATTERN)) {
            //it has to match patter
            throw new ValidationException("Username is in invalid format");
        }
    }
```

### Plug username validation into authentication logic
Validating the username should happen as early as possible, before any validation takes place.
``VBankAuthenticationProvider.loadUserByUsername(String uname)`` is the first method in our control which receives the username upon authentication.  

Please call _Validator.validateUserName_ at the very beginning of this method.

You may now try entering invalid input on the login screen.

## Validating transaction and account details
Please switch to git branch deep_modeling   
In IntelliJ: VCS -> Git -> Branches -> select origin/deep_modeling

Now that you are in the right branch.  
In this branch validation is enforced with custom types:
- UserName: can hold only a valid username
- AccountNumber: can hold only a valid account number (adhering to the pattern 1-23456-78)
- Amount: can hold only a valid account number (positive max 2 decimal points)
- Currency: : can hold only a valid currency (CHF,USD,EUR,GBP)

### Review Changes:
Please open below classes:
- ``ch.hslu.sprg.vbank.model.domainprimitives.UserName``: you can see that this class enforces validity rules in its constructor.   
The same applies to the ``AccountNumber, Amount`` classes. 
``Currency`` is an enum class, it has no constructor, one can only create a valid instance out of a pre-defined set. 
- ``ch.hslu.sprg.vbank.validator.UserNameValidationTest``: you can see that the validation testing is now done against the constructor of the model class.
- ``ch.hslu.sprg.vbank.service.AccountService``: this interface and the implementing classes now only accept custom types of our domain that enforce validation rules.  
This gives us a lot more guarantees that the data our application works with is valid in terms of business rules and it does not contain malicious XSS/SQLi payloads neither (provided, if the validation rules are strict enough).

##Task
``Transaction.comment`` is still a 'primitive' string type.  
Please refactor it to a custom type that resists XSS and SQLi payloads. 