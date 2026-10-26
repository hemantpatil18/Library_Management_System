
package org.example;

public class Student {

    private int studentId;
    private String name;
    private String contact;

    public Student(int studentId, String name, String contact) {
        this.studentId = studentId;
        this.name = name;
        this.contact = contact;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void displayStudent() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("----------------------");
    }
}
