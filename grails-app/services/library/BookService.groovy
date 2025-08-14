package library
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

@Transactional
class BookService {
    def grailsApplication

    def borrowBook(Book book, Student user) {
        if (!book.isAvailable) {
            throw new IllegalStateException("Book is already borrowed.")
        }

        if(user.bookBorrowed>=user.maxBooksAllowed){
            throw new IllegalStateException("Borrowing limit reached. Max allowed: $maxBooksAllowed")
        }

        book.borrowedBy = user
        user.bookBorrowed++
        book.isAvailable = false
        book.save(flush: true)
    }

    def returnBook(Book book, Student user) {
        book.isAvailable = true
        user.bookBorrowed--
        book.borrowedBy = null
        book.save(flush: true)
    }

    def loadGoogleApiBooks() {
        def apiKey = grailsApplication.config.my.google.books.apiKey
        def query = "default search"
        def url = "https://www.googleapis.com/books/v1/volumes?q=${URLEncoder.encode(query, 'UTF-8')}&maxResults=40&startIndex=0&key=${apiKey}"
        def json = new JsonSlurper().parse(new URL(url))

        json.items.each { item ->
            def isbn = item.volumeInfo?.industryIdentifiers?.find { it.type.startsWith("ISBN") }?.identifier
            if (isbn) {
                if (!Book.findByIsbn(isbn)) {
                    new Book(
                            title: item.volumeInfo?.title,
                            library: Library.findByName('library1'),
                            author: item.volumeInfo?.authors?.join(", "),
                            isbn: isbn,
                            isAvailable: true
                    ).save(flush: true, failOnError: true)
                }
            }
        }
    }


    def displayInfo(Book book) {
        return "Title: ${book.title}, Author: ${book.author}, ISBN: ${book.isbn}, Available: ${book.isAvailable}"
    }
}
