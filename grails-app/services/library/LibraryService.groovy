package library

import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class LibraryService {

    def registerUser(User user) {
        if (user.validate()) {
            user.save(flush: true)
        } else {
            throw new IllegalArgumentException("User validation failed")
        }
    }

    def registerUser(Librarian user) {
        if (user.validate()) {
            user.save(flush: true)
        } else {
            throw new IllegalArgumentException("User validation failed")
        }
    }

    /*def findBookByTitle(String title) {
        def book = Book.executeQuery(
                "SELECT b FROM Book b WHERE b.title = :title",
                [title: title],
                [max: 10, offset: 0]
        )
        return book
    }*/

    def findBookByIsbn(String isbn) {
        return Book.findByIsbn(isbn)
    }
}
