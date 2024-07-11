<?php

include "koneksi.php";

$nama = $_GET['username'];
$password = $_GET['password'];

$cek = "SELECT * FROM user WHERE username = '$nama' AND password = '$password'";
$mysql = mysqli_query($conn, $cek);
$result = mysqli_num_rows($mysql);

$response = array();

if (!empty($nama) && !empty($password)) {
    if ($result == 0) {
        $response['status'] = "error";
        $response['message'] = "Login gagal: Nama atau password salah";
    } else {
        $user = mysqli_fetch_assoc($mysql);
        $response['status'] = "success";
        $response['message'] = "Selamat Datang";
        $response['userId'] = $user['id'];
        $response['email'] = $user['email'];
        $response['no_telp'] = $user['no_telp'];
        $response['alamat'] = $user['alamat'];
        $response['userStatus'] = $user['status'];
    }
} else {
    echo "Ada data yang masih kosong!";
}

echo json_encode($response);

?>