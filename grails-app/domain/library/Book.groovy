package library

//import grails.rest.Resource

//@Resource(uri='/api/books', formats=['json', 'xml'])
class Book {

    String title
    String isbn
    String author
    boolean isAvailable
    String type
    byte[] file


    static belongsTo= [library: Library, borrowedBy: Student]

    static constraints = {
        title blank: false
        /*isbn blank: false, unique: true, validator: { val, obj ->
            if (!val) return "invalid.isbn13.format"

            def isbn13Pattern = /^\d{3}-\d{2}-\d{5}-\d{2}-\d$/

            if (!val.matches(isbn13Pattern)) {
                return "invalid.isbn13.format"
            }
        }*/
        author blank: false
        isAvailable default:true
        borrowedBy nullable: true
        type inList: ['physical', 'digital'], blank: false
        file nullable: true, maxSize: 10 * 1024 * 1024, validator: { val, obj ->
            if (obj.type == 'digital' && !val) {
                return 'file.required.for.digital.books'
            }
            if (obj.type == 'physical' && val) {
                return 'file.not.allowed.for.physical.books'
            }
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn=" + isbn +
                ", author='" + author + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    static mapping = {
        file sqlType: 'LONGBLOB'
    }
}