# time-precision-example
## Differences in implementation of time precision in Java 8, PostgreSQL and H2 databases can lead to not-so-easy-to-find errors.

Java 8 LocalDateTime gives us precision of nanosecond [java docs](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html),
PostgreSQL provides precision of microseconds [postgresql docs](https://www.postgresql.org/docs/9.1/datatype-datetime.html)
H2 gives us again precision of nanoseconds [h2database docs](http://www.h2database.com/html/datatypes.html#timestamp_type)
and this can be a problem, because it's common to use H2 as a test database for applications that works in production environment with PostgreSQL.

Here we have an example of such a situation. We have a database table with events consisting of only two fields: id and start. Field
start is of java 8 type LocalDateTime and is represented as TIMESTAMP WITHOUT TIMEZONE in H2 and PostgreSQL both.
We want to find number of events that start up to the end of a chosen day.

Our function:
```
     public long findEventCountIfStartIsLessOrEqualBadVersion(LocalDate day) {
         LocalDateTime dayEnd = LocalDateTime.of(day, LocalTime.MAX);
         return eventRepository.findEventCountIfStartIsLessOrEqual(dayEnd);
     }
```    
using repository function:
```
    @Query("Select count(*) from Event event " +
            "where event.start <= :dateTime")
    long findEventCountIfStartIsLessOrEqual(@Param("dateTime") LocalDateTime dateTime);
```
fails. In our PostgreSQL test (TimePrecisionExamplePostgresTests.java) this function should return 1, but it returns 2. (Test data is populated by data.sql).
What makes things even worse is the fact that the same function with the same data but using H2 works correctly - it returns 1.
Things make clear if we look into database log.

We invoke the tested function with day = "2020-11-12" and PostgreSQL log looks like: 
> select count(*) as col_0_0_ from event event0_ where event0_.start<=$1","parameters: $1 = '2020-11-13 00:00:00'",,,,,,,,"PostgreSQL JDBC Driver". 

However, H2 log looks in this way:
> select count(*) as col_0_0_ from event event0_ where event0_.start<=? {1: TIMESTAMP '2020-11-12 23:59:59.999999999'}.

Time precision makes the difference. PostgreSQL rounds java LocalDateTime parameter '2020-11-12T23:59:59.999999999' up to '2020-11-13 00:00:00',
whereas H2 keeps the precision.

In this case, to my mind, the simplest solution is to rewrite the function in question in the following manner:
```
    public long findEventCountIfStartIsLessOrEqual(LocalDate day) {
        LocalDate nextDay = day.plusDays(1);
        LocalDateTime nextDayStart = LocalDateTime.of(nextDay, LocalTime.MIN);

        return eventRepository.findEventCountIfStartIsLess(nextDayStart);
    }
```
and appropriate repository function as well:
``` 
     @Query("Select count(*) from Event event " +
             "where event.start < :dateTime")
     long findEventCountIfStartIsLess(@Param("dateTime") LocalDateTime dateTime);
```     
This approach is correct for PostgreSQL and H2 what can be seen in tests.
 
To run application or tests change application.properties. Then after `mvn clean install` run application: `java -jar time-precision-example-0.0.1.jar`
or `java -jar time-precision-example-0.0.1.jar --spring.profiles.active=h2` if you want to see how it works with H2.
Now open [localhost:8080](http:://localhost:8080) in you browser.