# orm_configuration

# Parameters:

#####Display sql or not

showSql: boolean        default value false;  

#####which package to scan

packageToScan:String[]  default value new String[]{};

#####which way to implementation,Hibernate or JPA

OrmType: enum           default value OrmType.Hibernate;
      OrmType.Hibernate (Hibernate implementation)
      OrmType.Jpa       (JPA implementation)
 

# How to use

```
@ComponentScan
@EnableAutoConfiguration
@EnableOrmConfiguration(showSql=false,packageToScan="com.usee",OrmType=OrmType.Hibernate)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
