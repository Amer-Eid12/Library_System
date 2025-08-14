package library

import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_STUDENT'])
class BookApiController extends RestfulController<Book> {
    static responseFormats = ['json', 'xml']

    BookApiController() {
        super(Book)
    }

}
