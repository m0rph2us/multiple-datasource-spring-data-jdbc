# multiple-datasource-spring-data-jdbc

Multiple datasource example for spring data jdbc. This example need [docker-mysql](https://github.com/m0rph2us/docker-mysql) for the MySQL database.

In the test directory, you can see DemoApplicationTests.kt file which has simple test for multiple datasource.

This example does not use **@Transactional** 's readOnly meta attribute for replica routing. 
Instead, it separates the repository into write and read purpose. 
Well, It might be possible to use the readOnly meta attribute for replica routing. However, that is often tricky if you
do not understand how the routing goes under the hood. 