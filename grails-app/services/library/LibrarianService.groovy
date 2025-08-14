package library

class LibrarianService {

    def addBook(Book book) {
        if (Book.findByIsbn(book.isbn)) {
            throw new IllegalStateException("This book is already in the library")
        }
        book.save(flush: true)
    }

    def removeBook(Book book) {
        if (!Book.exists(book.id)) {
            throw new IllegalArgumentException("This book does not exist in the library")
        }
        book.delete(flush: true)
    }

    def listAllBooks() {
        return Book.list()
    }
}
