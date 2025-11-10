package src.objek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Transaksi {
    // Atribut
    private String id;
    private String date;
    private String kasir;
    private ArrayList<item> items; // Relasi 1 to 0.. dengan item
    private double total;
    private double pajak;
    private double diskon;
    private String metodeBayar;

    // Relasi 1 to 1 dengan Struk
    private struk struk;

    // Constructor
    public Transaksi(String kasir) {
        this.id = "TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.date = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.kasir = kasir;
        this.items = new ArrayList<>();
        this.pajak = 0.1;
        this.diskon = 0;
        this.struk = null;
    }


    // Method
    public double hitungTotal() {
        double subtotal = 0;
        for (item item : items) {
            subtotal += item.getSubtotal();
        }

        double totalSebelumPajak = subtotal - diskon;
        double totalPajak = totalSebelumPajak * pajak;
        this.total = totalSebelumPajak + totalPajak;

        return this.total;
    }

    public void tambahItem(item item) {
        // Pengecekan apakah item dengan kode yang sama sudah ada
        for (item existingItem : items) {
            if (existingItem.getKode().equals(item.getKode())) {
                // Update Quantity jika sudah ada
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                hitungTotal();
                return;
            }
        }
        // Menambahkan jika belum ada
        items.add(item);
        hitungTotal();
    }

    public boolean hapusItem(String kodeItem) {
        boolean removed = items.removeIf(item -> item.getKode().equals(kodeItem));
        if (removed) {
            hitungTotal();
        }
        return removed;
    }

    public boolean editItem(String kodeItem, int quantityBaru) {
        for (item item : items) {
            if (item.getKode().equals(kodeItem)) {
                if (quantityBaru <= 0) {
                    hapusItem(kodeItem);
                } else {
                    item.setQuantity(quantityBaru);
                }
                hitungTotal();
                return true;
            }
        }
        return false;
    }

    public struk cetakStruk() {
        if (this.struk != null) {
            System.out.println("⚠️  Struk sudah dibuat sebelumnya!");
            return this.struk; // ✅ Kembalikan struk yang sudah ada
        }

        if (items.isEmpty()) {
            System.out.println("❌ Tidak bisa buat struk: belum ada item!");
            return null;
        }

        double subtotal = 0;
        for (item i : items) {
            subtotal += i.getHarga() * i.getQuantity();
        }

        double totalPajak = (subtotal - diskon) * pajak;
        this.total = (subtotal - diskon) + totalPajak;

        // Buat struk baru
        this.struk = new struk(
                this.id,
                this.kasir,
                new ArrayList<>(this.items),
                subtotal,
                totalPajak,
                this.diskon,
                this.total,
                this.metodeBayar,
                this
        );

        System.out.println("✅ Struk berhasil dibuat untuk transaksi: " + this.id);
        return this.struk;
    }

    // Getter dan setter
    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKasir(String kasir) {
        this.kasir = kasir;
    }

    public void setItems(ArrayList<item> items) {
        this.items = items;
        hitungTotal(); // Otomatis hitung ulang total
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setPajak(double pajak) {
        this.pajak = pajak;
        hitungTotal(); // Otomatis hitung ulang total
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
        hitungTotal();
    }

    public void setMetodeBayar(String metodeBayar) {
        this.metodeBayar = metodeBayar;
    }

    public void setStruk(struk struk) {
        this.struk = struk;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getKasir() {
        return kasir;
    }

    public ArrayList<item> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public double getPajak() {
        return pajak;
    }

    public double getDiskon() {
        return diskon;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public struk getStruk() {
        return struk;
    }
}
