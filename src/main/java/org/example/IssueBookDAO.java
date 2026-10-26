
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class IssueBookDAO {

    public boolean issueBook(int bookId, int studentId) {

        String checkBook =
                "SELECT quantity FROM books WHERE book_id = ? FOR UPDATE";

        String checkStudent =
                "SELECT student_id FROM students WHERE student_id = ?";

        String insertIssue =
                "INSERT INTO issued_books "
                        + "(book_id, student_id, issue_date, status) "
                        + "VALUES (?, ?, ?, 'ISSUED')";

        String updateQuantity =
                "UPDATE books SET quantity = quantity - 1 "
                        + "WHERE book_id = ? AND quantity > 0";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                // Check whether the student exists
                try (PreparedStatement ps =
                             con.prepareStatement(checkStudent)) {

                    ps.setInt(1, studentId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Student not found!");
                            con.rollback();
                            return false;
                        }
                    }
                }

                // Check book availability
                try (PreparedStatement ps =
                             con.prepareStatement(checkBook)) {

                    ps.setInt(1, bookId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Book not found!");
                            con.rollback();
                            return false;
                        }

                        if (rs.getInt("quantity") <= 0) {
                            System.out.println("Book is not available!");
                            con.rollback();
                            return false;
                        }
                    }
                }

                // Save issue record
                try (PreparedStatement ps =
                             con.prepareStatement(insertIssue)) {

                    ps.setInt(1, bookId);
                    ps.setInt(2, studentId);
                    ps.setDate(3, Date.valueOf(
                            java.time.LocalDate.now()));

                    ps.executeUpdate();
                }

                // Decrease available quantity
                try (PreparedStatement ps =
                             con.prepareStatement(updateQuantity)) {

                    ps.setInt(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        System.out.println("Could not update quantity!");
                        return false;
                    }
                }

                con.commit();
                System.out.println("Book issued successfully!");
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Issue error: " + e.getMessage());
            return false;
        }
    }

    public boolean returnBook(int issueId) {

        String findSql =
                "SELECT book_id FROM issued_books "
                        + "WHERE issue_id = ? AND status = 'ISSUED' FOR UPDATE";

        String returnSql =
                "UPDATE issued_books SET status = 'RETURNED', "
                        + "return_date = ? WHERE issue_id = ? AND status = 'ISSUED'";

        String quantitySql =
                "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int bookId;

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(findSql)) {

                    ps.setInt(1, issueId);

                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println(
                                    "Active issue record not found!");
                            con.rollback();
                            return false;
                        }

                        bookId = rs.getInt("book_id");
                    }
                }

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(returnSql)) {

                    ps.setDate(1, java.sql.Date.valueOf(
                            java.time.LocalDate.now()));
                    ps.setInt(2, issueId);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(quantitySql)) {

                    ps.setInt(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                con.commit();
                System.out.println("Book returned successfully!");
                return true;

            } catch (java.sql.SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Return error: " + e.getMessage());
            return false;
        }
    }


    public void viewIssuedBooks() {

        String sql = "SELECT i.issue_id, b.title, s.name, "
                + "i.issue_date, i.return_date, i.status "
                + "FROM issued_books i "
                + "JOIN books b ON i.book_id = b.book_id "
                + "JOIN students s ON i.student_id = s.student_id "
                + "ORDER BY i.issue_id";

        try (java.sql.Connection con = DBConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Issue ID: "
                        + rs.getInt("issue_id"));
                System.out.println("Book: "
                        + rs.getString("title"));
                System.out.println("Student: "
                        + rs.getString("name"));
                System.out.println("Issue Date: "
                        + rs.getDate("issue_date"));
                System.out.println("Return Date: "
                        + rs.getDate("return_date"));
                System.out.println("Status: "
                        + rs.getString("status"));
                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No issue records found.");
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
