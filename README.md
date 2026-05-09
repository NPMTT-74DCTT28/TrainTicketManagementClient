# TrainTicketManagementClient

Ứng dụng **Java Swing** quản lý bán vé tàu: giao diện máy trạm gọi REST API (`TrainTicketManagementAPI`).

## Yêu cầu

- JDK phù hợp với `pom.xml` (hiện cấu hình **Java 25** cho `maven.compiler.source/target`)
- API backend đang chạy và CSDL đã import

Nếu môi trường chỉ có JDK 17, có thể hạ `maven.compiler.source` / `target` xuống **17** trong `pom.xml` cho đồng bộ với module API (cần kiểm tra lại build).

## Cấu hình kết nối API

File: `src/main/resources/config.properties`

```properties
api.baseurl=http://localhost:8080/api/v1
```

Đổi host/cổng nếu API không chạy tại `localhost:8080`.

## Chạy ứng dụng

### IntelliJ IDEA / Eclipse

1. Mở project Maven **TrainTicketManagementClient**.
2. Chạy class có phương thức `main`:  
   `com.npmtt.ticketclient.controller.core.DangNhapController`

Luồng: màn hình **Đăng nhập** → sau khi thành công mở `MainFrame`, token lưu qua `SessionManager`.

### Dòng lệnh (Maven)

Project chưa cấu hình `exec-maven-plugin` sẵn; có thể thêm plugin hoặc đóng gói và chỉ định main class sau khi tạo JAR runnable.

## Cấu trúc (tóm tắt)

| Gói | Vai trò |
|-----|--------|
| `view` | `JFrame`, `JPanel`, form, Dashboard, thống kê |
| `controller` | Lắng nghe sự kiện, điều phối gọi API |
| `apiclient` | `java.net.http.HttpClient` + Gson tới từng resource |
| `dto` | Request/response đồng bộ với API |
| `util` | `ConfigLoader`, `SessionManager`, … |

## Phụ thuộc chính (Maven)

- Lombok  
- Gson  
- JCalendar  
- JFreeChart  

## Tài liệu API

Đặc tả REST và phân quyền nằm trong module API:  
[TrainTicketManagementAPI/API.md](https://github.com/NPMTT-74DCTT28/TrainTicketManagementAPI)

## Lưu ý phân quyền UI

`MainFrame.hienMenuTheoQuyen` ẩn một số menu với nhân viên thường (ví dụ nhóm **Thống kê**, quản trị nhân viên/ghế…). Trên server vẫn phải bảo vệ bằng JWT và `@PreAuthorize`; menu chỉ giúp giao diện gọn hơn.
