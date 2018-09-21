/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelleryshop.object;

import java.awt.List;
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
public class Work_db {
    String server, port, user, pass, conn_str;
    Connection con;

    public Work_db() {
        server = Login.server;
        port = Login.port;
        user = Login.user;
        pass = Login.pass;
        conn_str = "jdbc:mysql://" + server + ":" + port + "/shop_database";
    }

    public boolean isValidWorker(int worker_id) {
        int ret = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT COUNT(*) as count FROM worker WHERE worker_id='" + worker_id + "'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = Integer.parseInt(res.getString("count"));
            }
            if (ret == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Work_db.isValidWorker(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            System.err.println("Work_db.isValidWorker(), ClassNotFoundException : " + ex.getMessage());
            return false;
        }
    }

    public boolean isValidOrder(int type_id) {
        int ret = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT COUNT(*) as count FROM orderlist WHERE order_id='" + type_id + "'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = Integer.parseInt(res.getString("count"));
            }
            if (ret == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Work_db.isValidWorker(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            System.err.println("Work_db.isValidWorker(), ClassNotFoundException : " + ex.getMessage());
            return false;
        }
    }

    public boolean isValidRefurnish(int type_id) {

        int ret = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT COUNT(*) as count FROM refurnish_item WHERE id='" + type_id + "'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = Integer.parseInt(res.getString("count"));
            }
            if (ret == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Work_db.isValidWorker(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            System.err.println("Work_db.isValidWorker(), ClassNotFoundException : " + ex.getMessage());
            return false;
        }
    }

    public String getAutoIncriment() {
        String ret = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SHOW TABLE STATUS FROM shop_database WHERE name LIKE 'works'";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                ret = res.getString("auto_increment");
            }
            return ret;
        } catch (SQLException ex) {
            System.err.println("Work_db.getAutoIncriment(), SQLException (" + ex.getErrorCode() + ") : " + ex.getMessage());
            return ret;
        } catch (ClassNotFoundException ex) {
            System.err.println("Work_db.getAutoIncriment(), ClassNotFoundException : " + ex.getMessage());
            return ret;
        }
    }

    public int insert(int work_id, int worker_id, String item_name, String type, int type_id, String dateOfStart, String metal, float givenMetalWt, String notes) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql1 = "INSERT INTO works "
                    + "(work_id, worker_id, item_name, type, type_id, date_of_start, metal, given_matel_wt, return_metal_wt, item_wt, price_paid, metal_paid, date_of_return, notes) "
                    + "VALUES ('" + work_id + "', '" + worker_id + "', '" + item_name + "', '" + type + "', '" + type_id + "', '" + dateOfStart + "', '" + metal + "', '" + givenMetalWt + "', '0.00', '0.00', '0', '0.00', '0', '" + notes + "');";
            String sql2 = "";
            if (type.equals("Order")) {
                sql2 = "UPDATE orderlist SET status = 'Worker Recived' WHERE orderlist.order_id = '" + type_id + "';";
                statment.executeUpdate(sql2);
            } else if (type.equals("Refurnish")) {
                sql2 = "UPDATE refurnish_item SET status = 'Worker Recived' WHERE refurnish_item.id = '" + type_id + "';";
                statment.executeUpdate(sql2);
            }
            statment.executeUpdate(sql1);
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

    public String[] getDetails(String id) {
        String[] returnString = new String[14];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT * FROM works WHERE work_id = '" + id + "';";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                returnString[0] = res.getString("work_id");
                returnString[1] = res.getString("worker_id");
                returnString[2] = res.getString("type");
                returnString[3] = res.getString("type_id");
                returnString[4] = res.getString("item_name");
                returnString[5] = res.getString("date_of_start");
                returnString[6] = res.getString("date_of_return");
                returnString[7] = res.getString("metal");
                returnString[8] = res.getString("given_matel_wt");
                returnString[9] = res.getString("return_metal_wt");
                returnString[10] = res.getString("item_wt");
                returnString[11] = res.getString("price_paid");
                returnString[12] = res.getString("metal_paid");
                returnString[13] = res.getString("notes");
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

    public int complete(int work_id, String work_complete_date, float return_wt, float return_item_wt, boolean pay_to_worker, int payment_money, float payment_metal) {
        Stock_db stock = new Stock_db();
        int stock_id = Integer.parseInt(stock.getAutoIncriment());
        String workDetails[] = this.getDetails(work_id + " ".trim());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            con.setAutoCommit(false);
            Statement statment = con.createStatement();
            String sql1 = "UPDATE works SET date_of_return = '" + work_complete_date + "', return_metal_wt='" + return_wt + "', "
                    + "item_wt = '" + return_item_wt + "' WHERE work_id = '" + work_id + "' ";
            statment.executeUpdate(sql1);
            if (pay_to_worker) {
                String sql2 = "UPDATE works SET price_paid = '" + payment_money + "',metal_paid = '" + payment_metal + "' WHERE work_id = '" + work_id + "'";
                statment.executeUpdate(sql2);
            }
            if (workDetails[2].equals("Order")) {
                String sql3 = "UPDATE orderlist SET status = 'In Stock', stock_id = " + stock_id + " WHERE orderlist.order_id = '" + workDetails[3] + "';";
                statment.executeUpdate(sql3);
            } else if (workDetails[2].equals("Refurnish")) {
                String sql3 = "UPDATE refurnish_item SET status = 'Completed' WHERE refurnish_item.id = '" + workDetails[3] + "';";
                statment.executeUpdate(sql3);
            }

            if (stock.insert(stock_id, work_complete_date, workDetails[4], workDetails[7], return_item_wt, 0, 1, 0, Integer.parseInt(workDetails[3])) != 1) {
                throw new Exception("Not Inserted in Stock Database");
            }
            statment.executeUpdate(sql1);
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

    public List[] getAllWork() {

        List[] returnString = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(conn_str, user, pass);
            Statement statment = con.createStatement();
            String sql = "SELECT * FROM works WHERE 1;";
            ResultSet res = statment.executeQuery(sql);
            while (res.next()) {
                returnString[0].add(res.getString("work_id"));
                returnString[1].add(res.getString("worker_id"));
                returnString[1].add(res.getString("type"));
                returnString[1].add(res.getString("type_id"));
                returnString[1].add(res.getString("item_name"));
                returnString[1].add(res.getString("date_of_start"));
                returnString[1].add(res.getString("metal"));
                returnString[1].add(res.getString("given_matel_wt"));
                returnString[1].add(res.getString("return_metal_wt"));
                returnString[1].add(res.getString("item_wt"));
                returnString[1].add(res.getString("price_paid"));
                returnString[1].add(res.getString("metal_paid"));
                returnString[1].add(res.getString("notes"));
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
}
