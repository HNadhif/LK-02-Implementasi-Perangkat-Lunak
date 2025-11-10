package src.objek;

public class printer {
    // Atribut
    private String nama;
    private String status;

    // Constructor
    public printer(String nama) {
        this.nama = nama;
        this.status = "DISCONNECTED";
    }

    // Method
    public void connect() {
        this.status = "CONNECTED";
        System.out.println("✅ Printer " + nama + " berhasil terhubung");
    }

    public void print(String content) {
        if (!status.equals("CONNECTED")) {
            System.out.println("❌ ERROR: Printer " + nama + " belum terhubung!");
            return;
        }

        System.out.println("🖨️  === MENCETAK KE PRINTER " + nama + " ===");
        System.out.println(content);
        System.out.println("✅ === CETAK SELESAI ===");
    }

    public String status() {
        return "Printer " + nama + " - Status: " + status;
    }


    // Getter

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }
}