package com.libgo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class PrimaryController {
    @FXML
    private TableView<Buku> tableView; // Mengacu pada ID fx:id="tableView"
    @FXML
    private TableColumn<Buku, String> judulBukuColumn; // fx:id="judulBukuColumn"
    @FXML
    private TableColumn<Buku, String> pengarangColumn; // fx:id="pengarangColumn"
    @FXML
    private TableColumn<Buku, String> penerbitColumn; // fx:id="penerbitColumn"
    @FXML
    private TableColumn<Buku, Integer> isbnColumn; // fx:id="isbnColumn"
    @FXML
    private TableColumn<Buku, String> subjekColumn; // fx:id="subjekColumn"
    @FXML
    private TableColumn<Buku, Integer> stokColumn; // fx:id="stokColumn"
    @FXML
    private TableColumn<Buku, String> hargaColumn; // Ubah tipe data menjadi String

    @FXML
    private Button primarybutton; // Mengacu pada tombol Tambah Buku

    private ObservableList<Buku> BukuData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        System.out.println("Inisialisasi PrimaryController...");

        // Menghubungkan kolom dengan properti Buku
        judulBukuColumn.setCellValueFactory(new PropertyValueFactory<>("judulBuku"));
        pengarangColumn.setCellValueFactory(new PropertyValueFactory<>("pengarang"));
        penerbitColumn.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        subjekColumn.setCellValueFactory(new PropertyValueFactory<>("subjek"));
        stokColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
        
        // Ubah pengaturan untuk kolom harga
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("hargaString")); // Menggunakan properti baru

        // Muat data ke dalam tabel
        loadBukuData();

        // Tambahkan pewarnaan baris pada TableView
        addRowColoring();
    }

    private void loadBukuData() {
        System.out.println("Memuat data Buku dari database...");
        try {
            BukuData.setAll(Database.getAllBuku()); // Ambil data dari database
            tableView.setItems(BukuData); // Set data ke tabel
            System.out.println("Data Buku berhasil dimuat.");
        } catch (SQLException e) {
            showError("Gagal memuat data Buku dari database.");
            e.printStackTrace();
        }
    }

    private void addRowColoring() {
        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Buku item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle(""); // Reset style untuk baris kosong
                } else {
                    // Pola pewarnaan zebra (genap dan ganjil)
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #f2f2f2;"); // Baris genap abu-abu muda
                    } else {
                        setStyle("-fx-background-color: #ddd;"); // Baris ganjil putih
                    }
                }
            }
        });
    }

    @FXML
    private void switchToSecondary() throws IOException {
        System.out.println("Beralih ke form Tambah Buku...");
        App.setRoot ("secondary"); // Berpindah ke form Tambah Buku
    }

    @FXML
    private void handleEdit() throws IOException {
        // Ambil Buku yang dipilih dari tabel
        Buku selectedBuku = tableView.getSelectionModel().getSelectedItem();

        if (selectedBuku != null) {
            System.out.println("Mengedit Buku: " + selectedBuku);

            // Set Buku yang dipilih ke SecondaryController
            EditController.setSelectedBuku(selectedBuku);

            // Berpindah ke halaman secondary (form edit)
            App.setRoot("edit");
        } else {
            // Jika tidak ada Buku yang dipilih, tampilkan peringatan
            showWarning("Pilih Buku yang ingin diedit.");
        }
    }

    @FXML
    private void handleDelete() {
        // Ambil Buku yang dipilih dari tabel
        Buku selectedBuku = tableView.getSelectionModel().getSelectedItem();

        if (selectedBuku != null) {
            // Tampilkan konfirmasi penghapusan
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Konfirmasi Penghapusan");
            confirmationAlert.setHeaderText("Hapus Buku");
            confirmationAlert.setContentText("Apakah Anda yakin ingin menghapus Buku ini?");

            // Jika pengguna menekan OK
            if (confirmationAlert.showAndWait().get() == ButtonType.OK) {
                try {
                    // Hapus Buku dari database
                    Database.deleteBuku(selectedBuku.getId());

                    // Hapus Buku dari tabel
                    tableView.getItems().remove(selectedBuku);

                    // Tampilkan notifikasi
                    showInfo("Buku berhasil dihapus.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    showError("Terjadi kesalahan saat menghapus Buku.");
                }
            }
        } else {
            // Jika tidak ada Buku yang dipilih, tampilkan peringatan
            showWarning("Pilih Buku yang ingin dihapus.");
        }
    }

    // Fungsi utilitas untuk menampilkan informasi
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fungsi utilitas untuk menampilkan peringatan
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fungsi utilitas untuk menampilkan error
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}