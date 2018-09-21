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
public class Product_db {

    String server, port, user, pass, conn_str;
    Connection con;

    public Product_db() {
        server = Login.server;
        port = Login.port;
        user = Login.user;
        pass = Login.pass;
        conn_str = "jdbc:mysql://" + server + ":" + port + "/shop_database";
    }

    public int insert(int stock_id, String name, float weight, float making, String metal) {
        try {
            float st_wt = 0;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql1 = "INSERT INTO products "
                    + "(stock_id, item_name, date_of_adding, weight, making_charge, metal) "
                    + "VALUES ('" + stock_id + "', '" + name + "', CURRENT_DATE(), " + weight + ", " + making + ", '" + metal + "');";
            String sql3 = "UPDATE stock SET weight=weight-" + weight + ", stock_unit=stock_unit-1, stock_price=stock_price-" + making + " WHERE stock_id=" + stock_id;
            String sql2 = "SELECT weight FROM stock WHERE stock_id = " + stock_id;
            statment.executeUpdate(sql1);
            ResultSet res = statment.executeQuery(sql2);
            while (res.next()) {
                st_wt = Float.parseFloat(res.getString("weight"));
            }
            if (st_wt >= weight) {
                statment.executeUpdate(sql3);
            } else {
                throw new Exception("Weight Mismatch");
            }
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
            System.err.println(ex.getMessage());
            return -2;
        }
    }
}