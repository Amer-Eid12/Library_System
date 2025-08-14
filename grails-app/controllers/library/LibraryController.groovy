package library

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_LIBRARIAN'])
class LibraryController {

    LibraryService libraryService

    def index() {
        render(view: "index")
    }

    def createLibrary() {
        render(view: "createLibrary", model: [library: new Library()])
    }

    @Transactional
    def saveLibrary() {
        def library = new Library(params)
        if (library.validate()) {
            library.save(flush: true)
            flash.message = "Library created successfully"
            redirect(action: "index")
        } else {
            flash.message = "Failed to create library"
            render(view: "createLibrary", model: [library: library])
        }
    }




    def findBookByIsbn() {
        def isbn = params.isbn
        if (!isbn) {
            render "ISBN must be provided"
            return
        }

        def book = libraryService.findBookByIsbn(isbn)
        if (book) {
            render(view: "showBook", model: [book: book])
        } else {
            render "Book not found"
        }
    }

    /*def registerStudent() {
        if (request.method != "POST") {
            def libraries = Library.list()
            render(view: "/register/registerStudent", model: [libraries: libraries])
            return
        }

        def student = new Student(params)
        def libraries = Library.list()

        if (!student.validate()) {
            flash.message = "Validation failed"
            render(view: "/register/registerStudent", model: [student: student, libraries: libraries])
            return
        }

        try {
            libraryService.registerUser(student)
            flash.message = "Student registered successfully"
            redirect(action: "index")
        } catch (IllegalArgumentException e) {
            flash.message = "Registration failed: ${e.message}"
            render(view: "/register/registerStudent", model: [student: student, libraries: libraries])
        }
    }


    def registerLibrarian(){
        if(request.method != "POST") {
            def libraries = Library.list()
            render(view: "/register/registerLibrarian", model: [libraries: libraries])
            return
        }
        def librarian = new Librarian(params)
        def libraries= Library.list()

        if(!librarian.validate()){
            flash.message= "Validatoin failed"
            render(view: "/register/registerLibrarian", model: [librarian: librarian, libraries: libraries])
            return
        }
        try {
            libraryService.registerUser(librarian)
            flash.message = "Librarian registered successfully"
            redirect(action: "index")
        }catch (IllegalArgumentException e){
            flash.message="Registration failed: ${e.message}"
            render(view: "/register/registerLibrarian", model: [librarian: librarian, libraries: libraries])
        }
    }*/
}
