package org.test.mariadb;

import java.sql.*;

public class Test {
    public static void main(String[] args) {
        String connString = "jdbc:mariadb://localhost/testj?user=root";
        if (args.length > 0) {
            connString = args[0];
        }
        try {
            try (Connection con = DriverManager.getConnection(connString)) {
                Statement statement = con.createStatement();
                statement.execute("DROP TABLE IF EXISTS sequence_1_to_10000");
                statement.execute("CREATE TABLE sequence_1_to_10000 (t1 int)");
                try (PreparedStatement prep =
                             con.prepareStatement("INSERT INTO sequence_1_to_10000 VALUES (?)")) {
                    for (int i = 1; i <= 10_000; i++) {
                        prep.setInt(1, i);
                        prep.addBatch();
                    }
                    prep.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
