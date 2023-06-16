package org.lessons.java.database;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        //connessione al database
        String url = "jdbc:mysql://localhost:3306/db_nation";
        String user = "root";
        String password = "root";

        //provo a connettermi
        try(Connection c = DriverManager.getConnection(url, user, password)) {
            //definisco la query
            String query = """
                    SELECT countries.country_id as country_id, countries.name as country, regions.name as region_name,
                    continents.name as continent_name FROM `countries`
                    JOIN regions ON regions.region_id = countries.country_id
                    JOIN continents ON continents.continent_id = regions.continent_id
                    ORDER BY country;
                    """;

            //creo lo statement con la qu3ery
            try(PreparedStatement ps = c.prepareStatement(query)) {

                //eseguo la query e uso il ResultSet
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) { //per ogni riga del ResultSet
                        //salvo i valori delle colonne
                        String countryName = rs.getString("country");
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

    }

}