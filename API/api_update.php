<?php

include "koneksi.php";

// Set the content type to JSON
header('Content-Type: application/json');

$nama = $_POST['username'];
$email = $_POST['email'];
$no_telp = $_POST['no_telp'];
$alamat = $_POST['alamat'];
$id = $_POST['id'];
$password = isset($_POST['password']) ? $_POST['password'] : '';  // Check if password is set

if (!empty($nama) && !empty($email) && !empty($no_telp) && !empty($alamat) && !empty($id)) {
    // Prepare the query to update user information based on ID
    if (!empty($password)) {
        $update = $conn->prepare("UPDATE user SET username=?, email=?, no_telp=?, alamat=?, password=? WHERE id=?");
        $update->bind_param("sssssi", $nama, $email, $no_telp, $alamat, $password, $id);
    } else {
        $update = $conn->prepare("UPDATE user SET username=?, email=?, no_telp=?, alamat=? WHERE id=?");
        $update->bind_param("ssssi", $nama, $email, $no_telp, $alamat, $id);
    }

    if ($update->execute()) {
        echo json_encode(["status" => "success", "message" => "Update Berhasil"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Terjadi kesalahan saat mengupdate data. Silakan coba lagi."]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Semua data harus diisi lengkap!"]);
}

?>