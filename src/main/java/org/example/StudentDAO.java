
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    public void addStudent(Student student) {

        String sql = "INSERT INTO students "
                + "(student_id, name, contact) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getStudentId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getContact());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding student: "
                    + e.getMessage());
        }
    }

    public void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Student ID: "
                        + rs.getInt("student_id"));
                System.out.println("Name: "
                        + rs.getString("name"));
                System.out.println("Contact: "
                        + rs.getString("contact"));
                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No students found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing students: "
                    + e.getMessage());
        }
    }

    public Student searchStudent(int id) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getString("contact")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }

        return null;
    }

    public boolean updateStudent(Student student) {

        String sql = "UPDATE students SET name = ?, contact = ? "
                + "WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getContact());
            ps.setInt(3, student.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
            return false;
        }
    }


    public boolean returnBook(int issueId) {

        String findIssue =
                "SELECT book_id FROM issued_books "
                        + "WHERE issue_id = ? AND status = 'ISSUED' FOR UPDATE";

        String updateIssue =
                "UPDATE issued_books SET status = 'RETURNED', "
                        + "return_date = ? WHERE issue_id = ? AND status = 'ISSUED'";

        String updateBook =
                "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int bookId;

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(findIssue)) {

                    ps.setInt(1, issueId);

                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println(
                                    "Active issue record not found!"
                            );
                            con.rollback();
                            return false;
                        }

                        bookId = rs.getInt("book_id");
                    }
                }

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(updateIssue)) {

                    ps.setDate(1, java.sql.Date.valueOf(
                            java.time.LocalDate.now()));
                    ps.setInt(2, issueId);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                try (java.sql.PreparedStatement ps =
                             con.prepareStatement(updateBook)) {

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

            } finally {
                con.setAutoCommit(true);
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Return error: " + e.getMessage());
            return false;
        }
    }

}
