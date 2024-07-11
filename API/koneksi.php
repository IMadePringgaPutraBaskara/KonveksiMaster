<?php

$hostname = "localhost";
$username = "root";
$pass = "";
$dbname = "KonveksiMaster";

$conn = mysqli_connect($hostname, $username, $pass, $dbname);

if (!$conn) {
    echo "Koneksi Gagal";
}
?>