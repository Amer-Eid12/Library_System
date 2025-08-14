package library

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

class BootStrap {
    def grailsApplication


    def init = { servletContext ->
        //initializeData()
    }

    @Transactional
    def initializeData() {
        def apiKey = grailsApplication.config.my.google.books.apiKey
        def query = "default search"
        def url = "https://www.googleapis.com/books/v1/volumes?q=${URLEncoder.encode(query, 'UTF-8')}&maxResults=40&startIndex=${0}&key=${apiKey}"
        def json = new JsonSlurper().parse(new URL(url))
        json.items.collect{ item ->
            if(item.volumeInfo?.industryIdentifiers?.find { it.type.startsWith("ISBN") }?.identifier != null){
            new Book(
                   // id: item.id,
                    title: item.volumeInfo?.title,
                    library: Library.findByName('library1'),
                    author: item.volumeInfo?.authors?.join(", "),
                    isbn: item.volumeInfo?.industryIdentifiers?.find { it.type.startsWith("ISBN") }?.identifier,
                    isAvailable: true
            ).save(flush: true, failOnError: true)
        }
        }
    }

    def destroy = {
        // Cleanup logic if needed
    }
}
