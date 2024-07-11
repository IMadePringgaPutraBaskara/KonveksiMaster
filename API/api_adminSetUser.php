<?php

include "koneksi.php";

$id = $_POST['id'];
$nama = $_POST['username'];
$email = $_POST['email'];
$no_telp = $_POST['no_telp'];
$password = $_POST['password'];
$alamat = $_POST['alamat'];
$status = $_POST['status']; // Mengambil status dari POST request

// Validasi status untuk memastikan hanya 'user' atau 'admin'
if ($status !== 'user' && $status !== 'admin') {
    echo json_encode(['status' => 'error', 'message' => 'Invalid status value']);
    exit();
}

if (!empty($id) && !empty($nama) && !empty($email) && !empty($no_telp) && !empty($alamat) && !empty($status)) {
    // Cek apakah password diisi atau tidak
    if (!empty($password)) {
        // Jika password diisi, tambahkan parameter untuk password
        $query = "UPDATE user SET username=?, email=?, no_telp=?, alamat=?, status=?, password=? WHERE id=?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("ssssssi", $nama, $email, $no_telp, $alamat, $status, $password, $id);
    } else {
        // Jika password tidak diisi, tidak ada perubahan pada password
        $query = "UPDATE user SET username=?, email=?, no_telp=?, alamat=?, status=? WHERE id=?";
        $stmt = $conn->prepare($query);
        $stmt->bind_param("sssss", $nama, $email, $no_telp, $alamat, $status, $id);
    }

    if ($stmt->execute()) {
        echo json_encode(['status' => 'success', 'message' => 'Update Berhasil']);
    } else {
        error_log("MySQL Error: " . $conn->error);
        echo json_encode(['status' => 'error', 'message' => 'Terjadi kesalahan saat mengupdate data. Silakan coba lagi.']);
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Semua data harus diisi lengkap!']);
}

$stmt->close();
$conn->close();
?>