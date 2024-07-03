package com.example.myapplication;

public class Db_connection {
    public static  String ip = "192.168.1.91";

    public static final String urlLogin = "http://"+ip+"/KonveksiMaster/api_login.php";
    public static final String urlRegister = "http://"+ip+"/KonveksiMaster/api_register.php";
    public static final String urlGetUser = "http://"+ip+"/KonveksiMastter/api_get.php?id=";
    public static final String urlUpdateUser = "http://"+ip+"/KonveksiMaster/api_update.php?id=";
}
