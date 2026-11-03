# Graph Report - TrainTicketManagementClient  (2026-11-03)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 1536 nodes · 4223 edges · 55 communities (41 shown, 14 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 243 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `975f9f32`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- BasePanel
- list
- java.awt.event.MouseEvent
- QLTuyenDuongPanel
- QLLichTrinhPanel
- QLGhePanel
- BaseFrame
- MainController
- MainController.java
- QLNhanVienPanel
- QLKhachHangPanel
- ThongTinCaNhanDialog
- java.awt.event.ActionListener
- NhanVienResponse
- DoitMatKhauDialog
- java.awt.event.ActionEvent
- TKToaTauPanel
- QLGaTauPanel
- QLLoaiToaPanel
- QLVeTauPanel
- TabDoanhSo
- QLToaTauPanel
- .createInputField
- GaTauApiClient
- TKKhachHangPanel
- .QLNhanVienController
- TKNhanVienPanel
- TKVeTauPanel
- .showError
- LoaiToaApiClient
- TKGhePanel
- KhachHangApiClient
- TKTauPanel
- TabDoanhThuTheoTuyen
- TabKhachHangVip
- TabTyLeLapDay
- lombok.Data
- TKTuyenDuongPanel
- TKLoaiToaPanel
- ToaTauApiClient
- QLKhachHangController
- TabDoanhThuTheoNgay
- TKGaTauPanel
- TKLichTrinhPanel
- KhachHangRequest
- Override
- TKLichTrinhController
- TrangThaiVe
- .showTrangChu
- .actionPerformed
- .actionPerformed
- .actionPerformed
- com.nmptt:ticket-client

## God Nodes (most connected - your core abstractions)
1. `BasePanel` - 56 edges
2. `QLVeTauPanel` - 45 edges
3. `QLNhanVienPanel` - 44 edges
4. `MainFrame` - 43 edges
5. `QLKhachHangPanel` - 38 edges
6. `QLLichTrinhPanel` - 38 edges
7. `MainController` - 36 edges
8. `QLTuyenDuongPanel` - 34 edges
9. `ThongTinCaNhanDialog` - 33 edges
10. `QLGaTauPanel` - 30 edges

## Surprising Connections (you probably didn't know these)
- `DoanhSoController` --references--> `ThongKeApiClient`  [EXTRACTED]
  src/main/java/com/npmtt/ticketclient/controller/thongke/DoanhSoController.java → src/main/java/com/npmtt/ticketclient/apiclient/ThongKeApiClient.java
- `DoanhThuTheoNgayController` --references--> `ThongKeApiClient`  [EXTRACTED]
  src/main/java/com/npmtt/ticketclient/controller/thongke/DoanhThuTheoNgayController.java → src/main/java/com/npmtt/ticketclient/apiclient/ThongKeApiClient.java
- `DoanhThuTheoTuyenController` --references--> `ThongKeApiClient`  [EXTRACTED]
  src/main/java/com/npmtt/ticketclient/controller/thongke/DoanhThuTheoTuyenController.java → src/main/java/com/npmtt/ticketclient/apiclient/ThongKeApiClient.java
- `KhachHangVipController` --references--> `ThongKeApiClient`  [EXTRACTED]
  src/main/java/com/npmtt/ticketclient/controller/thongke/KhachHangVipController.java → src/main/java/com/npmtt/ticketclient/apiclient/ThongKeApiClient.java
- `TyLeLapDayController` --references--> `ThongKeApiClient`  [EXTRACTED]
  src/main/java/com/npmtt/ticketclient/controller/thongke/TyLeLapDayController.java → src/main/java/com/npmtt/ticketclient/apiclient/ThongKeApiClient.java

## Import Cycles
- None detected.

## Communities (55 total, 14 thin omitted)

### Community 0 - "BasePanel"
Cohesion: 0.06
Nodes (17): ThongKeApiClient, TypeToken<ApiResponse<List<DoanhThuBayNgayDTO>>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<DoanhThuTheoNgayDTO>>>, TypeToken<ApiResponse<Object>>, DashboardController, DoanhThuTheoNgayDTO, GioiTinh (+9 more)

### Community 1 - "list"
Cohesion: 0.07
Nodes (34): AuthApiClient, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<NhanVienResponse>>, GheApiClient, TypeToken<GheResponse>, TypeToken<ApiResponse<Void>>, TypeToken<List<GheResponse>>, TypeToken<GheResponse> (+26 more)

### Community 2 - "java.awt.event.MouseEvent"
Cohesion: 0.06
Nodes (11): TableMouseClickListener, TableMouseListener, TableMouseClickListener, QLTauController, ResetFormListener, SuaTauListener, TableMouseClickListener, ThemTauListener (+3 more)

### Community 3 - "QLTuyenDuongPanel"
Cohesion: 0.06
Nodes (12): TypeToken<ApiResponse<TuyenDuongResponse>>, TypeToken<ApiResponse<TuyenDuongResponse>>, TypeToken<ApiResponse<TuyenDuongResponse>>, TypeToken<ApiResponse<TuyenDuongResponse>>, AddTuyen, EditTuyen, QLTuyenDuongController, RemoveTuyen (+4 more)

### Community 4 - "QLLichTrinhPanel"
Cohesion: 0.05
Nodes (11): TypeToken<ApiResponse<LichTrinhResponse>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<LichTrinhResponse>>, TypeToken<ApiResponse<Object>>, LichTrinhRequest, TrangThaiLichTrinh, CHO, DANG_CHAY (+3 more)

### Community 5 - "QLGhePanel"
Cohesion: 0.08
Nodes (8): QLGheController, ResetFormListener, SuaGheListener, TableMouseClickListener, ThemGheListener, XoaGheListener, GheRequest, QLGhePanel

### Community 6 - "BaseFrame"
Cohesion: 0.07
Nodes (6): DangNhapController, ExitListener, LoginListener, WindowCloseListener, BaseFrame, DangNhapFrame

### Community 8 - "MainController"
Cohesion: 0.08
Nodes (19): DangXuatListener, DoiMatKhauListener, MainController, QLGaTauListener, QLKhachHangListener, QLLichTrinhListener, QLNhanVienListener, QLTauListener (+11 more)

### Community 9 - "MainController.java"
Cohesion: 0.11
Nodes (13): TauApiClient, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<TauDTO>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<TauDTO>>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<TauDTO>> (+5 more)

### Community 13 - "java.awt.event.ActionListener"
Cohesion: 0.16
Nodes (5): QLTuyenDuongListener, TKKhachHangListener, TKTuyenDuongListener, TKVeTauListener, MainFrame

### Community 14 - "NhanVienResponse"
Cohesion: 0.11
Nodes (11): NhanVienApiClient, TypeToken<ApiResponse<NhanVienResponse>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<NhanVienResponse>>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<NhanVienResponse>>, TypeToken<ApiResponse<Object>> (+3 more)

### Community 15 - "DoitMatKhauDialog"
Cohesion: 0.10
Nodes (4): DoiMatKhauController, HuyListener, XacNhanListener, DoitMatKhauDialog

### Community 16 - "java.awt.event.ActionEvent"
Cohesion: 0.07
Nodes (13): QLLoaiToaListener, QLToaTauListener, ThongKeDoanhThuTuyenListener, TKGheListener, QuayLaiListener, XacNhanListener, ResetGaTau, ResetListener (+5 more)

### Community 17 - "TKToaTauPanel"
Cohesion: 0.12
Nodes (6): TypeToken<ApiResponse<List<ToaTauResponse>>>, TypeToken<ApiResponse<Object>>, ComboItem, TimKiemListener, TKToaTauController, TKToaTauPanel

### Community 18 - "QLGaTauPanel"
Cohesion: 0.11
Nodes (4): AddGaTau, EditGaTau, RemoveGaTau, QLGaTauPanel

### Community 19 - "QLLoaiToaPanel"
Cohesion: 0.12
Nodes (6): QLLoaiToaController, ResetFormListener, SuaLoaiToaListener, ThemLoaiToaListener, XoaLoaiToaListener, QLLoaiToaPanel

### Community 21 - "TabDoanhSo"
Cohesion: 0.11
Nodes (6): TypeToken<ApiResponse<List<DoanhSoDTO>>>, TypeToken<ApiResponse<Object>>, DoanhSoController, ThongKeListener, DoanhSoDTO, TabDoanhSo

### Community 22 - "QLToaTauPanel"
Cohesion: 0.12
Nodes (4): ComboItem, QLToaTauController, ThemListener, QLToaTauPanel

### Community 24 - "GaTauApiClient"
Cohesion: 0.12
Nodes (11): GaTauApiClient, TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<List<GaTauDTO>>>, TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<GaTauDTO>> (+3 more)

### Community 25 - "TKKhachHangPanel"
Cohesion: 0.12
Nodes (5): LamMoiListener, ResetFormListener, TimKiemListener, TKKhachHangController, TKKhachHangPanel

### Community 26 - ".QLNhanVienController"
Cohesion: 0.14
Nodes (7): QLNhanVienController, RefreshListener, ResetFormListener, SuaNhanVienListener, TableMouseClickListener, ThemNhanVienListener, XoaNhanVienListener

### Community 27 - "TKNhanVienPanel"
Cohesion: 0.12
Nodes (5): LamMoiListener, ResetFormListener, TimKiemListener, TKNhanVienController, TKNhanVienPanel

### Community 28 - "TKVeTauPanel"
Cohesion: 0.14
Nodes (5): RefreshListener, ResetListener, TimkiemListener, TKVeTauController, TKVeTauPanel

### Community 29 - ".showError"
Cohesion: 0.14
Nodes (5): QLVeTauController, RefreshListener, SuaVeTauListener, ThemVeTauListener, XoaVeTauListener

### Community 30 - "LoaiToaApiClient"
Cohesion: 0.15
Nodes (8): LoaiToaApiClient, TypeToken<ApiResponse<LoaiToaDTO>>, TypeToken<ApiResponse<List<LoaiToaDTO>>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<LoaiToaDTO>>>, TypeToken<ApiResponse<LoaiToaDTO>>, TKLoaiToaController, LoaiToaDTO

### Community 31 - "TKGhePanel"
Cohesion: 0.15
Nodes (6): TypeToken<List<GheResponse>>, LamMoiListener, ResetFormListener, TimKiemListener, TKGheController, TKGhePanel

### Community 32 - "KhachHangApiClient"
Cohesion: 0.14
Nodes (9): KhachHangApiClient, TypeToken<ApiResponse<KhachHangResponse>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<KhachHangResponse>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<KhachHangResponse>>>, TypeToken<ApiResponse<Object>> (+1 more)

### Community 33 - "TKTauPanel"
Cohesion: 0.13
Nodes (6): TypeToken<ApiResponse<List<TauDTO>>>, TypeToken<ApiResponse<Object>>, LamMoiListener, ResetFormListener, TimKiemListener, TKTauPanel

### Community 34 - "TabDoanhThuTheoTuyen"
Cohesion: 0.13
Nodes (6): TypeToken<ApiResponse<List<DoanhThuTheoTuyenDTO>>>, TypeToken<ApiResponse<Object>>, DoanhThuTheoTuyenController, ThongKeListener, DoanhThuTheoTuyenDTO, TabDoanhThuTheoTuyen

### Community 35 - "TabKhachHangVip"
Cohesion: 0.13
Nodes (6): TypeToken<ApiResponse<List<KhachHangVipDTO>>>, TypeToken<ApiResponse<Object>>, KhachHangVipController, ThongKeListener, KhachHangVipDTO, TabKhachHangVip

### Community 36 - "TabTyLeLapDay"
Cohesion: 0.13
Nodes (6): TypeToken<ApiResponse<List<TyLeLapDayDTO>>>, TypeToken<ApiResponse<Object>>, ThongKeListener, TyLeLapDayController, TyLeLapDayDTO, TabTyLeLapDay

### Community 37 - "lombok.Data"
Cohesion: 0.14
Nodes (4): VeTauRequest, GheResponse, ToaTauResponse, DoanhThuBayNgayDTO

### Community 38 - "TKTuyenDuongPanel"
Cohesion: 0.16
Nodes (6): TypeToken<ApiResponse<List<TuyenDuongResponse>>>, TypeToken<ApiResponse<TuyenDuongResponse>>, ResetListener, TimkiemListener, TKTuyenDuongController, TKTuyenDuongPanel

### Community 39 - "TKLoaiToaPanel"
Cohesion: 0.13
Nodes (5): TKLoaiToaListener, LamMoiListener, ResetFormListener, TimKiemListener, TKLoaiToaPanel

### Community 40 - "ToaTauApiClient"
Cohesion: 0.11
Nodes (9): ToaTauApiClient, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<ToaTauResponse>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<List<ToaTauResponse>>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<Object>>, TypeToken<ApiResponse<ToaTauResponse>> (+1 more)

### Community 41 - "QLKhachHangController"
Cohesion: 0.18
Nodes (7): QLKhachHangController, RefreshListener, ResetFormListener, SuaKhachHangListener, TableMouseClickListener, ThemKhachHangListener, XoaKhachHangListener

### Community 42 - "TabDoanhThuTheoNgay"
Cohesion: 0.17
Nodes (3): DoanhThuTheoNgayController, ThongKeApiClientListener, TabDoanhThuTheoNgay

### Community 43 - "TKGaTauPanel"
Cohesion: 0.18
Nodes (6): TypeToken<ApiResponse<GaTauDTO>>, TypeToken<ApiResponse<List<GaTauDTO>>>, ResetListener, TimkiemListener, TKGaTauController, TKGaTauPanel

### Community 46 - "Override"
Cohesion: 0.21
Nodes (4): boxGheListener, boxLichTRinhListener, ResetFormListener, TableMouseClickListener

### Community 47 - "TKLichTrinhController"
Cohesion: 0.23
Nodes (4): TypeToken<ApiResponse<List<LichTrinhResponse>>>, TypeToken<ApiResponse<Object>>, TKLichTrinhController, LichTrinhResponse

### Community 48 - "TrangThaiVe"
Cohesion: 0.25
Nodes (4): TrangThaiVe, CHO, DA_THANH_TOAN, HUY

## Knowledge Gaps
- **13 isolated node(s):** `KHAC`, `NAM`, `NU`, `ADMIN`, `NHAN_VIEN` (+8 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 398 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **14 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `BasePanel` connect `BasePanel` to `java.awt.event.MouseEvent`, `QLTuyenDuongPanel`, `QLLichTrinhPanel`, `QLGhePanel`, `.createStyledButton`, `QLNhanVienPanel`, `QLKhachHangPanel`, `TKToaTauPanel`, `QLGaTauPanel`, `QLLoaiToaPanel`, `QLVeTauPanel`, `QLToaTauPanel`, `.createInputField`, `TKKhachHangPanel`, `TKNhanVienPanel`, `TKVeTauPanel`, `.showError`, `TKGhePanel`, `TKTauPanel`, `TKTuyenDuongPanel`, `TKLoaiToaPanel`, `TKGaTauPanel`, `TKLichTrinhPanel`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **What connects `KHAC`, `NAM`, `NU` to the rest of the system?**
  _13 weakly-connected nodes found - possible missing edges._
- **Should `BasePanel` be split into smaller, more focused modules?**
  _Cohesion score 0.06361556064073226 - nodes in this community are weakly interconnected._
- **Why does `QLNhanVienPanel` connect `QLNhanVienPanel` to `BasePanel`, `MainController`, `MainController.java`, `NhanVienResponse`, `.QLNhanVienController`?**
  _High betweenness centrality (0.069) - this node is a cross-community bridge._
- **Should `list` be split into smaller, more focused modules?**
  _Cohesion score 0.06851374570446736 - nodes in this community are weakly interconnected._
- **Why does `QLVeTauPanel` connect `QLVeTauPanel` to `BasePanel`, `KhachHangApiClient`, `lombok.Data`, `MainController.java`, `TKLichTrinhController`, `.actionPerformed`, `.showError`?**
  _High betweenness centrality (0.045) - this node is a cross-community bridge._
- **Should `java.awt.event.MouseEvent` be split into smaller, more focused modules?**
  _Cohesion score 0.06201923076923077 - nodes in this community are weakly interconnected._