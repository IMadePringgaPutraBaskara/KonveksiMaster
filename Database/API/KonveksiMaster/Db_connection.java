package com.example.riderocket;

public class Db_connection {
    public static  String ip = "192.168.1.10";

    public static final String urlLogin = "http://"+ip+"/riderocket/api_login.php";
    public static final String urlRegister = "http://"+ip+"/riderocket/api_register.php";
    public static final String urlGetUser = "http://"+ip+"/riderocket/api_get.php?id_user=";
    public static final String urlUpdateUser = "http://"+ip+"/riderocket/api_update.php?id_user=";
}
