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
public class Order_db {

    String server, port, user, pass, conn_str;
    Connection con;
    ShoppingCart cart = new ShoppingCart();

    public Order_db() {
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
            String sql = "SHOW TABLE STATUS FROM shop_database WHERE name LIKE 'orderlist'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = res.getString("auto_increment");
            }
            return ret;
        } catch (SQLException ex) {
            System.err.println("Order.getAutoIncriment(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ret;
        } catch (ClassNotFoundException ex) {
            System.err.println("Order.getAutoIncriment(), ClassNotFoundException : " + ex.getMessage());
            return ret;
        }
    }

    public boolean delete(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "DELETE FROM orderlist WHERE orderlist.order_id = '" + id + "';";
            statment.executeUpdate(sql);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("Order.delete(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            System.err.println("Order.delete(), ClassNotFoundException : " + ex.getMessage());
            return false;
        }
    }

    public int insert(int id, String date, String c_name, String c_add, String c_ph, String item, String metal, float min_wt, float max_wt, float advance, String notes) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql = "INSERT INTO orderlist "
                    + "(order_id, order_date, c_name, address, ph, item_name, metal, min_weight, max_weight, stock_id, product_id, advance, notes, status) "
                    + "VALUES ('" + id + "', '" + date + "', '" + c_name + "', '" + c_add + "', '" + c_ph + "', '" + item + "', '" + metal + "', '" + min_wt + "', '" + max_wt + "', '0', '0', '" + advance + "', '" + notes + "', 'Order Recived');";
            statment.executeUpdate(sql);
            con.commit();
            con.close();
            cart.addOrder(id + " ");
            return 1;
        } catch (SQLException ex) {
            System.err.println("SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ex.getErrorCode();
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException : " + ex.getMessage());
            return -1;
        }
    }

    public int update(int id, String date, String c_name, String c_add, String c_ph, String item, String metal, float min_wt, float max_wt, float advance, String notes) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql = "UPDATE orderlist SET order_date = '" + date + "',c_name = '" + c_name + "', "
                    + "address= '" + c_add + "', ph = '" + c_ph + "', item_name = '" + item + "', "
                    + "metal = '" + metal + "', min_weight = '" + min_wt + "', max_weight = '" + max_wt + "', "
                    + "advance = '" + advance + "', notes = '" + notes + "' WHERE order_id = '" + id + "'";
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

    public String[] getOrderDetails(String id) {
        String[] returnString = new String[14];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT * FROM `orderlist` WHERE order_id='" + id + "';";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                returnString[0] = res.getString("order_id");
                returnString[1] = res.getString("order_date");
                returnString[2] = res.getString("c_name");
                returnString[3] = res.getString("address");
                returnString[4] = res.getString("ph");
                returnString[5] = res.getString("item_name");
                returnString[6] = res.getString("metal");
                returnString[7] = res.getString("min_weight");
                returnString[8] = res.getString("max_weight");
                returnString[9] = res.getString("stock_id");
                returnString[10] = res.getString("product_id");
                returnString[11] = res.getString("advance");
                returnString[12] = res.getString("notes");
                returnString[13] = res.getString("status");
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
    public int complete(int orderID, int stock_id, float price) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql1 = "UPDATE stock SET stock_price = " + price + " WHERE stock.stock_id = " + stock_id + ";";
            String sql2 = "UPDATE orderlist SET orderlist.status = 'Completed', orderlist.stock_id = '" + stock_id + "' WHERE orderlist.order_id = '" + orderID + "';";
            statment.executeUpdate(sql1);
            statment.executeUpdate(sql2);
            con.commit();
            con.close();
            return 1;
        } catch (SQLException ex) {
            System.err.println("SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ex.getErrorCode();
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException : " + ex.getMessage());
            return -1;
        } catch (Exception ex) {
            System.err.println("Exception : " + ex.getMessage());
            return -2;
        }
    }

}
