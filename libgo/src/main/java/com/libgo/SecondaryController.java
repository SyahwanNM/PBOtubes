package com.libgo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SecondaryController {
    @FXML
    private TextField judulBukuField;
    @FXML
    private TextField pengarangField;
    @FXML
    private TextField penerbitField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField subjekField;
    @FXML
    private TextField stokField;
    @FXML
    private TextField hargaField;

    @FXML
    private void handleSave() {
        String judulBuku = judulBukuField.getText().trim();
        String pengarang = pengarangField.getText().trim();
        String penerbit = penerbitField.getText().trim();
        String isbnText = isbnField.getText().trim();
        String subjek = subjekField.getText().trim();
        String stokText = stokField.getText().trim();
        String hargaText = hargaField.getText().trim();

        // Validasi input kosong
        if (judulBuku.isEmpty() || subjek.isEmpty() 
            || stokText.isEmpty() || hargaText.isEmpty()) {
            showAlert("Semua field harus diisi.", Alert.AlertType.WARNING);
            return;
        }

        // Validasi agar subjek hanya berisi huruf (opsional: spasi)
        if (!subjek.matches("[a-zA-Z\\s]+")) {
            showAlert("Subjek hanya boleh mengandung huruf.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int stok = Integer.parseInt(stokText);
            double harga = Double.parseDouble(hargaText);
            long isbn = Long.parseLong(isbnText);

            if (stok <= 0 || harga <= 0) {
                showAlert("stok dan harga harus bernilai positif.", Alert.AlertType.WARNING);
                return;
            }

            // Validasi ISBN sebagai angka 13 digit
            if (!isbnText.matches("\\d{13}")) {
                showAlert("ISBN harus terdiri dari 13 angka.", Alert.AlertType.WARNING);
                return;
            }
            
            // Tambah Buku baru
            Database.addBuku(judulBuku, pengarang, penerbit, isbn, subjek, stok, harga);
            App.setRoot("primary"); // Kembali ke halaman utama

        } catch (NumberFormatException e) {
            showAlert("Stok/Harga/ISBN harus berupa angka.", Alert.AlertType.ERROR);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToSecondary() throws IOException {
        System.out.println("Beralih ke form Tambah Buku...");
        App.setRoot("secondary");
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}