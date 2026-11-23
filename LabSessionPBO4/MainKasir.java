/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.kasir;

/**
 *
 * @author anggu
 */
public class MainKasir {
    public static void main(String[] args) {
        // ===== TAHAP 1: uji class Pesanan =====
        System.out.println("=== TAHAP 1: Uji Pesanan ===");
        Pesanan pesanan = new Pesanan();

        pesanan.tambahItem(15000, 2); // Nasi Goreng
        pesanan.tambahItem(5000, 3);  // Es Teh
        System.out.println("Setelah 2 menu -> subtotal: " + pesanan.getSubtotal()
                + ", total: " + pesanan.hitungTotal());   // 45000 / 45000

        pesanan.tambahItem(25000, 1); // Ayam Bakar
        System.out.println("Setelah 3 menu -> subtotal: " + pesanan.getSubtotal()
                + ", total: " + pesanan.hitungTotal());   // 70000 / 63000

        pesanan.reset();
        System.out.println("Setelah reset  -> subtotal: " + pesanan.getSubtotal());

        // ===== TAHAP 2: dua kasir sebagai thread =====
        System.out.println("\n=== TAHAP 2: Dua Kasir ===");
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");

        dina.start();
        eko.start();

        try {
            dina.join();
            eko.join();
        } catch (InterruptedException e) {
            System.out.println("Main terinterupsi");
        }

        System.out.println("Semua pelanggan selesai dilayani");

        // ===== TAHAP 3 & 4: GUI (opsional dari sini) =====
        // Jalankan GUI: java lab.kasir.KasirFrame
    }
}