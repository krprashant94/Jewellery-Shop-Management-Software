/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelleryshop.object;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class ShoppingCart {

    public ShoppingCart() {

    }

    public boolean addOrder(String order_no) {
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("order.cart", true); //true tells to append data.
            out = new BufferedWriter(fstream);
            out.write(order_no.trim() + "\n");
            out.close();
        } catch (IOException e) {
            System.err.println("ShoppingCart.addOrder() : " + e.getMessage());
        }
        return true;
    }
}
