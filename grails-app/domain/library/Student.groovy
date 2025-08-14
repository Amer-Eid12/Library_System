package library

//import grails.rest.Resource

//@Resource(uri='/api/students', formats=['json', 'xml'])
class Student extends User {

    int maxBooksAllowed = 3
    int bookBorrowed = 0

    static constraints = {
        maxBooksAllowed nullable: false, max: 3
        bookBorrowed nullable: false, min: 0
    }

    /*@Override
    void borrow(Book book) {
        if (bookBorrowed >= maxBooksAllowed) {
            throw new IllegalStateException("Borrowing limit reached. Max allowed: $maxBooksAllowed")
        }

        if (!book.isAvailable) {
            throw new IllegalStateException("Book is not available")
        }

        bookBorrowed++
        book.isAvailable = false
        book.save(flush: true)
    }

    @Override
    void returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            throw new IllegalArgumentException("This book is not borrowed by this student")
        }

        bookBorrowed--
        book.isAvailable = true
        book.save(flush: true)
    }*/
}
