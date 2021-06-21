package pages.database;

import org.junit.Assert;
import java.sql.*;

public class Database {

    private static Statement statement;
    private static Connection connection;
    private static int population;


    public static void createDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:countries.db");
            statement = connection.createStatement();

            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("DROP TABLE IF EXISTS countries");
            statement.executeUpdate("CREATE TABLE countries (country string, population integer, area integer)");
            statement.executeUpdate("INSERT INTO countries VALUES ('Ukraine', 41588354, 603628)");
            statement.executeUpdate("INSERT INTO countries VALUES ('France', 67399000, 640679)");
            statement.executeUpdate("INSERT INTO countries VALUES ('USA', 328239523, 9833520)");
            statement.executeUpdate("INSERT INTO countries VALUES ('China', 1400050000, 9596961)");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void populationDensityIsLowerFifty() {
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:countries.db");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM countries");
            while(rs.next())
            {
                String country = rs.getString("country");


                System.out.println("Country = " + country);
                System.out.println("Population = " + rs.getInt("population"));
                population += rs.getInt("population");
                System.out.println("Area = " + rs.getInt("area"));

                int populationInt = rs.getInt("population");
                int areaInt = rs.getInt("area");
                int populationDensity = populationInt /= areaInt;


                System.out.println("population density = " + populationDensity);
                if (populationDensity > 50) {
                    Assert.assertNotEquals("USA", country);
                    System.out.println("Country is not USA and population density bigger than fifty :" +" Country:" + country + " Population density:" + populationDensity);
                }

                if (populationDensity < 50) {
                    Assert.assertEquals("USA", country);
                    System.out.println("Country is USA and population density is lower than fifty: " + " Country: " + country + " Population density: " + populationDensity);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static int checkPopulationOfAllCountries() {
        ResultSet rs = null;
        population = 0;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:countries.db");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT population FROM countries");
            while(rs.next())
            {
                System.out.println("Population = " + rs.getInt("population"));
                population += rs.getInt("population");
                System.out.println("Total population: " + population);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return population;
    }

}
