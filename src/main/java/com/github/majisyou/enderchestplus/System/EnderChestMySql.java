package com.github.majisyou.enderchestplus.System;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderChestMySql {

    private Connection connection;
    private String host, database, username, password,tablename1,tablename2;
    private int port;
    private final EnderChestPlus plugin = EnderChestPlus.getInstance();

    public EnderChestMySql(){
        host = ConfigManager.getHost();
        port = ConfigManager.getPort();
        database = ConfigManager.getDatabase();
        username = ConfigManager.getUserName();
        password = ConfigManager.getPassWord();
        tablename1 = ConfigManager.getTableName1();
        tablename2 = ConfigManager.getTableName2();
    }

    public void insertLock(UUID player,int page){
        try {
            openConnection();
            PreparedStatement statement = this.connection.prepareStatement("INSERT IGNORE INTO "+tablename1+" (uuid, page) VALUES (?,?);");
            try {
                statement.setString(1, player.toString());
                statement.setInt(2, page);
                statement.executeUpdate();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteLock(UUID player){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM "+tablename1+" WHERE uuid = ?;");
            try {
                statement.setString(1, player.toString());
                statement.executeUpdate();
            } finally {
                if (statement != null) {
                    statement.close();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID,Integer> readEClockAll(){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+tablename1+";");
            try {
                ResultSet resultSet = statement.executeQuery();
                HashMap<UUID,Integer> resultMap = new HashMap<>();
                while (resultSet.next()){
                    try {
                        int page = resultSet.getInt("page");
                        String uuid = resultSet.getString("uuid");
                        resultMap.put(UUID.fromString(uuid),page);
                    }catch (Exception ignored){}
                }
                return resultMap;
            }catch (Exception ignored){}
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void initEnderChest(UUID player){
        String data = (new Gson()).toJson(new JsonArray());
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT IGNORE INTO "+tablename2+" (uuid, page, data) VALUES (?,1,?),(?,2,?),(?,3,?),(?,4,?),(?,5,?),(?,6,?),(?,7,?),(?,8,?),(?,9,?);");
            try {
                statement.setString(1, player.toString());
                statement.setString(3, player.toString());
                statement.setString(5, player.toString());
                statement.setString(7, player.toString());
                statement.setString(9, player.toString());
                statement.setString(11, player.toString());
                statement.setString(13, player.toString());
                statement.setString(15, player.toString());
                statement.setString(17, player.toString());
                statement.setString(2, data);
                statement.setString(4, data);
                statement.setString(6, data);
                statement.setString(8, data);
                statement.setString(10, data);
                statement.setString(12, data);
                statement.setString(14, data);
                statement.setString(16, data);
                statement.setString(18, data);

                statement.executeUpdate();
            }finally {
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertEnderChest(UUID player,int page,String data){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+tablename2+" (uuid, page, data) VALUES (?,?,?);");
            try {
                statement.setString(1, player.toString());
                statement.setInt(2, page);
                statement.setString(3, data);

                statement.executeUpdate();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnderChest(UUID player,int page){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM "+tablename2+" WHERE uuid = ? AND page = ?;");
            try {
                statement.setString(1, player.toString());
                statement.setInt(2, page);
                statement.executeUpdate();
            } finally {
                if (statement != null) {
                    statement.close();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnderChestAll(UUID player){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM "+tablename2+" WHERE uuid = ?;");
            try {
                statement.setString(1, player.toString());
                statement.executeUpdate();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String readEnderChest(UUID player,int page){
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT data FROM "+tablename2+" WHERE uuid = ? AND page = ?;");
            try {
                statement.setString(1, player.toString());
                statement.setInt(2, page);
                ResultSet result = statement.executeQuery();
                if(result.next()){
                    String data = result.getString("data");
                    statement.close();
                    return data;
                }
            }finally {
                if(statement != null){
                    statement.close();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openConnection() throws SQLException, ClassNotFoundException{
        if (connection != null && !connection.isClosed()){
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }

}
