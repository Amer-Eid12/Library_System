package library

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_LIBRARIAN'])
class LibrarianController {

    def index(){
        def librarians = Librarian.list()
        [librarians: librarians]
    }

    def show(Long id){
        def librarian=Librarian.get(id)
        if(!librarian){
            flash.message="Librarian not found"
            redirect(action: "index")
            return
        }
        [librarian: librarian]
    }

    def edit(Long id){
        def librarian=Librarian.get(id)
        if (!librarian){
            flash.message="Librarian not found"
            redirect(action: "index")
            return
        }
        [librarian: librarian]
    }

    @Transactional
    def delete(Long id){
        def librarian=Librarian.get(id)
        if(!librarian){
            flash.message="librarian not found"
            redirect(action: "index")
            return
        }
        try {
            librarian.delete(flush: true)
            flash.message="Librarian deleted successfully"
        }catch(Exception e){
            flash.message="Failed to delete librarian: ${e.message}"
        }
        redirect(action: "index")
    }

    @Transactional
    def update(){
        def librarian=Librarian.get(params.id)
        if(!librarian){
            flash.message="Librarian not found"
            redirect(action: "index")
            return
        }

        librarian.username=params.name
        if (librarian.validate()){
            librarian.save(flush: true)
            flash.message="Librarian updated successfully"
        }else{
            flash.message="Validation failed"
        }
        redirect(action: "index")
    }

}
