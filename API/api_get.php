<?php

include "koneksi.php";

$id = $_GET['id'];

if (!empty($id)) {
    // Query untuk mengambil data profil pengguna berdasarkan ID
    $cek = $conn->prepare("SELECT username, email, no_telp, alamat FROM user WHERE id = ?");
    $cek->bind_param("i", $id);
    $cek->execute();
    $result = $cek->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo json_encode($row); // Mengembalikan data sebagai JSON
    } else {
        echo json_encode(array("error" => "User tidak ditemukan"));
    }
} else {
    echo json_encode(array("error" => "ID tidak boleh kosong"));
}

?>