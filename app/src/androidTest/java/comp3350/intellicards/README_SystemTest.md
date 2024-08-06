# Test Database and System Testing

- Graders should not change the data in the "testHsqldb" as it is used for system testing
- System tests must use the test database, if the value of datasource in Configuration.java is not "testHsqldb", the tests will not run and an exception will be thrown prompting the user to change the variable