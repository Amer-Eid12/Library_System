package library

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_STUDENT'])
class BookApiController extends RestfulController<Book> {
    static responseFormats = ['json', 'xml']

    BookService bookService
    APIRateLimitService apiRateLimitService
    def springSecurityService

    BookApiController() {
        super(Book)
    }

    def loadGoogleApiBooks(){
        def list = bookService.loadGoogleApiBooks()
        respond list, formats: ['json']
    }

    def getBooks(){
        String userId = springSecurityService.currentUser?.id?.toString() ?: "anonymous"
        if(apiRateLimitService?.allowRequest(userId)) {
            def books = Book.list()
            respond books
        }else{
            render(status: 429, text: "Rate limit exceeded") as JSON
        }

    }

    def limits(){
        String userId = springSecurityService.currentUser?.id?.toString() ?: "anonymous"
        render apiRateLimitService.remaining(userId) as JSON
    }

}