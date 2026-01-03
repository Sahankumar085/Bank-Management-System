package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame{
    
    MiniStatement(String pinnumber) {
        setTitle("Mini Statement");
        
        setLayout(null);
        
        JLabel text = new JLabel();
        add(text);
        
        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 100, 20);
        add(bank);
        
        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);
        
        JLabel mini = new JLabel();
        mini.setBounds(20, 80, 400, 400);
        add(mini);
        
        JLabel balance = new JLabel();
        balance.setBounds(20, 400, 300, 20);
        add(balance);
        
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from login where pin = '" + pinnumber + "'");
            while (rs.next()) {
                String cardNumber = rs.getString("cardnumber");
                // Mask card number: first 4 + last 4
                card.setText("Card Number: " + 
                             cardNumber.substring(0, 4) + 
                             "XXXXXXXX" + 
                             cardNumber.substring(12));
            }
        } catch (Exception e) {
            System.out.println("Error fetching card details: " + e);
        }

        try {
            Conn conn = new Conn();
            int bal = 0;
            ResultSet rs = conn.s.executeQuery("select * from bank where pin = '" + pinnumber + "'");

            StringBuilder statement = new StringBuilder("<html>");
            while (rs.next()) {
                statement.append(rs.getString("date"))
                         .append("&nbsp;&nbsp;&nbsp;&nbsp;")
                         .append(rs.getString("type"))
                         .append("&nbsp;&nbsp;&nbsp;&nbsp;")
                         .append(rs.getString("amount"))
                         .append("<br><br>");

                if (rs.getString("type").equalsIgnoreCase("Deposit")) {
                    bal += Integer.parseInt(rs.getString("amount"));
                } else {
                    bal -= Integer.parseInt(rs.getString("amount"));
                }
            }
            statement.append("</html>");
            mini.setText(statement.toString());

            balance.setText("Your current account balance is Rs " + bal);
        } catch (Exception e) {
            System.out.println("Error fetching transactions: " + e);
        }
        
        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
      new MiniStatement("");
    }
}


