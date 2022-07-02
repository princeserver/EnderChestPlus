package com.github.majisyou.enderchestplus.System;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ConfigManager {

    private static final EnderChestPlus plugin = EnderChestPlus.getInstance();
    private static final FileConfiguration config = plugin.getConfig();


    private static String Host;
    private static String Database;
    private static Integer Port;
    private static String UserName;
    private static String PassWord;
    private static String TableName1;
    private static String TableName2;


    public static void loadConfig(){
        Host = config.getString("MySql.Host");
        Database = config.getString("MySql.Database");
        Port = config.getInt("MySql.Port");
        UserName = config.getString("MySql.UserName");
        PassWord = config.getString("MySql.PassWord");
        TableName1 = config.getString("MySql.TableName1");
        TableName2 = config.getString("MySql.TableName2");
    }

    public static String getHost(){return Host;}
    public static String getDatabase(){return Database;}
    public static Integer getPort(){return Port;}
    public static String getUserName(){return UserName;}
    public static String getPassWord(){return PassWord;}
    public static String getTableName1(){return TableName1;}
    public static String getTableName2(){return TableName2;}


}
