package org.lessons.java.database;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //connessione al database
        String url = "jdbc:mysql://localhost:3306/db_nation";
        String user = "root";
        String password = "root";

        System.out.println("Benvenuto");
        Scanner scan = new Scanner(System.in);

        //provo a connettermi
        try(Connection c = DriverManager.getConnection(url, user, password)) {

            System.out.println("digita per filtrare");
            String filter = scan.nextLine();

            //modifico la query
            String query = """
                    SELECT `countries`.`name` AS `country_name`, `countries`.`country_id`, `regions`.`name` AS `region_name`, `continents`.`name` AS `continent_name`
                    FROM `countries`
                    JOIN `regions` ON `countries`.`region_id` = `regions`.`region_id`
                    JOIN `continents` ON `regions`.`continent_id` = `continents`.`continent_id`
                    WHERE `countries`.`name` LIKE ?
                    ORDER BY `country_name`;
                    """;

            //creo lo statement con la qu3ery
            try(PreparedStatement ps = c.prepareStatement(query)) {

                //setto il valore ? della query
                ps.setString(1, "%" + filter + "%");

                //eseguo la query e uso il ResultSet
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) { //per ogni riga del ResultSet
                        //salvo i valori delle colonne
                        String countryName = rs.getString("country_name");
                        int countryId = rs.getInt("country_id");
                        String regionName = rs.getString("region_name");
                        String continentName = rs.getString("continent_name");
                        //stampo i valori
                        System.out.println(
                                "Nazione: " + countryName +
                                        " - Id Nazione: " + countryId +
                                        " - Regione: " + regionName +
                                        " - Continente: " + continentName
                        );
                    }
                }
            }

        }catch (SQLException e) {
            //errore se non funziona
            System.out.println("Errore impossibile collegarsi a DataBase");
            e.printStackTrace();
        }

        scan.close();

    }

}