package library

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_LIBRARIAN'])
class StudentController {

    StudentService studentService

    def index(){
        def students = Student.list()
        [students: students]
    }

    def show(Long id){
        def student=Student.get(id)
        if(!student){
            flash.message="Student not found"
            redirect(action: "index")
            return
        }
        [student: student]
    }

    @Transactional
    def delete(Long id){
        def student=Student.get(id)
        if (!student){
            flash.messsage="Student not found"
            redirect(action: "index")
            return
        }

        try {
            student.delete(flush: true)
            flash.message="Student deleted successfully"
        }catch(Exception e){
            flash.message="Failed to delete student: ${e.message}"
        }
        redirect(action: "index")
    }

    def edit(Long id) {
        def student = Student.get(id)
        if (!student) {
            flash.message = "Student not found"
            redirect(action: "index")
            return
        }
        [student: student]
    }

    @Transactional
    def update() {
        def student = Student.get(params.id)
        if (!student) {
            flash.message = "Student not found"
            redirect(action: "index")
            return
        }

        student.username = params.username

        if (student.validate()) {
            student.save(flush: true)
            flash.message = "Student updated successfully"
        } else {
            flash.message = "Validation failed"
        }
        redirect(action: "index")
    }

    def profileImage(Long id){
        def student = Student.get(id)
        if(!student || !student.profileImage){
            render status: 404
            return
        }

        response.contentType= "image/jpeg"
        response.outputStream << student.profileImage
        response.outputStream.flush()
    }
}
