<?php

include "koneksi.php";

$id = $_POST['id'];

if (!empty($id)) {
    // Query untuk menghapus pengguna berdasarkan ID
    $delete = $conn->prepare("DELETE FROM user WHERE id = ?");
    $delete->bind_param("i", $id);
    $delete->execute();

    if ($delete->affected_rows > 0) {
        echo json_encode(array("message" => "User berhasil dihapus"));
    } else {
        echo json_encode(array("error" => "User tidak ditemukan atau gagal dihapus"));
    }
} else {
    echo json_encode(array("error" => "ID tidak boleh kosong"));
}

?>