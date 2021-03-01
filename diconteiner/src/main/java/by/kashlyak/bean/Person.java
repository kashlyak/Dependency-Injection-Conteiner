package by.kashlyak.bean;

import by.kashlyak.annotation.Inject;

public class Person {
    Book book;
    @Inject

    public Person(Book book) {
        this.book = book;
    }

    public Person() {
    }
    public void reedBook() {
        book.bookText();
    }
}
