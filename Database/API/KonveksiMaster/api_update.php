<?php

include "koneksi.php";

$nama = $_POST['username'];
$email = $_POST['email'];
$no_telp = $_POST['no_telp'];
$alamat = $_POST['alamat'];
$id = $_POST['id'];

if (!empty($nama) && !empty($email) && !empty($no_telp) && !empty($alamat) && !empty($id)) {
    // Query untuk memperbarui data profil pengguna berdasarkan ID
    $update = $conn->prepare("UPDATE user SET username=?, email=?, no_telp=?, alamat=? WHERE id=?");
    $update->bind_param("ssssi", $nama, $email, $no_telp, $alamat, $id);
    if ($update->execute()) {
        echo "Update Berhasil";
    } else {
        echo "Terjadi kesalahan saat mengupdate data. Silakan coba lagi.";
    }
} else {
    echo "Semua data harus diisi lengkap!";
}

?>