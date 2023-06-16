package org.lessons.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        //connessione al database
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        //provo a connettermi
        try(Connection c = DriverManager.getConnection(url, user, password)) {

            System.out.println(c.getCatalog());
            System.out.println("Collegamento riuscito");

        }catch (SQLException e) {
            //errore se non funziona
            System.out.println("Errore impossibile collegarsi a DataBase");
            e.printStackTrace();
        }

    }

}