package lang

import spock.lang.Specification

class CallingMethodSpec extends Specification {

    def "call method with named args"() {

        when:
        def b = Book.build([title: "The Title", author: "Author Dude"])

        then:
        b.title == "The Title"
        b.author == "Author Dude"
    }

    def "call method with named args but skipping one"() {
        when:
        def b = Book.build([title: "The Title"])

        then:
        b.title == "The Title"
        b.author == null
    }
}

class Book {

    String title
    String author

    def static build(Map params = [:]) {
        return new Book(params)
    }
}
