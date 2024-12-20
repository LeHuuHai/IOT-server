
# API Documentation

## Endpoints

### POST auth/login-bagic
- **Description**: Đăng nhập.

curl --location 'localhost:8080/auth/login-basic' \
--header 'Content-Type: application/json' \
--data '{
"username": "test",
"password": "123"
}'

**Response**:
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDYyNzI2NiwiZXhwIjoxNzM0NjMwODY2fQ.3ibQtEgOTJ8EQuQR_In4M_jPJs-vFY1gosJOAevxT0aZhrTVHVva6U0a9FlsZy5QV-rEUB9be70BF3lDZTlb4g",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6InRlc3QiLCJpYXQiOjE3MzQ2MjcyNjYsImV4cCI6MTczNTIzMjA2Nn0.HrNGi5wXw0O-8t2pcuo4811-RXuPdwaLO45euvTgDQrhWIp1SwQ6b59mUB6oz8nT7Ws5lpmanQFJ3gyVmTyx4g"
}
```
### GET auth/refresh-token
- **Description**: Lấy token mới.
  
curl --location 'localhost:8080/auth/refresh-token' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6InRlc3QiLCJpYXQiOjE3MzQ2MjY0NTksImV4cCI6MTczNTIzMTI1OX0.HtImwWlz7Xw8NS1S3KDSxjVXzA7MAcmrCkpPb-HJpJ2kfem5dsUXdpquQbR66ngfrai22QR2FwIk_0y_RPOsoQ'

#### Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDYyNzI0MywiZXhwIjoxNzM0NjMwODQzfQ.5DctqPcTkYU3aNXi4iviUEHEfWSyDG-fPCLnjD3snMjeI6GsIoRNiVrbTKY7Yu-MUtLoImd7q-M-4WrW4dGucw",
  "refreshToken": null
}
```
### GET api/devices
- **Description**: Lấy danh sách thiết bị.

curl --location 'localhost:8080/api/devices' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDYyOTAwNiwiZXhwIjoxNzM0NjMyNjA2fQ.0lefV4baHfGvnZ-4UW58nbDZBChLfeSqJJnI5aNvalOUI2x0q7zPAd_2Gz7c9NvW2W9y1V-FfKbAKbg6g6-YtA'

#### Response:
  ```json
  [
    {
      "id": {
        "timestamp": 1734624811,
        "date": "2024-12-19T16:13:31.000+00:00"
        },
        "deviceId": "0123456789",
        "publicKey": "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8rQLcXYCRHvmYqFdgh46cTw17\nW5Ec6IimOb2yB0mJ3A1vmX/J2zZbq+u5D745wmlRfUxJAP1ZFKctel0yaElx5Ts4\naRmG66UG7i63+HyPmStRaglL20+ZhDcnK2YvDk5yzjxQ3PXZvR3QpvD/d3hR/TDY\na0ojhU/bXgvK6Cq74QIDAQAB",
        "name": "Độ ẩm 001",
        "low": 40.5,
        "high": 70.3,
        "soilMoistureData": []
    },
    {
      "id": {
        "timestamp": 1734629071,
          "date": "2024-12-19T17:24:31.000+00:00"
        },
      "deviceId": "1234567890",
      "publicKey": "",
      "name": "Độ ẩm 002",
      "low": 50.35,
      "high": 80.34,
      "soilMoistureData": []
    }
  ]
```
### GET api/threshold/{deviceId}
- **Description**: Lấy dữ liệu ngưỡng độ ẩm đất của thiết bị.
- **Parameters**:
  - `deviceId`: ID của thiết bị cần lấy dữ liệu.
  
curl --location 'localhost:8080/api/threshold/0123456789' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDYyOTAwNiwiZXhwIjoxNzM0NjMyNjA2fQ.0lefV4baHfGvnZ-4UW58nbDZBChLfeSqJJnI5aNvalOUI2x0q7zPAd_2Gz7c9NvW2W9y1V-FfKbAKbg6g6-YtA'



### POST api/soilMoisture
- **Description**: Gửi dữ liệu `SoilMoistureData` lên server.

curl --location 'localhost:8080/api/soilMoisture' \
--header 'Content-Type: application/json' \
--data '{
"time": "2024-12-19T14:45:54",
"soilMoistureValue": 11.05,
"deviceId": "1234567890",
"signature": "OUrMPCqNL8CtO5ZrIfKgn4lAIFCNerBCz+dIn4jRf6DsVVsS4pMBwn4oeCGZhC2L/uHMhvD1Uo9MbDXL25uS4KgN+VHF/kFoWm7zJmBwef3gEAEmTD9SLLXZbkLUe3D9A9WCNiXB4EQHW6SxD06EyTFlwwlUBatfNiSuM8jvPno="
}'

### GET api/soilMoisture/today/{deviceId}
- **Description**: Lấy dữ liệu độ ẩm đất trong ngày của thiết bị.
- **Parameters**:
- `deviceId`: ID của thiết bị cần lấy dữ liệu.

curl --location 'localhost:8080/api/soilMoisture/today/1234567890' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDY4MDkwMywiZXhwIjoxNzM0Njg0NTAzfQ.PLIl9NE2zuynvRjiAaS-xFf8PuFHq-sVwtZQihQcqKwZ23bL9_QNF_KpJrSDVnlgSuAkANVuQOBN53wGKHd83Q'

#### Response:
```json
[
  {
  "id": {
    "timestamp": 1734681600,
    "date": "2024-12-20T08:00:00.000+00:00"
    },
  "time": "2024-12-20T14:45:54",
  "soilMoistureValue": 20.05
  },
  {
  "id": {
    "timestamp": 1734681656,
    "date": "2024-12-20T08:00:56.000+00:00"
    },
  "time": "2024-12-20T14:45:54",
  "soilMoistureValue": 21.05
  }
]
```

### GET api/soilMoisture/last-week/{deviceId}
- **Description**: Lấy dữ liệu độ ẩm đất của thiết bị trong 7 ngày gần nhất, data được tính trung bình theo giờ
- **Parameters**:
    - `deviceId`: ID của thiết bị cần lấy dữ liệu.

curl --location 'localhost:8080/api/soilMoisture/last-week/1234567890' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiYWNjZXNzIiwic3ViIjoidGVzdCIsImlhdCI6MTczNDY4MDkwMywiZXhwIjoxNzM0Njg0NTAzfQ.PLIl9NE2zuynvRjiAaS-xFf8PuFHq-sVwtZQihQcqKwZ23bL9_QNF_KpJrSDVnlgSuAkANVuQOBN53wGKHd83Q'

#### Response:
```json
[
    {
        "id": null,
        "time": "2024-12-19T14:00:00",
        "soilMoistureValue": 11.05
    },
    {
        "id": null,
        "time": "2024-12-20T14:00:00",
        "soilMoistureValue": 20.55
    },
    {
        "id": null,
        "time": "2024-12-14T12:00:00",
        "soilMoistureValue": 27.71
    }
]
```

### GET /registration
- **Description**: Đăng ký WebSocket để nhận thông báo.

### /topic/alert
- **Description**: Đăng ký để nhận thông báo từ topic "alert" khi có sự kiện.

## Client - Đăng ký WebSocket nhận Alert

Dưới đây là cách đăng ký WebSocket client để nhận alert từ server.

```javascript
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/registration' // Đảm bảo kết nối đúng URL của WebSocket
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    // Đăng ký nhận dữ liệu từ topic /topic/alert
    stompClient.subscribe('/topic/alert', (message) => {
        // Giải mã dữ liệu nhị phân từ message body
        const alertData = JSON.parse(new TextDecoder().decode(message._binaryBody));
        console.log('Alert received:', alertData);
    });
};

// Kích hoạt kết nối WebSocket
stompClient.activate();
```

- **Giải thích**:
    - `brokerURL`: Địa chỉ WebSocket server.
    - `stompClient.onConnect`: Được gọi khi kết nối WebSocket thành công.
    - `stompClient.subscribe`: Đăng ký nhận thông tin từ một topic, trong trường hợp này là `/topic/alert`.
    - `TextDecoder().decode(message._binaryBody)`: Giải mã dữ liệu nhị phân (`_binaryBody`) thành chuỗi JSON và xử lý.

---

