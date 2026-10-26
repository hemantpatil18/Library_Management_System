
package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Check Book Availability");
            System.out.println("7. Add Student");
            System.out.println("8. View Students");
            System.out.println("9. Search Student");
            System.out.println("10. Update Student");
            System.out.println("11. Delete Student");
            System.out.println("12. Issue Book");
            System.out.println("13. Return Book");
            System.out.println("14. Track Issued Books");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    checkAvailability();
                    break;
                case 7:
                    addStudent();
                    break;

                case 8:
                    StudentDAO studentDAO = new StudentDAO();
                    studentDAO.viewStudents();
                    break;
                case 9:
                    searchStudent();
                    break;

                case 10:
                    updateStudent();
                    break;

                case 11:
                    deleteStudent();
                    break;
                case 12:
                    issueBook();
                    break;
                case 13:
                    returnBook();
                    break;
                case 14:
                    IssueBookDAO dao = new IssueBookDAO();
                    dao.viewIssuedBooks();
                    break;

                case 15:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 15);
    }
    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        if (id <= 0 || quantity < 0
                || title.isBlank() || author.isBlank()) {
            System.out.println("Invalid book details!");
            return;
        }

        Book book = new Book(id, title, author, quantity);

        BookDoA dao = new BookDoA();
        dao.addBook(book);
    }


    static void viewBooks() {
        BookDoA dao = new BookDoA();
        dao.viewBooks();
    }


    static void searchBook() {
        System.out.print("Enter Book ID to search: ");
        int id = sc.nextInt();
        sc.nextLine();

        BookDoA dao = new BookDoA();
        Book book = dao.searchBook(id);

        if (book != null) {
            System.out.println("Book Found!");
            book.displayBook();
        } else {
            System.out.println("Book not found!");
        }
    }



    static void updateBook() {

        System.out.print("Enter Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        BookDoA dao = new BookDoA();
        Book book = dao.searchBook(id);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.print("Enter new title: ");
        String title = sc.nextLine();

        System.out.print("Enter new author: ");
        String author = sc.nextLine();

        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        if (title.isBlank() || author.isBlank() || quantity < 0) {
            System.out.println("Invalid book details!");
            return;
        }

        Book updatedBook = new Book(id, title, author, quantity);

        if (dao.updateBook(updatedBook)) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book update failed!");
        }
    }



    static void deleteBook() {

        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        BookDoA dao = new BookDoA();

        if (dao.deleteBook(id)) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found or delete failed!");
        }
    }




    static void checkAvailability() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        BookDoA dao = new BookDoA();
        Book book = dao.searchBook(id);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (book.getQuantity() > 0) {
            System.out.println("Book is available.");
            System.out.println("Available quantity: "
                    + book.getQuantity());
        } else {
            System.out.println("Book is currently unavailable.");
        }
    }


    static Book findBook(int id) {
        for (Book book : books) {
            if (book.getBookId() == id) {
                return book;
            }
        }
        return null;
    }

    static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        if (id <= 0 || name.isBlank() || contact.isBlank()) {
            System.out.println("Invalid student details!");
            return;
        }

        Student student = new Student(id, name, contact);

        StudentDAO dao = new StudentDAO();
        dao.addStudent(student);
    }

    static void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        StudentDAO dao = new StudentDAO();
        Student student = dao.searchStudent(id);

        if (student != null) {
            student.displayStudent();
        } else {
            System.out.println("Student not found!");
        }
    }

    static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        StudentDAO dao = new StudentDAO();
        Student student = dao.searchStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new contact: ");
        String contact = sc.nextLine();

        if (name.isBlank() || contact.isBlank()) {
            System.out.println("Invalid student details!");
            return;
        }

        Student updated = new Student(id, name, contact);

        if (dao.updateStudent(updated)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student update failed!");
        }
    }

    static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        StudentDAO dao = new StudentDAO();

        if (dao.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found or delete failed!");
        }
    }

    static void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        IssueBookDAO dao = new IssueBookDAO();
        dao.issueBook(bookId, studentId);
    }

    static void returnBook() {

        System.out.print("Enter Issue ID to return: ");
        int issueId = sc.nextInt();
        sc.nextLine();

        IssueBookDAO dao = new IssueBookDAO();
        dao.returnBook(issueId);
    }


}
