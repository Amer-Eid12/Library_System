package library

class LoadGoogleAPIBooksJob {

    BookService bookService

    static triggers = {
        cron name: 'loadGoogleBooksAPITrigger', cronExpression: "0 0 12 * * ?"
    }

    def execute(){
        println("Loading Google API Books...")
        bookService.loadGoogleApiBooks()
    }

}
