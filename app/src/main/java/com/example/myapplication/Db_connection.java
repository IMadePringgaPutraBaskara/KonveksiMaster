package com.example.myapplication;

public class Db_connection {
    public static String ip = "192.168.1.201";

    public static final String urlLogin = "http://" + ip + "/KonveksiMaster/api_login.php";
    public static final String urlRegister = "http://" + ip + "/KonveksiMaster/api_register.php";
    public static final String urlGetUser = "http://" + ip + "/KonveksiMaster/api_get.php?id=";
    public static final String urlUpdateUser = "http://" + ip + "/KonveksiMaster/api_update.php?id=";
    public static final String urlGetTransaksi = "http://" + ip + "/KonveksiMaster/api_getTransaksi.php";
    public static final String urlTransaksi = "http://" + ip + "/KonveksiMaster/api_transaksi.php"; // Tambahkan URL untuk transaksi
    public static final String urlGetAllTransaksi = "http://" + ip + "/KonveksiMaster/api_getAllTransaksi.php"; // Tambahkan URL untuk semua transaksi
}
