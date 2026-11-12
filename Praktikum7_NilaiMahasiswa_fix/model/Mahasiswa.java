/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anggu
 */
public class Mahasiswa {

    // Konstanta standar kelulusan
    public static final double FINAL_PASSING_GRADE = 70.0;

    private String nama;   // reference type
    private String nim;    // reference type
    private double nilai;  // primitive type

    public Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException {
        // Manipulasi String: rapikan nama dan NIM
        this.nama = nama.trim().toUpperCase();
        this.nim = nim.trim();

        // Assertion: NIM tidak boleh kosong (aktifkan dengan java -ea)
        assert !this.nim.isEmpty() : "NIM tidak boleh kosong!";

        // Validasi tetap dilakukan walaupun assertion dinonaktifkan
        if (this.nim.isEmpty()) {
            throw new IllegalArgumentException("NIM tidak boleh kosong!");
        }

        // Validasi NIM hanya berisi angka menggunakan Character.isDigit()
        for (int i = 0; i < this.nim.length(); i++) {
            if (!Character.isDigit(this.nim.charAt(i))) {
                throw new IllegalArgumentException(
                        "NIM hanya boleh berisi angka! Ditemukan karakter '"
                                + this.nim.charAt(i) + "'.");
            }
        }

        // Validasi nilai -> Custom Exception
        if (nilai < 0 || nilai > 100) {
            throw new NilaiInvalidException(
                    "Nilai " + nilai + " tidak valid! Harus di antara 0 sampai 100.");
        }
        this.nilai = nilai;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public double getNilai() {
        return nilai;
    }

    /** Memetakan nilai ke grade huruf menggunakan If-Else. */
    public String getGrade() {
        if (nilai >= 85) {
            return "A";
        } else if (nilai >= 70) {
            return "B";
        } else if (nilai >= 55) {
            return "C";
        } else {
            return "D";
        }
    }

    /** Status kelulusan berdasarkan FINAL_PASSING_GRADE. */
    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }

    public String getStatus() {
        return isLulus() ? "LULUS" : "TIDAK LULUS";
    }
}

