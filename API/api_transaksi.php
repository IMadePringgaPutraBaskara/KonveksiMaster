<?php

include "koneksi.php";

// Mengambil data dari request POST
$id_user = $_POST['id_user'];
$jenis_produk = $_POST['jenis_produk'];
$jumlah = $_POST['jumlah']; // Menambahkan jumlah
$nama_pemesan = $_POST['nama_pemesan'];
$alamat = $_POST['alamat'];
$tgl_pesan = $_POST['tgl_pesan'];
$tgl_selesai = $_POST['tgl_selesai'];
$total_harga = $_POST['total_harga'];

// Validasi input
if (!empty($id_user) && !empty($jenis_produk) && !empty($jumlah) && !empty($nama_pemesan) && !empty($alamat) && !empty($tgl_pesan) && !empty($tgl_selesai) && !empty($total_harga)) {
    // Query untuk menambahkan transaksi
    $query = "INSERT INTO transaksi (id_user, jenis_produk, jumlah, nama_pemesan, alamat, tgl_pesan, tgl_selesai, total_harga)
                VALUES ('$id_user', '$jenis_produk', '$jumlah', '$nama_pemesan', '$alamat', '$tgl_pesan', '$tgl_selesai', '$total_harga')";

    // Eksekusi query
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => "success", "message" => "Transaksi berhasil ditambahkan."]);
    } else {
        echo json_encode(["status" => "error", "message" => "Gagal menambahkan transaksi: " . mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Semua data harus diisi lengkap!"]);
}

?>