# Program Pengelolaan Nilai Siswa (Java File I/O)

Program Java prosedural sederhana untuk mengelola data nilai siswa. Program membaca data dari konsol, menulisnya ke file teks `laporan_nilai.txt`, lalu membaca kembali isi file tersebut dan menampilkannya di konsol.

## Fitur

- Input jumlah siswa, nama, dan nilai melalui konsol (`Scanner`)
- Penentuan status kelulusan otomatis (nilai ≥ 70 = Lulus, nilai < 70 = Tidak Lulus)
- Penulisan laporan ke file `laporan_nilai.txt` (`FileWriter` + `BufferedWriter`)
- Ringkasan di akhir laporan: rata-rata nilai kelas, jumlah siswa lulus, dan jumlah siswa tidak lulus
- Pembacaan ulang file baris demi baris (`FileReader` + `BufferedReader`, `readLine()` sampai `null`)
- Validasi input: jumlah siswa harus > 0, nama tidak boleh kosong, nilai harus angka antara 0 dan 100 (koma desimal seperti `85,5` juga diterima)
- Exception handling dengan `try-catch` dan `try-with-resources`, sehingga stream otomatis ditutup

## Struktur Project

```
LaporanNilai/
├── LaporanNilai.java     # Source code program
├── laporan_nilai.txt     # Dibuat otomatis setelah program dijalankan
└── README.md
```

## Alur Program

1. Pengguna memasukkan jumlah siswa.
2. Untuk setiap siswa, pengguna memasukkan nama dan nilai. Status kelulusan ditentukan langsung di dalam loop.
3. Data tiap siswa ditulis ke `laporan_nilai.txt`, diikuti ringkasan di akhir file.
4. File dibuka kembali dan seluruh isinya ditampilkan ke konsol.

## Method yang Digunakan

| Method | Fungsi |
|--------|--------|
| `main()` | Mengatur alur program |
| `bacaJumlahSiswa()` | Membaca dan memvalidasi jumlah siswa |
| `bacaNama()` | Membaca nama siswa (tidak boleh kosong) |
| `bacaNilai()` | Membaca dan memvalidasi nilai (0 - 100) |
| `tentukanStatus()` | Menentukan Lulus atau Tidak Lulus |
| `tulisLaporan()` | Menulis data dan ringkasan ke file |
| `bacaLaporan()` | Membaca file dan menampilkannya ke konsol |

## Cara Menjalankan

### Menggunakan NetBeans
1. Buat class baru bernama `LaporanNilai`, lalu tempel kode program.
2. Jika class berada dalam package, tambahkan baris `package namapackage;` di paling atas file.
3. Klik kanan file, lalu pilih **Run File** (`Shift + F6`).
4. File `laporan_nilai.txt` akan muncul di folder project (sejajar dengan `src`).

### Menggunakan Terminal
```bash
javac LaporanNilai.java
java LaporanNilai
```

## Contoh Penggunaan

```
======================================
      PENGELOLAAN NILAI SISWA
======================================
Masukkan jumlah siswa: 2

Data siswa ke-1
Nama  : Andi
Nilai : 85

Data siswa ke-2
Nama  : Budi
Nilai : 60.5

Data berhasil ditulis ke file laporan_nilai.txt

======== ISI FILE laporan_nilai.txt ========
LAPORAN NILAI SISWA
=======================================================
No   Nama                 Nilai    Status
-------------------------------------------------------
1    Andi                 85.00    Lulus
2    Budi                 60.50    Tidak Lulus
=======================================================
RINGKASAN
Rata-rata nilai kelas : 72.75
Jumlah siswa lulus    : 1
Jumlah tidak lulus    : 1
======================================
```

## Teknologi

- Java (JDK 8 atau lebih baru)
- Apache NetBeans IDE
- Package `java.io` (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`, `IOException`) dan `java.util.Scanner`
