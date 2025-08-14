package library

abstract class User implements Serializable {

    String username
    String password
    boolean enabled=true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    byte[] profileImage
    String profileImageType

    static belongsTo = [library: Library]
    //static hasMany = [authorities: Role]

    static constraints = {
        username blank: false, unique: true
        password blank: false
        profileImage nullable: true, maxSize: 10 * 1024 * 1024
        profileImageType nullable: true
    }

    static mapping = {
        id generator: 'increment'
        table name: 'users'
        password column: '`password`'
    }

    //abstract void borrow(Book book)
    //abstract void returnBook(Book book)


    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this)*.role as Set<Role>
    }

}
