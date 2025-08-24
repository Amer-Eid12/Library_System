package library

import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_STUDENT'])
class BookApiController extends RestfulController<Book> {
    static responseFormats = ['json', 'xml']

    BookService bookService

    BookApiController() {
        super(Book)
    }

    def loadGoogleApiBooks(){
        def list = bookService.loadGoogleApiBooks()
        respond list, formats: ['json']
    }

}
