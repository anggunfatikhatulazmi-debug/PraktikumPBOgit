/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.kasir;

/**
 *
 * @author anggu
 */
public class Pesanan {
    private double subtotal;

    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Diskon 10% jika subtotal > 50.000
    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal - (subtotal * 0.10);
        }
        return subtotal;
    }

    public void reset() {
        subtotal = 0;
    }
}