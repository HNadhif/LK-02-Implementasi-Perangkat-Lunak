package src;
import src.objek.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("       SISTEM KASIR TOKO SEDERHANA       ");
        System.out.println("==========================================");

        // Pre-Condition 1: Buat transaksi dengan kasir
        System.out.print("Masukkan nama kasir : ");
        String namaKasir = scanner.nextLine();
        Transaksi transaksi = new Transaksi(namaKasir);

        System.out.println("\nINPUT ITEM BELANJAAN");
        System.out.println("Ketik 'SELESAI' untuk mengakhiri input item\n");

        // Alur Normal 1: Input item belanja
        int nomorItem = 1;
        while (true) {
            System.out.println("--- Item ke-" + nomorItem + " ---");

            // Input nama item
            System.out.print("Nama item  : ");
            String namaItem = scanner.nextLine().trim();

            if (namaItem.equalsIgnoreCase("SELESAI")) {
                if (transaksi.getItems().isEmpty()) {
                    System.out.println("⚠️  Minimal harus ada 1 item! Lanjutkan input.");
                    continue;
                }
                break;
            }

            if (namaItem.isEmpty()) {
                System.out.println("⚠️  Nama item tidak boleh kosong!");
                continue;
            }

            // Input kode item
            System.out.print("Kode item  : ");
            String kodeItem = scanner.nextLine().trim();
            if (kodeItem.isEmpty()) {
                kodeItem = "ITM" + nomorItem;
            }

            // Input harga
            double harga = 0;
            while (true) {
                try {
                    System.out.print("Harga      : Rp");
                    harga = scanner.nextDouble();
                    scanner.nextLine();
                    if (harga <= 0) {
                        System.out.println("⚠️  Harga harus lebih dari 0!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("⚠️  Input harga tidak valid! Harus angka.");
                    scanner.nextLine();
                }
            }

            // Input quantity
            int quantity = 0;
            while (true) {
                try {
                    System.out.print("Quantity   : ");
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity <= 0) {
                        System.out.println("⚠️  Quantity harus lebih dari 0!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("⚠️  Input quantity tidak valid! Harus angka.");
                    scanner.nextLine();
                }
            }

            // Tambah item ke transaksi
            item itemBaru = new item(kodeItem, namaItem, harga, quantity, transaksi);
            transaksi.tambahItem(itemBaru);

            System.out.println("✅ " + itemBaru.getNama() + " berhasil ditambahkan");
            System.out.println("   Subtotal: Rp" + itemBaru.getSubtotal() + "\n");
            nomorItem++;
        }

        // Fitur: Edit transaksi item
        System.out.println("\nEDIT ITEM (Opsional)");
        System.out.print("Ingin edit item? (Y/N): ");
        String editKonfirmasi = scanner.nextLine();

        if (editKonfirmasi.equalsIgnoreCase("Y")) {
            boolean editSelesai = false;
            while (!editSelesai) {
                System.out.println("\nPilihan Edit:");
                System.out.println("1. Edit quantity item");
                System.out.println("2. Hapus item");
                System.out.println("3. Selesai edit");
                System.out.print("Pilih (1-3): ");
                String pilihanEdit = scanner.nextLine();

                switch (pilihanEdit) {
                    case "1":
                        System.out.print("Masukkan kode item yang akan diedit: ");
                        String kodeEdit = scanner.nextLine();
                        System.out.print("Quantity baru: ");
                        int qtyBaru = scanner.nextInt();
                        scanner.nextLine();
                        if (transaksi.editItem(kodeEdit, qtyBaru)) {
                            System.out.println("✅ Quantity berhasil diupdate");
                        } else {
                            System.out.println("❌ Item tidak ditemukan!");
                        }
                        break;
                    case "2":
                        System.out.print("Masukkan kode item yang akan dihapus: ");
                        String kodeHapus = scanner.nextLine();
                        if (transaksi.hapusItem(kodeHapus)) {
                            System.out.println("✅ Item berhasil dihapus");
                        } else {
                            System.out.println("❌ Item tidak ditemukan!");
                        }
                        break;
                    case "3":
                        editSelesai = true;
                        break;
                    default:
                        System.out.println("⚠️  Pilihan tidak valid!");
                }
            }
        }

        // Fitur: Kelola metode pembayaran
        System.out.println("\n💳 METODE PEMBAYARAN");
        System.out.println("1. TUNAI");
        System.out.println("2. QRIS");
        System.out.println("3. DEBIT");
        System.out.println("4. KREDIT");

        String metodeBayar = "";
        while (metodeBayar.isEmpty()) {
            System.out.print("Pilih metode (1-4): ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1": metodeBayar = "TUNAI"; break;
                case "2": metodeBayar = "QRIS"; break;
                case "3": metodeBayar = "DEBIT"; break;
                case "4": metodeBayar = "KREDIT"; break;
                default:
                    System.out.println("⚠️  Pilihan tidak valid! Pilih 1-4");
            }
        }
        transaksi.setMetodeBayar(metodeBayar);

        // Alur Normal 2: Hitung total belanja
        System.out.println("\n🧮 MENGHITUNG TOTAL...");
        double totalAkhir = transaksi.hitungTotal();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("           RINGKASAN TRANSAKSI");
        System.out.println("=".repeat(50));
        System.out.println("Kasir       : " + transaksi.getKasir());
        System.out.println("Total Item  : " + transaksi.getItems().size() + " jenis");

        double totalQuantity = 0;
        for (item item : transaksi.getItems()) {
            totalQuantity += item.getQuantity();
        }
        System.out.println("Total Qty   : " + (int)totalQuantity + " pcs");
        System.out.println("Subtotal    : Rp" + transaksi.getItems().stream()
                .mapToDouble(item::getSubtotal).sum());
        System.out.println("Diskon      : Rp" + transaksi.getDiskon());
        System.out.println("Pajak (10%) : Rp" + (transaksi.getItems().stream()
                .mapToDouble(item::getSubtotal).sum() - transaksi.getDiskon()) * 0.1);
        System.out.println("TOTAL       : Rp" + totalAkhir);
        System.out.println("Metode Bayar: " + transaksi.getMetodeBayar());
        System.out.println("=".repeat(50));

        // Alur Normal 3 & Alternatif: Cetak struk dengan preview
        System.out.println("\n🖨️  CETAK STRUK");
        System.out.println("1. Langsung cetak struk");
        System.out.println("2. Preview struk dulu");
        System.out.print("Pilih (1-2): ");
        String pilihanCetak = scanner.nextLine();

        if (pilihanCetak.equals("2")) {
            // Alur Alternatif: Preview struk
            System.out.println("\n=== PREVIEW STRUK ===");
            struk previewStruk = transaksi.cetakStruk();
            if (previewStruk != null) {
                previewStruk.previewStruk();

                System.out.print("Cetak struk? (Y/N): ");
                String konfirmasiCetak = scanner.nextLine();
                if (!konfirmasiCetak.equalsIgnoreCase("Y")) {
                    System.out.println("❌ Transaksi dibatalkan.");
                    scanner.close();
                    return;
                }
            }
        }

        // Alur Normal 4-7: Proses cetak struk
        System.out.println("\n🖨️  MENCETAK STRUK...");

        // Pre-Condition 3: Simulasi printer terhubung
        printer printer = new printer("Epson TM-U220");
        printer.connect();

        struk strukAkhir = transaksi.cetakStruk();
        if (strukAkhir != null) {
            System.out.println("\n" + "=".repeat(50));
            strukAkhir.printStruk();
            System.out.println("=".repeat(50));

            // Post-Condition: Tampilkan info transaksi
            System.out.println("\n📋 INFO TRANSAKSI:");
            System.out.println("Transaksi ID: " + transaksi.getId());
            System.out.println("Struk No    : " + strukAkhir.getNoStruk());
            System.out.println("Status      : ✅ Transaksi tercatat dalam sistem");

            System.out.println("\n✅ TRANSAKSI BERHASIL!");
        }

        // Fitur: Kelola data item (opsional)
        System.out.println("\n📦 KELOLA DATA ITEM (Opsional)");
        System.out.print("Tampilkan daftar item transaksi? (Y/N): ");
        String tampilkanItems = scanner.nextLine();

        if (tampilkanItems.equalsIgnoreCase("Y")) {
            System.out.println("\n=== DAFTAR ITEM TRANSAKSI ===");
            for (int i = 0; i < transaksi.getItems().size(); i++) {
                item item = transaksi.getItems().get(i);
                System.out.println((i+1) + ". " + item.getKode() + " - " + item.getNama() +
                        " | Rp" + item.getHarga() + " x " + item.getQuantity() +
                        " = Rp" + item.getSubtotal());
            }
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("   TERIMA KASIH TELAH BERBELANJA");
        System.out.println("        DI TOKO SEDERHANA");
        System.out.println("=".repeat(50));

        scanner.close();
    }
}