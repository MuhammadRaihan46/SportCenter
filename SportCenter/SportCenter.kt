class Member(
    val idMember: String,
    val namaMember: String,
    saldoAwal: Double
) {
    private var saldo: Double = saldoAwal

    fun getSaldo(): Double = saldo

    fun kurangiSaldo(nominal: Double): Boolean {
        if (nominal <= 0) {
            println("  [ERROR] Nominal tidak valid!")
            return false
        }
        if (saldo < nominal) {
            println("  [GAGAL] Saldo $namaMember tidak mencukupi!")
            println("  Saldo saat ini: Rp${saldo} | Dibutuhkan: Rp${nominal}")
            return false
        }
        saldo -= nominal
        println("  [SUKSES] Saldo berkurang Rp${nominal} | Sisa: Rp${saldo}")
        return true
    }

    fun topUpSaldo(nominal: Double) {
        if (nominal <= 0) {
            println("  [ERROR] Nominal top up tidak valid!")
            return
        }
        saldo += nominal
        println("  [SUKSES] Top up Rp${nominal} berhasil! Saldo: Rp${saldo}")
    }

    fun tampilkanInfo() {
        println("  Member : $namaMember (ID: $idMember) | Saldo: Rp${saldo}")
    }
}

class Lapangan(
    val idLapangan: String,
    val namaLapangan: String,
    val hargaSewa: Double
) {
    private var isBooked: Boolean = false
    private var jadwalBooking: String = "-"

    fun getStatus(): Boolean = isBooked
    fun getJadwal(): String = jadwalBooking

    fun setStatusBooking(jadwal: String): Boolean {
        if (isBooked) {
            println("  [GAGAL] Lapangan $namaLapangan sudah terisi!")
            println("  Jadwal terisi: $jadwalBooking")
            return false
        }
        isBooked = true
        jadwalBooking = jadwal
        println("  [SUKSES] Lapangan $namaLapangan berhasil dibooking!")
        println("  Jadwal: $jadwalBooking")
        return true
    }

    fun tampilkanInfo() {
        val status = if (isBooked) "Terisi (Jadwal: $jadwalBooking)" else "Tersedia"
        println("  Lapangan : $namaLapangan | Harga: Rp${hargaSewa}/jam | Status: $status")
    }
}

class Resepsionis(
    val idResepsionis: String,
    val namaResepsionis: String
) {
    private var isLoggedIn: Boolean = false

    fun login(password: String): Boolean {
        val passwordBenar = "sportcenter123"
        if (password != passwordBenar) {
            println("  [GAGAL] Password salah!")
            return false
        }
        isLoggedIn = true
        println("  [SUKSES] $namaResepsionis berhasil login!")
        return true
    }

    fun prosesBooking(member: Member, lapangan: Lapangan, jadwal: String): Boolean {
        if (!isLoggedIn) {
            println("  [GAGAL] Resepsionis belum login!")
            return false
        }
        if (lapangan.getStatus()) {
            println("  [GAGAL] Lapangan sudah tidak tersedia!")
            return false
        }
        if (member.getSaldo() < lapangan.hargaSewa) {
            println("  [GAGAL] Saldo ${member.namaMember} tidak mencukupi!")
            println("  Saldo: Rp${member.getSaldo()} | Harga: Rp${lapangan.hargaSewa}")
            return false
        }
        member.kurangiSaldo(lapangan.hargaSewa)
        lapangan.setStatusBooking(jadwal)
        println("  [SUKSES] Booking diproses oleh $namaResepsionis!")
        return true
    }

    fun topUpSaldo(member: Member, nominal: Double) {
        if (!isLoggedIn) {
            println("  [GAGAL] Resepsionis belum login!")
            return
        }
        member.topUpSaldo(nominal)
    }
}

fun main() {
    println("==============================================")
    println("   SIMULASI SISTEM SPORT CENTER ITK")
    println("==============================================")

    val member1 = Member("M001", "Budi Santoso", 50000.0)
    val member2 = Member("M002", "Siti Rahayu", 200000.0)
    val lapangan1 = Lapangan("L001", "Lapangan Badminton A", 100000.0)
    val lapangan2 = Lapangan("L002", "Lapangan Futsal B", 150000.0)
    val resepsionis = Resepsionis("R001", "Dewi Anggraini")

    println("\n--- INFO AWAL ---")
    member1.tampilkanInfo()
    member2.tampilkanInfo()
    lapangan1.tampilkanInfo()
    lapangan2.tampilkanInfo()

    println("\n--- SIMULASI GAGAL #1: Booking tanpa login ---")
    resepsionis.prosesBooking(member2, lapangan1, "Senin 08.00-09.00")

    println("\n--- SIMULASI GAGAL #2: Login password salah ---")
    resepsionis.login("passwordsalah")

    println("\n--- RESEPSIONIS LOGIN ---")
    resepsionis.login("sportcenter123")

    println("\n--- SIMULASI GAGAL #3: Saldo Budi kurang ---")
    resepsionis.prosesBooking(member1, lapangan1, "Senin 08.00-09.00")

    println("\n--- SIMULASI SUKSES #1: Top up saldo Budi ---")
    resepsionis.topUpSaldo(member1, 150000.0)

    println("\n--- SIMULASI SUKSES #2: Budi booking Lapangan Badminton ---")
    resepsionis.prosesBooking(member1, lapangan1, "Senin 08.00-09.00")

    println("\n--- SIMULASI GAGAL #4: Siti booking lapangan yang sudah terisi ---")
    resepsionis.prosesBooking(member2, lapangan1, "Senin 08.00-09.00")

    println("\n--- SIMULASI SUKSES #3: Siti booking Lapangan Futsal ---")
    resepsionis.prosesBooking(member2, lapangan2, "Senin 10.00-11.00")

    println("\n--- INFO AKHIR ---")
    member1.tampilkanInfo()
    member2.tampilkanInfo()
    lapangan1.tampilkanInfo()
    lapangan2.tampilkanInfo()

    println("\n==============================================")
    println("   SIMULASI SELESAI")
    println("==============================================")
}