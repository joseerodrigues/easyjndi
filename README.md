# easyjndi
Easily bind Objects of any type via JNDI in standalone java app. Example: Get a DataSource from JNDI


Maven dependency

```xml
<dependency>
  <groupId>io.github.joseerodrigues.utils</groupId>
  <artifactId>easyjndi</artifactId>
  <version>0.0.1</version>
</dependency>
```

## How to use

1. Install easyjndi InitialContextFactory
1. Use the returned _context_ instance and add your jndi mappings

### Sample - create a DataSource in a standalone app

Using [c3p0](https://github.com/swaldman/c3p0) for a datasource implementation

```java
//create a new datasource using c3p0
ComboPooledDataSource cpds = new ComboPooledDataSource();
cpds.setDriverClass("com.ibm.db2.jcc.DB2Driver"); 
cpds.setJdbcUrl("jdbc:db2://myhost:5021/mydb");
cpds.setUser("username");                                  
cpds.setPassword("password");

//setup easyjndi
Context ctx = EasyJndi.install();

//bind JNDI name to dataSource instance
ctx.bind("jdbc/DB", cpds);
```

And that's it!

Now any lookups will return your provided dataSource

```java
InitialContext ctx = new InitialContext();

DataSource ds = ctx.lookup("jdbc/DB");
```