package com.example.myapplication.models

// Transaction.kt
data class Transaction(
    val id_transaksi: Int,
    val id_user: Int,
    val jenis_produk: String,
    val jumlah: Int,
    val total_harga: Int,
    val nama_pemesan: String,
    val alamat: String,
    val tgl_pesan: String,
    val tgl_selesai: String
)
