<div align="center">

# 🍽️ Kasir Kantin UNS

**Aplikasi kasir berbasis Java Swing dengan simulasi multithreading**

*Praktikum PBO Kelas 3B — Pertemuan 11 (Multithreading) & Pertemuan 12 (GUI Programming)*

</div>

---

## 📖 Tentang Proyek

Kantin UNS membutuhkan aplikasi kasir sederhana berbasis jendela. Kasir memasukkan **menu, harga, dan jumlah**, lalu aplikasi menghitung **total bayar** (lengkap dengan diskon). Proyek ini juga memuat simulasi **dua kasir yang melayani pelanggan bersamaan** dan fitur **cetak struk** yang tidak membuat jendela macet.

## ✨ Fitur

| Fitur | Keterangan |
|---|---|
| 🧮 Hitung total otomatis | Diskon **10%** jika subtotal di atas Rp 50.000 |
| 👥 Simulasi dua kasir | Dina dan Eko berjalan bersamaan sebagai `Thread` |
| 🖥️ GUI Swing | Form input, area struk, dan label total |
| ✅ Validasi input | Field kosong dan input non-angka ditangani dengan dialog |
| 🖨️ Cetak struk non-blocking | Proses 2 detik berjalan di thread terpisah |

## 🗂️ Struktur Proyek

```
proyek
├── lab
│   └── kasir
│       ├── Pesanan.java      → Tahap 1 · logika pesanan & diskon
│       ├── Kasir.java        → Tahap 2 · kasir sebagai Thread
│       ├── MainKasir.java    → Tahap 1 & 2 · pengujian konsol
│       └── KasirFrame.java   → Tahap 3 & 4 · GUI & cetak struk
└── README.md
```

> Semua class berada dalam satu package: `lab.kasir`

## 🚀 Cara Menjalankan

**Persyaratan:** JDK 8 atau lebih baru, dan lingkungan desktop untuk GUI.

```bash
# 1. Kompilasi semua class (dari folder yang berisi lab/)
javac lab/kasir/*.java

# 2. Tahap 1 & 2 — konsol
java lab.kasir.MainKasir

# 3. Tahap 3 & 4 — GUI
java lab.kasir.KasirFrame
```

---

## 🧩 Penjelasan Tahap

### Tahap 1 — Class Logika `Pesanan`

| Method | Fungsi |
|---|---|
| `tambahItem(double harga, int jumlah)` | Menambah `harga × jumlah` ke subtotal |
| `getSubtotal()` | Membaca nilai subtotal |
| `hitungTotal()` | Subtotal > 50.000 → dikurangi diskon 10%, selain itu apa adanya |
| `reset()` | Mengembalikan subtotal ke 0 |

> 🔒 Atribut `subtotal` bersifat `private` (enkapsulasi).

**Data uji**

| Menu | Harga | Jumlah |
|---|--:|--:|
| Nasi Goreng | 15.000 | 2 |
| Es Teh | 5.000 | 3 |
| Ayam Bakar | 25.000 | 1 |

**Hasil yang diharapkan**

| Setelah | Subtotal | Total |
|---|--:|--:|
| Menu 1 & 2 | 45.000 | **45.000** (tanpa diskon) |
| Menu 3 | 70.000 | **63.000** (diskon 10%) |

### Tahap 2 — Dua Kasir sebagai Thread

- `Kasir extends Thread` mencetak `Kasir <nama> melayani pelanggan ke-<i>` sebanyak 5 kali, jeda 500 ms.
- `MainKasir` menjalankan **Dina** dan **Eko** dengan `start()`, lalu `join()` keduanya.
- `Semua pelanggan selesai dilayani` selalu tampil **paling akhir**.

Alur singkat:

1. `main` memanggil `dina.start()` dan `eko.start()`
2. Kedua kasir melayani pelanggan 1 sampai 5 secara bersamaan
3. `main` menunggu lewat `dina.join()` dan `eko.join()`
4. `main` mencetak "Semua pelanggan selesai dilayani"

> ⚠️ **Urutan output Dina dan Eko tidak selalu sama** di setiap eksekusi, karena penjadwalan thread diatur oleh scheduler JVM/OS, bukan oleh urutan `start()`.

### Tahap 3 — Tampilan GUI `KasirFrame`

| Komponen | Perilaku |
|---|---|
| **Nama Menu, Harga, Jumlah** | Tiga `JTextField` untuk input |
| **Tambah** | Ambil harga & jumlah → `pesanan.tambahItem(...)` → tulis satu baris ke struk → kosongkan field |
| **Hitung Total** | Tampilkan `pesanan.hitungTotal()` pada label total (format `63.000`) |
| **Area struk** | `JTextArea` dalam `JScrollPane` |
| **Validasi** | `JOptionPane` muncul jika field kosong atau input bukan angka (`NumberFormatException`) |

### Tahap 4 — Cetak Struk dengan Thread

Tombol **Cetak Struk** menjalankan thread baru agar jendela tetap responsif.

Alur singkat:

1. Klik **Cetak Struk** → `new Thread(...).start()`
2. Status menjadi *Mencetak struk...*
3. Thread tidur 2 detik (`Thread.sleep(2000)`)
4. Status menjadi *Struk selesai*

- ✅ Thread berjalan lewat `start()`
- ✅ Tombol **Tambah** dan **Hitung Total** tetap bisa ditekan selama 2 detik menunggu
- ✅ Label berubah dari *Mencetak struk...* menjadi *Struk selesai*
- 🛡️ Update label memakai `SwingUtilities.invokeLater` agar aman untuk Swing

---

## 🖨️ Contoh Output Konsol

```text
=== TAHAP 1: Uji Pesanan ===
Setelah 2 menu -> subtotal: 45000.0, total: 45000.0
Setelah 3 menu -> subtotal: 70000.0, total: 63000.0
Setelah reset  -> subtotal: 0.0

=== TAHAP 2: Dua Kasir ===
Kasir Dina melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-1
Kasir Eko melayani pelanggan ke-2
Kasir Dina melayani pelanggan ke-2
...
Semua pelanggan selesai dilayani
```

## 🧪 Skenario Uji GUI

1. Isi ketiga data uji, tekan **Tambah** setiap selesai mengisi.
2. Tekan **Hitung Total** → label menampilkan `Rp 63.000`.
3. Tekan **Cetak Struk**, lalu coba tekan Tambah atau Hitung Total selama 2 detik.
4. Kosongkan field atau isi huruf pada harga/jumlah untuk melihat pesan validasi.

## 🛠️ Teknologi & Konsep

`Java` · `Swing` · `Thread` · `join()` · `sleep()` · `SwingUtilities` · `Enkapsulasi` · `JOptionPane`

---

<div align="center">

Dibuat untuk **Praktikum PBO Kelas 3B** · Universitas Sebelas Maret 🎓

</div>
