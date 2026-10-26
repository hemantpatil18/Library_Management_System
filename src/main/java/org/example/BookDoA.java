
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDoA{

    public void addBook(Book book) {

        String sql = "INSERT INTO books "
                + "(book_id, title, author, quantity) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, book.getBookId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getQuantity());

            ps.executeUpdate();

            System.out.println("Book added to MySQL successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void viewBooks() {

        String sql = "SELECT * FROM books";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing books: " + e.getMessage());
        }
    }

    public Book searchBook(int id) {

        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("quantity")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }

        return null;
    }

    public boolean updateBook(Book book) {

        String sql = "UPDATE books SET title = ?, author = ?, quantity = ? WHERE book_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getQuantity());
            ps.setInt(4, book.getBookId());

            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLException e) {
            System.out.println("Update error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBook(int id) {

        String sql = "DELETE FROM books WHERE book_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    public boolean checkAvailability(int id) {

        String sql = "SELECT quantity FROM books WHERE book_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (java.sql.ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("quantity") > 0;
                } else {
                    System.out.println("Book not found!");
                    return false;
                }
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Availability error: " + e.getMessage());
            return false;
        }
    }


}
