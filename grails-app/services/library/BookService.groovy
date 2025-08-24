package library
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook

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
        List<Book> list = new ArrayList<Book>()

        json.items.each { item ->
            def isbn = item.volumeInfo?.industryIdentifiers?.find { it.type.startsWith("ISBN") }?.identifier
            if (isbn) {
                //if (!Book.findByIsbn(isbn)) {
                    list.add(new Book(
                            title: item.volumeInfo?.title,
                            library: Library.findByName('library1'),
                            author: item.volumeInfo?.authors?.join(", "),
                            isbn: isbn,
                            isAvailable: true
                    ))
                //}
            }
        }
        return list
    }

    def exportAllBooks(XSSFWorkbook workbook) {
        def sheet = workbook.createSheet("All Books")

        def header = sheet.createRow(0)
        header.createCell(0).setCellValue("Title")
        header.createCell(1).setCellValue("Author")
        header.createCell(2).setCellValue("ISBN")
        header.createCell(3).setCellValue("Is Available")

        Book.list().eachWithIndex { book, i ->
            def row = sheet.createRow(i + 1)
            row.createCell(0).setCellValue(book.title)
            row.createCell(1).setCellValue(book.author)
            row.createCell(2).setCellValue(book.isbn)
            row.createCell(3).setCellValue(book.isAvailable ? "Yes" : "No")
        }

        sheet.autoSizeColumn(0)
        sheet.autoSizeColumn(1)
        sheet.autoSizeColumn(2)
        sheet.autoSizeColumn(3)
    }

    def exportAvailableBooks(XSSFWorkbook workbook) {
        def results = Book.createCriteria().list {
            eq("isAvailable", true)
        }

        def sheet = workbook.createSheet("Available Books")

        def header = sheet.createRow(0)
        header.createCell(0).setCellValue("Title")
        header.createCell(1).setCellValue("Author")
        header.createCell(2).setCellValue("ISBN")
        header.createCell(3).setCellValue("Is Available")

        results.eachWithIndex { book, i ->
            def row = sheet.createRow(i + 1)
            row.createCell(0).setCellValue(book.title)
            row.createCell(1).setCellValue(book.author)
            row.createCell(2).setCellValue(book.isbn)
            row.createCell(3).setCellValue(book.isAvailable ? "Yes" : "No")
        }

        sheet.autoSizeColumn(0)
        sheet.autoSizeColumn(1)
        sheet.autoSizeColumn(2)
        sheet.autoSizeColumn(3)
    }

    def exportBorrowedBooks(XSSFWorkbook workbook) {
        def results = Book.createCriteria().list {
            eq("isAvailable", false)
        }

        def sheet = workbook.createSheet("Borrowed Books")

        def header = sheet.createRow(0)
        header.createCell(0).setCellValue("Title")
        header.createCell(1).setCellValue("Author")
        header.createCell(2).setCellValue("ISBN")
        header.createCell(3).setCellValue("Is Available")

        results.eachWithIndex { book, i ->
            def row = sheet.createRow(i + 1)
            row.createCell(0).setCellValue(book.title)
            row.createCell(1).setCellValue(book.author)
            row.createCell(2).setCellValue(book.isbn)
            row.createCell(3).setCellValue(book.isAvailable ? "Yes" : "No")
        }

        sheet.autoSizeColumn(0)
        sheet.autoSizeColumn(1)
        sheet.autoSizeColumn(2)
        sheet.autoSizeColumn(3)

    }

    def generateFullWorkbook() {
        def workbook = new XSSFWorkbook()

        exportAllBooks(workbook)
        exportAvailableBooks(workbook)
        exportBorrowedBooks(workbook)

        return workbook
    }

}
