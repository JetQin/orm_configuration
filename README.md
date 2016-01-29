# @EnableOrmConfiguration 

### implement hibernate config use java config annotation

# Parameters:

#####display sql or not

showSql: boolean        default value false;  

#####which package to scan

packageToScan:String[]  default value new String[]{};

#####which way to implementation,Hibernate or JPA

OrmType: enum           default value OrmType.Hibernate;
      OrmType.Hibernate (Hibernate implementation)
      OrmType.Jpa       (JPA implementation)
 

# How to use

[a simple example](https://github.com/JetQin/orm_example) 

*  Create a property file to setup database parameter and hibernate parameter
   ``` 
	connection.driver_class = oracle.jdbc.OracleDriver
	connection.url = jdbc:oracle:thin:@localhost:1521:XE
	connection.username = rdm
	connection.password = 123456
	hibernate.dialect = org.hibernate.dialect.Oracle9iDialect
	
	hibernate.dbcp.initialSize = 10
	hibernate.dbcp.maxActive = 100
	hibernate.dbcp.maxIdle = 10
	hibernate.dbcp.minIdle = 10
   ```
* Add @EnableOrmConfiguration at the main configuration file
  
  ```
   @EnableOrmConfiguration(packageToScan="com.example.domain",showSql=false,enableCache=true)
  
  ```
* Add package "com.github.jetqin.configuration*" to componentScan parameter
  ```
   @ComponentScan(basePackages={"com.example.*","com.github.jetqin.configuration*"})
   
  ```
##### Below is an example configuration
```
@Configuration
@ComponentScan(basePackages={"com.example.*","com.github.jetqin.configuration*"})
@EnableOrmConfiguration(showSql=false,packageToScan="com.example.domain",enableCache=true)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```

# Configuration Properties

```
connection.driver_class = oracle.jdbc.OracleDriver
connection.url = jdbc:oracle:thin:@localhost:1521:ORCL
connection.username = username
connection.password = password
hibernate.dialect = org.hibernate.dialect.Oracle9iDialect

hibernate.dbcp.initialSize = 10
hibernate.dbcp.maxActive = 100
hibernate.dbcp.maxIdle = 10
hibernate.dbcp.minIdle = 10
```

# Maven dependency

```

<dependency>
		<groupId>com.github.jetqin</groupId>
		<artifactId>orm</artifactId>
		<version>1.1.5</version>
</dependency>

```

# Reference [Spring enable annotation](http://www.javacodegeeks.com/2015/04/spring-enable-annotation-writing-a-custom-enable-annotation.html)
