<?php

include "koneksi.php";

$id_user = $_GET['id'];

if (!empty($id_user)) {
    // Query untuk mengambil data transaksi berdasarkan id_user
    $cek = $conn->prepare("SELECT id_transaksi, id_user, jenis_produk, jumlah, total_harga, nama_pemesan, alamat, tgl_pesan, tgl_selesai FROM transaksi WHERE id_user = ?");
    $cek->bind_param("i", $id_user);
    $cek->execute();
    $result = $cek->get_result();

    $transaksi = array();

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $transaksi[] = $row;
        }
        echo json_encode($transaksi); // Mengembalikan data sebagai JSON
    } else {
        echo json_encode(array("error" => "Transaksi tidak ditemukan"));
    }
} else {
    echo json_encode(array("error" => "ID User tidak boleh kosong"));
}

?>