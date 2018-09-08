# Transfer API test

## Install and Run

```bash
mvn clean install

java -jar target/transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## Startup data
When starting, the application will load the following data in a in-memory DB from
```
src/main/resources/init.sql
```

## REST API

### Accounts API
It shows the accounts balance stored in memory.

```bash
curl -X GET http://localhost:9998/accounts
```
### Transfer API
It transfers an amount from an account to another.
```bash
curl -X POST \
  http://localhost:9998/transfer \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
"fromAccount":1,
"toAccount": 2,
"amount":2
}'
```

## Assumptions

1. All Data is stored in memory using an in memory Db (h2 db)
2. The application moves funds from an account to another, no transaction records are stored
3. The accounts balance is a long number, assuming that 1L is 1cent