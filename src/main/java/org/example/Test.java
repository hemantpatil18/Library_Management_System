
package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) {

        try (Connection con =
                     DBConnection.getConnection()) {

            System.out.println(
                    "MySQL Connected Successfully!"
            );

        } catch (SQLException e) {
            System.out.println(
                    "Database connection failed!"
            );
            e.printStackTrace();
        }
    }
}
