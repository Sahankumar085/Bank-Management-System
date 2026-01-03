package bank.management.system;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;
    
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bankmanagementsystem?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "sahan"
            );
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
