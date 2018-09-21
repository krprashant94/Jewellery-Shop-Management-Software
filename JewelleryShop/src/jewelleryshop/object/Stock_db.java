/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelleryshop.object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jewelleryshop.Login;

/**
 *
 * @author Admin
 */
public class Stock_db {
    String server, port, user, pass, conn_str;
    Connection con;

    public Stock_db() {
        server = Login.server;
        port = Login.port;
        user = Login.user;
        pass = Login.pass;
        conn_str = "jdbc:mysql://" + server + ":" + port + "/shop_database";
    }

    public String getAutoIncriment() {
        String ret = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SHOW TABLE STATUS FROM shop_database WHERE name LIKE 'stock'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = res.getString("auto_increment");
            }
            return ret;
        } catch (SQLException ex) {
            System.err.println("stock.getAutoIncriment(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ret;
        } catch (ClassNotFoundException ex) {
            System.err.println("stock.getAutoIncriment(), ClassNotFoundException : " + ex.getMessage());
            return ret;
        }
    }
    public int insert(int id, String purchaseDate, String itemName, String metal, float weight, float price, int unit, int purchesForm, int order_id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql = "INSERT INTO stock "
                    + "(stock_id, purchase_date, item_name, metal, weight, stock_price, stock_unit, distributor_id, order_id)"
                    + " VALUES (" + id + ", '" + purchaseDate + "', '" + itemName + "', '" + metal + "', " + weight + ", " + price + ", " + unit + ", " + purchesForm + ", "+order_id+");";
            statment.executeUpdate(sql);
            con.commit();
            con.close();
            return 1;
        } catch (SQLException ex) {
            System.err.println("SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ex.getErrorCode();
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException : " + ex.getMessage());
            return -1;
        }
    }
    public int insert(String id, String purchaseDate, String metal, String itemName, String weight, String price, String unit, String purchesForm) {
        int tmp = insert(Integer.parseInt(id), purchaseDate, itemName, metal, Float.parseFloat(weight), Float.parseFloat(price), Integer.parseInt(unit), Integer.parseInt(purchesForm), 0);
        return tmp;
    }
    
    public String[] getStockDetails(String id) {
        String[] returnString = new String[9];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT * FROM stock WHERE stock_id = '" + id + "';";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                returnString[0] = res.getString("stock_id");
                returnString[1] = res.getString("purchase_date");
                returnString[2] = res.getString("item_name");
                returnString[3] = res.getString("metal");
                returnString[4] = res.getString("weight");
                returnString[5] = res.getString("stock_price");
                returnString[6] = res.getString("stock_unit");
                returnString[7] = res.getString("distributor_id");
                returnString[8] = res.getString("order_id");
            }
            con.close();
            return returnString;
        } catch (SQLException ex) {
            System.err.println("SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException : " + ex.getMessage());
            return null;
        }
    }

    public boolean delete(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "DELETE FROM stock WHERE stock.stock_id = '" + id + "';";
            statment.executeUpdate(sql);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException : " + ex.getMessage());
            return false;
        }
    }
}
