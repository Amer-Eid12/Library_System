package library
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_STUDENT', 'ROLE_LIBRARIAN'])
class BookController  {

    def bookService
    def libraryService
    SpringSecurityService springSecurityService

    def index() {
        params.max = 10
        params.offset = params.int('offset') ?: 0

        def books = Book.list(max: params.max, offset: params.offset)
        //def apiBooks = bookService.loadGoogleApiBooks()
        def total = Book.count()

        [books: books, total: total]
    }

    def show(Long id) {
        def book = Book.get(id)
        if (!book) {
            flash.message = "Book not found"
            redirect(action: "index")
            return
        }
        [book: book]
    }


    def downloadFile(Long id) {
    def book = Book.get(id)
    if (!book || book.type != 'digital' || !book.file) {
        flash.message = "No file available for download."
        redirect(action: "show", id: id)
        return
    }
    response.setContentType("application/pdf")
    response.setHeader("Content-Disposition", "attachment; filename=\"${book.title?.replaceAll(/\s+/, '_') ?: 'book'}.pdf\"")
    response.outputStream << book.file
    response.outputStream.flush()
    }

    /*def show(String id) {
        def apiBooks = bookService.loadGoogleApiBooks()
        def index = 0
        for(int i=0; i<apiBooks.size(); i++){
            if(apiBooks.get(i).id == id){
                break
            }
            else{
                index++
            }
        }
        def book = apiBooks.get(index)
        if (!book) {
            flash.message = "Book not found"
            redirect(action: "index")
            return
        }
        [book: book]
    }*/

    def create() {
        def libraries = Library.list()
        render(view: 'create', model: [book: new Book(), libraries: libraries])
    }

    def availabilityFilter(){
        params.max = 10
        params.offset = params.int('offset') ?: 0

        def books = Book.executeQuery(
                "FROM Book b WHERE b.isAvailable = true",
                [],
                [max: params.max, offset: params.offset]
        )

        def total = Book.executeQuery(
                "SELECT count(b) FROM Book b WHERE b.isAvailable = true"
        )[0]

        render(view: 'index', model: [books: books, filtered: true, total: total])
    }


    def myBorrowedFilter() {
        params.max = 5
        params.offset = params.int('offset') ?: 0

        def loggedInUser = springSecurityService.currentUser
        Student currentStudent = Student.get(loggedInUser.id)

        def books = Book.executeQuery(
                "SELECT b FROM Book b WHERE b.borrowedBy = :studentId",
                [studentId: currentStudent],
                [max: params.max, offset: params.offset]
        )

        def total = Book.executeQuery(
                "SELECT count(b) FROM Book b WHERE b.borrowedBy = :studentId",
                [studentId: currentStudent]
        )[0]

        render(view: 'index', model: [books: books, filtered: true, total: total])
    }

    def physicalFilter(){
        params.max = 10
        params.offset = params.int('offset') ?: 0

        def books = Book.executeQuery(
                "FROM Book b WHERE b.type = 'physical'",
                [],
                [max: params.max, offset: params.offset]
        )

        def total = Book.executeQuery(
                "SELECT count(b) FROM Book b WHERE b.type = 'physical'"
        )[0]

        render(view: 'index', model: [books: books, filtered: true, total: total])
    }

    def digitalFilter(){
        params.max = 10
        params.offset = params.int('offset') ?: 0

        def books = Book.executeQuery(
                "FROM Book b WHERE b.type = 'digital'",
                [],
                [max: params.max, offset: params.offset]
        )

        def total = Book.executeQuery(
                "SELECT count(b) FROM Book b WHERE b.type = 'digital'"
        )[0]

        render(view: 'index', model: [books: books, filtered: true, total: total])
    }


    /*def availabilityFilter(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0

        def criteria= Book.createCriteria()
        def books=criteria.list(max: params.max, offset: params.offset) {
            order('author', 'asc')
        }
        def total = criteria.count()
        render(view: 'index', model: [books: books, filtered: true, total: total])
    }*/

    def edit(Long id){
        def book = Book.get(id)
        if (!book){
            flash.message="Book not found"
            redirect(action: "index")
            return
        }
        [book: book]
    }

    @Transactional
    def update(){
        def book=Book.get(params.id)
        if(!book){
            flash.message="Book not found"
            redirect(action: "index")
            return
        }

        book.title=params.title
        book.isbn =params.isbn
        book.author=params.author

        if (book.validate()){
            book.save(flush: true)
            flash.message = "Book updated successfully"
        }else{
            flash.message = "Validation failed"
        }
        redirect(action: "index")
    }

    @Transactional
    def delete(Long id){
        def book =Book.get(id)
        if (!book){
            flash.message="Book not found"
            redirect(actoin: "index")
            return
        }

        try {
            book.delete(flush: true)
            flash.message="Book deleted successfully"
        }catch(Exception e){
            flash.message = "Failed to delete book: ${e.message}"
        }
        redirect(action: "index")
    }

    @Transactional
    def save() {
        def book = new Book(params)

        def uploadedFile = request.getFile('file')
        if (book.type == 'digital' && uploadedFile && !uploadedFile.empty) {
            if (uploadedFile.contentType == 'application/pdf') {
                book.file = uploadedFile.bytes
            } else {
                book.errors.rejectValue('file', 'file.not.pdf', 'Only PDF files are allowed.')
            }
        }

        if (!book.validate()) {
            flash.message = "Please correct the errors below"
            render(view: "create", model: [book: book])
            return
        }

        if (Book.findByIsbn(book.isbn)) {
            flash.message = "A book with this ISBN already exists"
            render(view: "create", model: [book: book])
            return
        }

        book.isAvailable = true

        if (book.save(flush: true)) {
            flash.message = "Book created successfully"
            redirect(action: "show", id: book.id)
        } else {
            flash.message = "Failed to create book"
            render(view: "create", model: [book: book])
        }
    }

    def borrow(Long id) {
        def book = Book.get(id)
        if (!book) {
            flash.message = "Book not found"
            redirect(action: "index")
            return
        }

        def currentUser = springSecurityService.currentUser

        try {
            bookService.borrowBook(book, currentUser)
            flash.message = "Book borrowed successfully"
        } catch (Exception e) {
            flash.message = e.message
        }
        redirect(action: "show", id: id)
    }

    def returnBook(long id) {
        def book = Book.get(id)
        if (!book) {
            flash.message = "Book not found"
            redirect(action: "index")
            return
        }
        Student currentUser= springSecurityService.currentUser
        if(book.borrowedBy.id == currentUser.id){
            bookService.returnBook(book, currentUser)
            flash.message = "Book returned successfully"
            redirect(action: "show", id: id)
        }
        else{
            flash.message = "Book is not borrowed by you"
            redirect(action: "show", id: id)
        }
    }

    def findBookByTitle() {
        def title = params.title
        def book = libraryService.findBookByTitle(title)
        def total = Book.executeQuery(
                "SELECT count(b) FROM Book b WHERE b.title = :title",
                [title: title]
        )[0]

        if (book) {
            render(view: "index", model: [books: book, total: total, filtered: true])
        } else {
            flash.message = "Book not found"
            redirect(action: "index")
        }
    }

}
