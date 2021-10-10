# Start
- ./mvnw spring-boot:run

# H2 Console
http://localhost:8080/h2-console

# Test
- curl http:/localhost:8080/employees/byBean
- curl http:/localhost:8080/employees/byTemplate
- curl http:/localhost:8080/employees/byOriginal

- curl http:/localhost:8080/mybean/byTemplate
- curl http:/localhost:8080/mybean/byOriginal