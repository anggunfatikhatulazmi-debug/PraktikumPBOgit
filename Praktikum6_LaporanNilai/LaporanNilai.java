/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author anggu
 */
public class LaporanNilai {

    static final String NAMA_FILE = "laporan_nilai.txt";
    static final double NILAI_LULUS = 70.0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("      PENGELOLAAN NILAI SISWA");
        System.out.println("======================================");

        int jumlahSiswa = bacaJumlahSiswa(input);

        // Langkah 1: input dari konsol dan tulis ke file
        boolean berhasilMenulis = tulisLaporan(input, jumlahSiswa);

        // Langkah 2: baca kembali file dan tampilkan ke konsol
        if (berhasilMenulis) {
            bacaLaporan();
        }

        input.close();
    }

    // Membaca jumlah siswa, diulang sampai input valid (bilangan bulat > 0)
    static int bacaJumlahSiswa(Scanner input) {
        while (true) {
            System.out.print("Masukkan jumlah siswa: ");
            String teks = input.nextLine().trim();
            try {
                int jumlah = Integer.parseInt(teks);
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("Jumlah siswa harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat.");
            }
        }
    }

    // Membaca nama siswa, tidak boleh kosong
    static String bacaNama(Scanner input) {
        while (true) {
            System.out.print("Nama  : ");
            String nama = input.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("Nama tidak boleh kosong.");
        }
    }

    // Membaca nilai (0 - 100). Koma desimal (85,5) juga diterima.
    static double bacaNilai(Scanner input) {
        while (true) {
            System.out.print("Nilai : ");
            String teks = input.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(teks);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("Nilai harus antara 0 dan 100.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    // Menentukan status kelulusan
    static String tentukanStatus(double nilai) {
        return (nilai >= NILAI_LULUS) ? "Lulus" : "Tidak Lulus";
    }

    // Membaca data siswa dan menulisnya ke file (try-with-resources)
    static boolean tulisLaporan(Scanner input, int jumlahSiswa) {
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {

            writer.write                  ("LAPORAN NILAI SISWA");
            writer.newLine();
            writer.write("=======================================================");
            writer.newLine();
            writer.write(String.format("%-4s %-20s %-8s %-12s", "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write("-------------------------------------------------------");
            writer.newLine();

            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println("\nData siswa ke-" + i);
                String nama = bacaNama(input);
                double nilai = bacaNilai(input);
                String status = tentukanStatus(nilai);

                totalNilai += nilai;
                if (status.equals("Lulus")) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }

                writer.write(String.format(java.util.Locale.US,
                        "%-4d %-20s %-8.2f %-12s", i, nama, nilai, status));
                writer.newLine();
            }

            double rataRata = totalNilai / jumlahSiswa;

            writer.write("=======================================================");
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write(String.format(java.util.Locale.US,
                    "Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah siswa lulus    : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();

            System.out.println("\nData berhasil ditulis ke file " + NAMA_FILE);
            return true;

        } catch (IOException e) {
            System.out.println("Gagal menulis file: " + e.getMessage());
            return false;
        }
    }

    // Membaca isi file baris demi baris sampai null lalu menampilkannya
    static void bacaLaporan() {
        System.out.println("\n======== ISI FILE " + NAMA_FILE + " ========");

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }

        System.out.println("======================================");
    }
}
