<?php

include "koneksi.php"; // Sesuaikan dengan nama file koneksi.php yang Anda gunakan

// Ambil data dari POST request
$nama = $_POST['username'];
$email = $_POST['email'];
$no_telp = $_POST['tel_number']; // Sesuaikan dengan nama yang dikirim dari Android
$password = $_POST['password'];
$alamat = $_POST['address']; // Sesuaikan dengan nama yang dikirim dari Android

// Validasi input
if (!empty($nama) && !empty($email) && !empty($no_telp) && !empty($password) && !empty($alamat)) {
    // Cek apakah username sudah ada di database
    $cek = "SELECT * FROM user WHERE username = '" . $nama . "'";
    $mysql = mysqli_query($conn, $cek);
    $result = mysqli_num_rows($mysql);

    if ($result == 0) {
        // Jika username belum ada, lakukan proses registrasi
        $regis = "INSERT INTO user(username, email, no_telp, password, alamat) VALUES ('$nama', '$email', '$no_telp', '$password', '$alamat')";
        $mysqlRegis = mysqli_query($conn, $regis);
        if ($mysqlRegis) {
            echo "Daftar Berhasil";
        } else {
            echo "Gagal melakukan registrasi";
        }
    } else {
        // Jika username sudah ada, beri pesan kesalahan
        echo "Username sudah digunakan";
    }
} else {
    // Jika ada data yang kosong, beri pesan kesalahan
    echo "Semua data harus diisi lengkap!";
}

?>