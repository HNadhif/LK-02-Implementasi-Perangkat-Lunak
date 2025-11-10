package src.objek;

public class item {
    // Atribut
    private String kode;
    private String nama;
    private double harga;
    private int quantity;

    // Relasi 0.. to 1 dengan Transaksi
    Transaksi transaksi;

    // Constructor
    public item(String kode, String nama, double harga, int quantity, Transaksi transaksi) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.quantity = quantity;
        this.transaksi = transaksi;
    }

    // Main Method
    public double getSubtotal() {
        return harga * quantity;
    }



    // Setter dan Getter
    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
