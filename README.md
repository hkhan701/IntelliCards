# A01-G01-IntelliCards

## Startup Guide for Iteration 2

| :memo:        | Please use portrait mode for the tablet to ensure proper user experience.       |
|---------------|:------------------------|

### Changing Data Sources

Graders can change between data sources by changing the variable "datasource" in "app/src/main/java/comp3350/intellicards/Application/Configuration.java"
The following variable values are recognized:
- "hsqldb"
- "testHsqldb"
- "stub"

If an unrecognized variable value is declared, the app will default to the HSQLDB

### Login Page

Graders can login with the following pre-inserted usernames and passwords unless they are using the Test HSQLDB. They contain fake data.

| Username | Password |
|----------|----------|
| user1    | pass1    |
| user2    | pass2    |
| user3    | pass3    |

- You can sign up with a new username and password. Once it is successful you will be able to login with it.

- You can also login as a guest user but will be limited by the features they can use.

### Notifications

Upon clicking the notification button, graders should allow permissions for notifications, and then toggle on the alarm permissions as well for the notifications to work as intended. 
