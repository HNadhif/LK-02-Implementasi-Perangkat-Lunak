package src.objek;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class struk {
    // Atribut
    private String noStruk;
    private Date tanggal;
    private String kasir;
    private ArrayList<item> items; // Relasi 1 to 0.. dengan item
    private double subtotal;
    private double pajak;
    private double diskon;
    private double total;
    private String metodeBayar;

    // RELASI 1 to 1 terhadap Transaksi
    private Transaksi transaksi;

    private static final int LINE_LENGTH = 40;
    private static final String TOKO_NAME = "TOKO SEDERHANA";
    private static final String TOKO_ALAMAT = "Jl. Merdeka No. 123";
    private static final String TOKO_TELP = "Telp: (021) 123456";

    // Constructor
    public struk(String noStruk, String kasir, ArrayList<item> items,
                 double subtotal, double pajak, double diskon,
                 double total, String metodeBayar, Transaksi transaksi) {
        this.noStruk = noStruk;
        this.tanggal = new Date();
        this.kasir = kasir;
        this.items = items;
        this.subtotal = subtotal;
        this.pajak = pajak;
        this.diskon = diskon;
        this.total = total;
        this.metodeBayar = metodeBayar;
        this.transaksi = transaksi;
    }

    // Method
    public String generateStruk() {
        StringBuilder strukBuilder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // HEADER
        strukBuilder.append("=".repeat(50)).append("\n");
        strukBuilder.append(centerText("TOKO SEDERHANA")).append("\n");
        strukBuilder.append(centerText("Jl. Merdeka No. 123")).append("\n");
        strukBuilder.append(centerText("Telp: (021) 123456")).append("\n");
        strukBuilder.append("=".repeat(50)).append("\n");

        // INFO TRANSAKSI
        strukBuilder.append(String.format("%-12s: %s\n", "No Struk", noStruk));
        strukBuilder.append(String.format("%-12s: %s\n", "Tanggal", sdf.format(tanggal)));
        strukBuilder.append(String.format("%-12s: %s\n", "Kasir", kasir));
        strukBuilder.append("-".repeat(50)).append("\n");

        // HEADER ITEM
        strukBuilder.append(String.format("%-3s %-15s %-10s %-4s %-10s\n",
                "No", "Item", "Harga", "Qty", "Subtotal"));
        strukBuilder.append("-".repeat(50)).append("\n");

        // DETAIL ITEMS
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                item item = items.get(i);
                String namaItem = truncateText(item.getNama(), 15);

                strukBuilder.append(String.format("%-3d %-15s %-10s %-4d %-10s\n",
                        (i + 1),
                        namaItem,
                        formatRupiah(item.getHarga()),
                        item.getQuantity(),
                        formatRupiah(item.getSubtotal())
                ));
            }
        }

        strukBuilder.append("-".repeat(50)).append("\n");

        // RINGKASAN PEMBAYARAN
        strukBuilder.append(String.format("%-20s: %13s\n", "Subtotal", formatRupiah(subtotal)));
        strukBuilder.append(String.format("%-20s: %13s\n", "Diskon", formatRupiah(diskon)));
        strukBuilder.append(String.format("%-20s: %13s\n", "Pajak (10%)", formatRupiah(pajak)));
        strukBuilder.append("=".repeat(50)).append("\n");
        strukBuilder.append(String.format("%-20s: %13s\n", "TOTAL", formatRupiah(total)));
        strukBuilder.append("=".repeat(50)).append("\n");

        // INFO PEMBAYARAN
        strukBuilder.append(String.format("%-15s: %s\n", "Metode Bayar", metodeBayar));
        strukBuilder.append(String.format("%-15s: %s\n", "Status", "LUNAS"));
        strukBuilder.append("-".repeat(50)).append("\n");

        // FOOTER
        strukBuilder.append(centerText("TERIMA KASIH")).append("\n");
        strukBuilder.append(centerText("ATAS KUNJUNGAN ANDA")).append("\n");
        strukBuilder.append("=".repeat(50)).append("\n");

        return strukBuilder.toString();
    }

    public void printStruk() {
        String strukContent = generateStruk();
        System.out.println(strukContent);

        // Simulasi print ke printer (dalam real implementation, ini akan connect ke printer fisik)
        System.out.println("🖨️  Struk berhasil dicetak ke printer!");
    }

    public void previewStruk() {
        System.out.println("=== PREVIEW STRUK ===");
        String strukContent = generateStruk();
        System.out.println(strukContent);
    }

    // Method pembantu
    private String createLine(String character) {
        return character.repeat(LINE_LENGTH) + "\n";
    }

    private String centerText(String text) {
        int totalWidth = 40;
        int textLength = text.length();
        int padding = (totalWidth - textLength) / 2;
        if (padding < 0) padding = 0;
        return " ".repeat(padding) + text;
    }

    private String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    private String formatRupiah(double amount) {
        return "Rp" + String.format("%,.0f", amount).replace(',', '.');
    }


    // Getter dan Setter
    public String getNoStruk() {
        return noStruk;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getKasir() {
        return kasir;
    }

    public ArrayList<item> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getPajak() {
        return pajak;
    }

    public double getDiskon() {
        return diskon;
    }

    public double getTotal() {
        return total;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }
}