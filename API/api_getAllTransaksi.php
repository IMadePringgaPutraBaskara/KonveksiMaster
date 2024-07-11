<?php

include "koneksi.php";

// Query untuk mengambil semua data transaksi
$query_all = "SELECT * FROM transaksi";
$result_all = $conn->query($query_all);

if ($result_all->num_rows > 0) {
    $rows = array();
    while ($row = $result_all->fetch_assoc()) {
        $rows[] = $row;
    }
    echo json_encode($rows); // Mengembalikan semua data sebagai JSON
} else {
    echo json_encode(array("error" => "Tidak ada data transaksi"));
}

?>