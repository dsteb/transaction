# Transaction
Java test coding challenge based on spring-boot.
The application is JSON based REST API for managing simple transactions.


Each Transaction has `id`, `amount`, `type` and `parent_id`.


`amount` is double (you should never use double for money in real applications).


`type` is String.


`parent_id` is optional parameter with parent transaction id.

## Requirements
The requirements are maven 3 and java 1.8.

## Getting started

```
$ mvn compile
$ mvn exec:java
```
The application will start on port 8080.

## Examples

Creating transaction:
```
curl -X PUT -H "Content-Type: application/json" -d '{
	"amount": 5000,
	"type": "cars"
}' localhost:8080/transactionservice/transaction/10
```
```
curl -X PUT -H "Content-Type: application/json" -d '{
	"amount": 10000,
	"type": "shopping",
  "parent_id": 10
}' localhost:8080/transactionservice/transaction/11
```
Get Transaction:
```
curl localhost:8080/transactionservice/transaction/10
```
Get transactions ID by type:
```
curl localhost:8080/transactionservice/types/cars
```
Get transitive sum by parent ID:
```
curl localhost:8080/transactionservice/sum/10
```
