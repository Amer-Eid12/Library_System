package library

//import grails.rest.Resource

//@Resource(uri='/api/libraries', formats=['json', 'xml'])
class Library {

    String name

    static hasMany = [books: Book, users: User]


}
