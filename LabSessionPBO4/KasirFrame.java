/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.kasir;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author anggu
 */
public class KasirFrame extends JFrame{
    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JLabel lblStatus;
    private Pesanan pesanan = new Pesanan();

    // Format angka ala Indonesia: 63000 -> 63.000
    private final DecimalFormat rupiah =
            new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("id", "ID")));

    public KasirFrame() {
        setTitle("Kasir Kantin UNS");
        setSize(400, 450);
        setLayout(null);

        // ----- Tahap 3: komponen -----
        JLabel lblJudul = new JLabel("Kasir Kantin UNS");
        lblJudul.setBounds(20, 10, 300, 25);

        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 45, 80, 25);
        txtMenu = new JTextField();
        txtMenu.setBounds(110, 45, 250, 25);

        JLabel lblHarga = new JLabel("Harga     :");
        lblHarga.setBounds(20, 80, 80, 25);
        txtHarga = new JTextField();
        txtHarga.setBounds(110, 80, 250, 25);

        JLabel lblJumlah = new JLabel("Jumlah    :");
        lblJumlah.setBounds(20, 115, 80, 25);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(110, 115, 250, 25);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(40, 150, 140, 28);
        JButton btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(200, 150, 140, 28);

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaStruk);
        scroll.setBounds(20, 190, 340, 130);

        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setBounds(20, 328, 340, 25);

        // ----- Tahap 4: tombol cetak + label status -----
        JButton btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 358, 140, 28);
        lblStatus = new JLabel("Status : -");
        lblStatus.setBounds(20, 392, 340, 25);

        add(lblJudul);
        add(lblMenu);   add(txtMenu);
        add(lblHarga);  add(txtHarga);
        add(lblJumlah); add(txtJumlah);
        add(btnTambah); add(btnHitung);
        add(scroll);
        add(lblTotal);
        add(btnCetak);
        add(lblStatus);

        // ----- Tombol Tambah (dengan validasi) -----
        btnTambah.addActionListener(e -> {
            String menu = txtMenu.getText().trim();
            String hargaStr = txtHarga.getText().trim();
            String jumlahStr = txtJumlah.getText().trim();

            if (menu.isEmpty() || hargaStr.isEmpty() || jumlahStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }
            try {
                double harga = Double.parseDouble(hargaStr);
                int jumlah = Integer.parseInt(jumlahStr);
                if (harga < 0 || jumlah <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Harga tidak boleh negatif dan jumlah harus lebih dari 0!");
                    return;
                }

                pesanan.tambahItem(harga, jumlah);
                areaStruk.append(menu + "  " + rupiah.format(harga) + " x " + jumlah
                        + " = " + rupiah.format(harga * jumlah) + "\n");

                txtMenu.setText("");
                txtHarga.setText("");
                txtJumlah.setText("");
                txtMenu.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan jumlah harus berupa angka!");
            }
        });

        // ----- Tombol Hitung Total -----
        btnHitung.addActionListener(e ->
                lblTotal.setText("Total Bayar : Rp " + rupiah.format(pesanan.hitungTotal())));

        // ----- Tombol Cetak Struk: tunggu 2 detik di thread baru -----
        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                setStatus("Mencetak struk...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                setStatus("Struk selesai");
            }).start();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Update komponen Swing dilakukan lewat Event Dispatch Thread
    private void setStatus(String teks) {
        SwingUtilities.invokeLater(() -> lblStatus.setText("Status : " + teks));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KasirFrame::new);
    }
}