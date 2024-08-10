import entities.CdrGenerator.CdrGenerator;
import entities.CdrGenerator.ICdrGenerator;
import entities.UdrGenerator.IUdrGenerator;
import entities.UdrGenerator.UdrGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String url = "jdbc:h2:./src/main/java/databases/H2";
    private static final String user = "admin2";
    private static final String password = "654321";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            ICdrGenerator cdrGenerator = new CdrGenerator(connection);
            cdrGenerator.generateCdrFiles();

            IUdrGenerator udrGenerator = new UdrGenerator(connection);

            udrGenerator.generateReport();
            udrGenerator.generateReport("79998887769");
            udrGenerator.generateReport("7900000001", 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
