/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import model.Mahasiswa;
import model.NilaiInvalidException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author anggu
 */

public class Main {

    private static final String NAMA_FILE = "laporan_nilai.txt";

    public static void main(String[] args) {
        ArrayList<Mahasiswa> daftar = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PROGRAM PENGELOLAAN NILAI MAHASISWA ===");
        int jumlah = bacaJumlah(scanner);

        // Input data beberapa mahasiswa
        int ke = 1;
        while (ke <= jumlah) {
            System.out.println("\n--- Data Mahasiswa ke-" + ke + " ---");
            try {
                System.out.print("Nama  : ");
                String nama = scanner.nextLine();
                System.out.print("NIM   : ");
                String nim = scanner.nextLine();
                System.out.print("Nilai : ");
                double nilai = Double.parseDouble(scanner.nextLine().trim());

                Mahasiswa m = new Mahasiswa(nama, nim, nilai);
                daftar.add(m);
                System.out.println("Data berhasil ditambahkan.");
                ke++;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Nilai harus berupa angka! Ulangi input.");
            } catch (NilaiInvalidException e) {
                System.out.println("[ERROR] " + e.getMessage() + " Ulangi input.");
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage() + " Ulangi input.");
            } catch (AssertionError e) {
                System.out.println("[ASSERTION] " + e.getMessage() + " Ulangi input.");
            }
        }

        if (daftar.isEmpty()) {
            System.out.println("Tidak ada data untuk diproses.");
            scanner.close();
            return;
        }

        // Hitung statistik dengan for-each
        double total = 0;
        int lulus = 0;
        int tidakLulus = 0;
        for (Mahasiswa m : daftar) {
            total += m.getNilai();
            if (m.isLulus()) {
                lulus++;
            } else {
                tidakLulus++;
            }
        }
        double rataRata = total / daftar.size();

        tampilkanKeKonsol(daftar, rataRata, lulus, tidakLulus);
        simpanKeFile(daftar, rataRata, lulus, tidakLulus);

        scanner.close();
    }

    private static int bacaJumlah(Scanner scanner) {
        int jumlah = 0;
        while (jumlah <= 0) {
            System.out.print("Masukkan jumlah mahasiswa: ");
            try {
                jumlah = Integer.parseInt(scanner.nextLine().trim());
                if (jumlah <= 0) {
                    System.out.println("[ERROR] Jumlah harus lebih dari 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan bilangan bulat!");
            }
        }
        return jumlah;
    }

    private static void tampilkanKeKonsol(ArrayList<Mahasiswa> daftar,
                                          double rataRata, int lulus, int tidakLulus) {
        System.out.println("\n=== REKAPITULASI NILAI MAHASISWA ===");
        System.out.printf("%-4s %-20s %-12s %-8s %-6s %-12s%n",
                "No", "Nama", "NIM", "Nilai", "Grade", "Status");
        int no = 1;
        for (Mahasiswa m : daftar) {
            System.out.printf("%-4d %-20s %-12s %-8.2f %-6s %-12s%n",
                    no++, m.getNama(), m.getNim(), m.getNilai(), m.getGrade(), m.getStatus());
        }
        System.out.println("-----------------------------------");
        System.out.println("Jumlah mahasiswa : " + daftar.size());
        System.out.printf("Rata-rata nilai  : %.2f%n", rataRata);
        System.out.println("Jumlah lulus     : " + lulus);
        System.out.println("Jumlah tidak lulus: " + tidakLulus);
    }

    /** Menyimpan laporan menggunakan try-with-resources (BufferedWriter + FileWriter). */
    private static void simpanKeFile(ArrayList<Mahasiswa> daftar,
                                     double rataRata, int lulus, int tidakLulus) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write("=== LAPORAN NILAI MAHASISWA ===");
            writer.newLine();
            writer.write(String.format("%-4s %-20s %-12s %-8s %-6s %-12s",
                    "No", "Nama", "NIM", "Nilai", "Grade", "Status"));
            writer.newLine();

            int no = 1;
            for (Mahasiswa m : daftar) {
                writer.write(String.format("%-4d %-20s %-12s %-8.2f %-6s %-12s",
                        no++, m.getNama(), m.getNim(), m.getNilai(),
                        m.getGrade(), m.getStatus()));
                writer.newLine();
            }

            writer.write("-----------------------------------");
            writer.newLine();
            writer.write("STATISTIK KELAS");
            writer.newLine();
            writer.write("Jumlah mahasiswa   : " + daftar.size());
            writer.newLine();
            writer.write(String.format("Rata-rata nilai    : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah lulus       : " + lulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus : " + tidakLulus);
            writer.newLine();

            System.out.println("\nLaporan berhasil disimpan ke file " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menulis file: " + e.getMessage());
        }
    }
}

