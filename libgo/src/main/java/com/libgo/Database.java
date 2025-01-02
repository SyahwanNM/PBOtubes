package com.libgo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DATABASE_URL = "jdbc:mariadb://127.0.0.1:3307/libgo_db";
    private static final String DATABASE_USER = "root"; 
    private static final String DATABASE_PASSWORD = "lordzoro234"; 

    private static Connection connection;

    // Inisialisasi koneksi ke database
    static {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.err.println("Gagal menghubungkan ke database.");
            e.printStackTrace();
            System.exit(1); // Keluar jika koneksi gagal
        }
    }

    // Metode untuk mendapatkan semua Buku
    public static List<Buku> getAllBuku() throws SQLException {
        List<Buku> BukuList = new ArrayList<>();
        String sql = "SELECT * FROM Buku";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String judulBuku = rs.getString("judul_Buku");
            String pengarang = rs.getString("pengarang");
            String penerbit = rs.getString("penerbit");
            long isbn = rs.getLong("isbn");
            String subjek = rs.getString("subjek");
            int stok = rs.getInt("stok");
            double harga = rs.getDouble("harga");

            Buku Buku = new Buku(id, judulBuku, pengarang, penerbit, isbn, subjek, stok, harga);
            BukuList.add(Buku);
        }

        return BukuList;
    }

    // Tambah Buku baru
    public static void addBuku(String judulBuku, String pengarang, String penerbit, long isbn, String subjek, int stok, double harga) throws SQLException {
        String sql = "INSERT INTO Buku (judul_Buku, pengarang, penerbit, isbn, subjek, stok, harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, judulBuku);
        stmt.setString(2, pengarang);
        stmt.setString(3, penerbit);
        stmt.setLong(4, isbn);
        stmt.setString(5, subjek);
        stmt.setInt(6, stok);
        stmt.setDouble(7, harga);
        stmt.executeUpdate();
    }

    // Update Buku yang sudah ada
    public static void updateBuku(int id, String judulBuku, String pengarang, String penerbit, long isbn, String subjek, int stok, double harga) throws SQLException {
        String sql = "UPDATE Buku SET judul_Buku = ?, pengarang = ?, penerbit = ?, isbn = ?, subjek = ?, stok = ?, harga = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, judulBuku);
        stmt.setString(2, pengarang);
        stmt.setString(3, penerbit);
        stmt.setLong(4, isbn);
        stmt.setString(5, subjek);
        stmt.setInt(6, stok);
        stmt.setDouble(7, harga);
        stmt.setInt(8, id);
        stmt.executeUpdate();
    }

    // Hapus Buku berdasarkan ID
    public static void deleteBuku(int id) throws SQLException {
        String sql = "DELETE FROM Buku WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    // Tutup koneksi ke database (opsional, untuk menutup koneksi saat aplikasi selesai)
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Koneksi ke database ditutup.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
