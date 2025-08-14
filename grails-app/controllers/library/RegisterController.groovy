package library

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

class RegisterController {

    def index(){
        render(view: "index")
    }

    def registerStudent(){
        def libraries = Library.list()
        render (view: 'registerStudent', model: [libraries: libraries])
    }

    def registerLibrarian(){
        def libraries = Library.list()
        render (view: 'registerLibrarian', model: [libraries: libraries])
    }

    @Transactional
    def saveStudent(){
        if(request.method!= "POST"){
            def libraries=Library.list()
            render(view: "/register/registerStudent", model: [libraries: libraries])
            return
        }

        def libraries = Library.list()
        def selectedLibrary = Library.get(params.long('library.id'))
        MultipartFile imageFile= request.getFile('imageFile')

        def student = new Student(
                username: params.username,
                password: params.password,
                profileImage: imageFile.bytes,
                profileImageType: imageFile.contentType,
                library: selectedLibrary
        )

        if (params.password != params.confirmPassword) {
            flash.message = "Passwords do not match"
            render(view: "/register/registerStudent", model: [student: student, libraries: libraries])
            return
        }

        if(!student.validate()){
            student.errors.allErrors.each{
                println "Validation error: ${it}"
            }
            flash.message = "Validation failed"
            render(view: "/register/registerStudent", model: [student: student, libraries: libraries])
            return
        }

        try{
            student.save(flush: true, failOnError: true)

            def role = Role.findByAuthority('ROLE_STUDENT')
            UserRole.create(student, role, true)

            flash.message = "Registration successful. You can now log in."
            redirect uri: "/login/auth"

        } catch (IllegalArgumentException e){
            flash.message = "Registration failed: ${e.message}"
            render(view: "/register/registerStudent", model: [student: student, libraries: libraries])
        }
    }

    @Transactional
    def saveLibrarian(){
        if (request.method != "POST") {
            def libraries = Library.list()
            render(view: "/register/registerLibrarian", model: [libraries: libraries])
            return
        }

        def libraries = Library.list()
        def selectedLibrary = Library.get(params.long('library.id'))
        MultipartFile imageFile= request.getFile('imageFile')

        def librarian = new Librarian(
                username: params.username,
                password: params.password,
                profileImage: imageFile.bytes,
                profileImageType: imageFile.contentType,
                library: selectedLibrary
        )

        if (params.password != params.confirmPassword) {
            flash.message = "Passwords do not match"
            render(view: "/register/registerLibrarian", model: [librarian: librarian, libraries: libraries])
            return
        }

        if (!librarian.validate()) {
            librarian.errors.allErrors.each {
                println "Validation error: ${it}"
            }
            flash.message = "Validation failed"
            render(view: "/register/registerLibrarian", model: [librarian: librarian, libraries: libraries])
            return
        }

        try {
            librarian.save(flush: true, failOnError: true)

            def role = Role.findByAuthority('ROLE_LIBRARIAN')
            UserRole.create(librarian, role, true)

            flash.message = "Registration successful. You can now log in."
            redirect(uri: "/login/auth")

        } catch (IllegalArgumentException e) {
            flash.message = "Registration failed: ${e.message}"
            render(view: "/register/registerLibrarian", model: [librarian: librarian, libraries: libraries])
        }
    }

}
