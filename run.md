# Run

```bash
# terminal 1 (port 8081)
cd book-service  && mvn -q clean package -DskipTests && java -jar target/book-service-1.0.0.jar
# terminal 2 (port 8080; override with ORDER_SERVICE_PORT=8090 if 8080 is busy)
cd order-service && mvn -q clean package -DskipTests && java -jar target/order-service-1.0.0.jar

curl http://localhost:8081/books/2
curl http://localhost:8080/orders/create/2    # Order created successfully for book: Effective Java
curl http://localhost:8080/orders/create/99   # Book not found, order cannot be created.
```
