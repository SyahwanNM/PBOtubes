package com.libgo;

public class Buku {
    private int id;
    private String judulBuku;
    private String pengarang;
    private String penerbit;
    private long isbn;
    private String subjek;
    private int stok;
    private double harga;

    // Constructor
    public Buku(int id, String judulBuku, String pengarang, String penerbit, long isbn, String subjek, int stok, double harga) {
        this.id = id;
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.isbn = isbn;
        this.subjek = subjek;
        this.stok = stok;
        this.harga = harga;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    // Tambahkan properti hargaString
    public String getHargaString() {
        return "Rp " + String.format("%,d", (int) harga); // Format harga dengan Rp dan pemisah ribuan
    }

    @Override
    public String toString() {
        return "Buku{" +
                "id=" + id +
                ", judulBuku='" + judulBuku + '\'' +
                ", pengarang='" + pengarang + '\'' +
                ", penerbit='" + penerbit + '\'' +
                ", isbn=" + isbn +
                ", subjek='" + subjek + '\'' +
                ", stok=" + stok +
                ", harga=" + harga +
                '}';
    }
}
