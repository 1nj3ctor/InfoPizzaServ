/**
 * Created by Cedric on 30.12.2015.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DataGatherer {
    private Connection conn = null;

    private String dbHost = null; // Hostname
    private String dbPort = null;      // Port -- Standard: 3306
    private String dbName = null;   // Datenbankname
    private String dbUser = null;     // Datenbankuser
    private String dbPass = null;      // Datenbankpasswort
    String[] samplePizzas;
    int count = 0;

    public DataGatherer() {
        MySQLConnection mysqlConn = new MySQLConnection();

        mysqlConn.buildConnection("localhost", "3306", "pizza", "root", "");

        Connection conn = mysqlConn.getConnection();
    }

    public Connection getConnection() {
        return conn;
    }

    public void buildConnection(String dbHostPara, String dbPortPara, String dbNamePara, String dbUserPara, String dbPassPara) {
        try {
            dbHost = dbHostPara;
            dbPort = dbPortPara;
            dbName = dbNamePara;
            dbUser = dbUserPara;
            dbPass = dbPassPara;
            // Datenbanktreiber für JDBC Schnittstellen laden.
            Class.forName("com.mysql.jdbc.Driver");
            // Verbindung zur Datenbank herstellen.
            conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass);
        } catch (ClassNotFoundException e) {
            System.out.println("Treiber wurde nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Verbindung nicht moglich");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Fehlercode: " + e.getErrorCode());
        }
    }

    public String[] getPizzas() {
        if (conn != null) {

            // Statement erzeugen.
            Statement stmt;
            try {
                stmt = conn.createStatement();

                // SQL-Abfrage aufbauen
                ResultSet rs = stmt.executeQuery("SELECT name FROM pizza");

                // Ergebnissätze durchlaufen
                while (rs.next()) {
                    samplePizzas[count] = rs.getString("name");
                    count++;
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return samplePizzas;
    }


    public double getPrice(String pizza) {

        double price = 0;
        if (conn != null) {

            // Statement erzeugen.
            Statement stmt;
            try {
                stmt = conn.createStatement();

                // SQL-Abfrage aufbauen
                ResultSet rs = stmt.executeQuery("SELECT price FROM pizza WHERE name='" + pizza + "'");

                // Ergebnissätze durchlaufen

                price = rs.getDouble("price");

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

}
