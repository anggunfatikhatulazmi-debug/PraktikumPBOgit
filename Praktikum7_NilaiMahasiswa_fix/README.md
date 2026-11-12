# Program Pengelolaan Nilai Mahasiswa

Program Java berorientasi objek untuk mengelola data nilai mahasiswa. Program membaca input dari pengguna, memvalidasi data, menyimpannya dalam koleksi, menghitung statistik kelas, lalu menyimpan rekapitulasi ke file `laporan_nilai.txt`.

## Struktur Proyek

```
.
├── model
│   ├── Mahasiswa.java               # Kelas data + validasi + grade
│   └── NilaiInvalidException.java   # Custom exception
└── app
    └── Main.java                    # Kelas utama (input, rekap, simpan file)
```

## Cara Menjalankan

Jalankan dari folder yang berisi `model` dan `app`:

```bash
javac -d out model/*.java app/*.java
java -ea -cp out app.Main
```

> Opsi `-ea` mengaktifkan assertion. Tanpa opsi ini, `assert` diabaikan oleh Java, sehingga program menyediakan pengecekan cadangan menggunakan `if`.

## Fitur Utama

- Input data beberapa mahasiswa (nama, NIM, nilai) melalui konsol.
- Validasi NIM dan nilai, dengan pengulangan input jika salah.
- Penentuan grade huruf (A, B, C, D) dan status kelulusan.
- Perhitungan rata-rata nilai, jumlah lulus, dan jumlah tidak lulus.
- Penyimpanan laporan permanen ke `laporan_nilai.txt`.

## Penjelasan Kelas

### `model.Mahasiswa`
Menyimpan atribut `nama` (String), `nim` (String), dan `nilai` (double).

| Komponen | Keterangan |
|---|---|
| `FINAL_PASSING_GRADE` | Konstanta `final` bernilai `70.0` sebagai batas kelulusan |
| Constructor | Merapikan nama/NIM, memvalidasi NIM dan nilai |
| `getGrade()` | Memetakan nilai ke grade dengan if-else |
| `isLulus()` / `getStatus()` | Menentukan LULUS / TIDAK LULUS berdasarkan `FINAL_PASSING_GRADE` |

**Pemetaan grade:**

| Nilai | Grade |
|---|---|
| ≥ 85 | A |
| 70 – 84 | B |
| 55 – 69 | C |
| < 55 | D |

### `model.NilaiInvalidException`
Turunan dari `Exception`. Dilempar oleh constructor `Mahasiswa` jika nilai kurang dari 0 atau lebih dari 100.

### `app.Main`
Kelas pengeksekusi dengan alur sebagai berikut:
1. Meminta jumlah mahasiswa.
2. Membaca data tiap mahasiswa dengan `Scanner`. Jika terjadi error, input diulang.
3. Menyimpan objek ke `ArrayList<Mahasiswa>`.
4. Menghitung total, rata-rata, jumlah lulus, dan jumlah tidak lulus dengan for-each.
5. Menampilkan rekap ke konsol.
6. Menulis laporan ke `laporan_nilai.txt`.

## Pemetaan Materi

| Pertemuan | Materi | Implementasi |
|---|---|---|
| 2 | Package, class, constructor, variabel | Package `model` dan `app`, constructor `Mahasiswa`, konstanta `final` |
| 3 | Kondisional, looping, tipe data | If-else untuk grade, for-each untuk statistik, primitif (`double`, `boolean`, `int`) dan reference (`Mahasiswa`, `String`) |
| 4 | Exception, assertion, String, Character | `NilaiInvalidException`, `assert !nim.isEmpty()`, `trim()`, `toUpperCase()`, `Character.isDigit()` |
| 5 | Collection | `ArrayList<Mahasiswa>` dengan `add()`, `size()`, for-each |
| 6 | I/O dan file | `Scanner`, `BufferedWriter` + `FileWriter` dalam try-with-resources |

## Validasi dan Penanganan Error

| Kondisi | Penanganan |
|---|---|
| Nilai < 0 atau > 100 | `NilaiInvalidException` |
| Nilai bukan angka | `NumberFormatException`, input diulang |
| NIM kosong | `assert` (dengan `-ea`) dan `IllegalArgumentException` sebagai cadangan |
| NIM mengandung huruf/simbol | `IllegalArgumentException` (dicek dengan `Character.isDigit()`) |
| Gagal menulis file | `IOException` ditangkap dan pesan error ditampilkan |

## Contoh Output

**Konsol / `laporan_nilai.txt`:**

```
=== LAPORAN NILAI MAHASISWA ===
No   Nama                 NIM          Nilai    Grade  Status
1    BUDI SANTI           12345        80.00    B      LULUS
2    ANI                  999          60.00    C      TIDAK LULUS
3    CIKA                 777          95.00    A      LULUS
-----------------------------------
STATISTIK KELAS
Jumlah mahasiswa   : 3
Rata-rata nilai    : 78.33
Jumlah lulus       : 2
Jumlah tidak lulus : 1
```
