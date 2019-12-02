# Exercise 3 - Breaking Authenticaion with SQL injection
In this exercise you will see how a negligent login implementation with an SQL injectable username parameter can allow for spoofing.
## Examine authenitcation code
Please open _VBankAuthenticationProvider.java_ and take a deep look at method _loadUserByUsername()_:
```java
...
    private UserDetails loadUserByUsername(String uname) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = "SELECT USERNAME, PASSWORD FROM USER U WHERE U.USERNAME = '" + uname + "'";
            log.warn(sql);
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                final String username = resultSet.getString("USERNAME");
                final String password = resultSet.getString("PASSWORD");
                return User.withUsername(username).password(password).roles("USER").build();
            } else {
                log.info("No user found with name : " + uname);
                return null;
            }
        }
    }
...
``` 
You may see that:
* the query reads the username and password from the database with a SQL query
* this has a single condition which is username which is put into the SQL query with single string concatenation. Hence, the _uname_ parameter (which is coming from the username field of the login page) is injectable! 

Please find a way to make the query return the username and password pair of your choice:
* Use UNION (ALL) injection
* The second injection does not need to have FROM and WHERE clauses.
Note that if your are in control of the returned username and password pair you still need to enter the same password on the login page.

username is: ' UNION ALL SELECT 'Victim' as USERNAME, 'password' as PASSWORD #
password is: 'password'

## Fix the query:
### with prepared statement
```java
PreparedStatement stmt = connection.prepareStatement("SELECT USERNAME, PASSWORD FROM USER U WHERE U.USERNAME = ?");
stmt.setString(0, uname);
ResultSet resultSet = stmt.executeQuery();
```
Is there any other way to fix this?
What other measures could be taken to make the login page more SQLi resistant?
