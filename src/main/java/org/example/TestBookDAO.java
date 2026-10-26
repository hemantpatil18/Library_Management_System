
package org.example;

import java.util.Scanner;

public class TestBookDAO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookDoA dao = new BookDoA();

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();

        Book book = new Book(id, title, author, quantity);

        dao.addBook(book);

        sc.close();
    }
}

