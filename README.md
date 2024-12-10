
# API Documentation

## Models

### SoilMoistureData
```java
String deviceId;
LocalDateTime time;
float soilMoistureValue;
```

### Threshold
```java
String deviceId;    // (key)
float low;
float high;
```

### Alert
```java
float soilMoisture;
State state;
```

## Endpoints

### POST api/soilMoisture
- **Description**: Gửi dữ liệu `SoilMoistureData` lên server.
- **Request Body**:
  ```json
  {
    "deviceId": "device-001",
    "time": "2024-12-01T16:51:25",
    "soilMoistureValue": 4095
  }
  ```

### POST api/soilMoisture/list
- **Description**: Gửi danh sách các `SoilMoistureData` lên server.
- **Request Body**:
  ```json
  [
    {
      "deviceId": "device-001",
      "time": "2024-12-01T16:51:25",
      "soilMoistureValue": 4095
    },
    {
      "deviceId": "device-002",
      "time": "2024-12-01T16:51:26",
      "soilMoistureValue": 3500
    }
  ]
  ```

### GET api/soilMoisture/today/{deviceId}
- **Description**: Lấy dữ liệu độ ẩm đất trong ngày của thiết bị.
- **Parameters**:
    - `deviceId`: ID của thiết bị cần lấy dữ liệu.

### GET api/soilMoisture/last-week/{deviceId}
- **Description**: Lấy dữ liệu độ ẩm đất của thiết bị trong 7 ngày gần nhất.
- **Parameters**:
    - `deviceId`: ID của thiết bị cần lấy dữ liệu.

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

