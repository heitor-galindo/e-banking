# Capstone Project - Microservices Architecture

## KAFKA

Create topic

```bash
docker exec -it -w /opt/kafka/bin capstone-security-kafka-1 \
  ./kafka-topics.sh --create \
    --topic reservation-event \
    --bootstrap-server localhost:9092 \
    --partitions 3 \
    --replication-factor 1
```

Show messages

```bash
docker exec -it -w /opt/kafka/bin capstone-security-kafka-1 \
  ./kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic reservation-event \
    --group consumer-reservation \
    --from-beginning
```

## TEST

Get token

```bash
curl --location 'http://localhost:8080/realms/MsSecurity/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=ms-reservation' \
--data-urlencode 'client_secret=123asd'
```

Create reservation

```bash
curl --location --request POST 'http://localhost:8086/reservations?studentId=1' \
--header 'Authorization: Bearer xxxxxxxxxxx'
```

Get reservations

```bash
curl --location 'http://localhost:8086/reservations' \
--header 'Authorization: Bearer xxxxxxxxxxxx'
```

Get students

```bash
curl --location 'http://localhost:8084/students/all' \
--header 'Authorization: Bearer xxxxxxxxxxxx'
```