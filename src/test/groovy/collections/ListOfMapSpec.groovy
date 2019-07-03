package collections

import spock.lang.Specification

class ListOfMapSpec extends Specification {

	def "access map key and value from inside list"() {

		when:
		def args = [ [ foo: 'Foo~Value-1' ],
		             [ bar: 'Bar~Value-2' ]
		]
		def entry = args[0].entrySet()[0]

		then:
		entry.key == 'foo'
		entry.value == 'Foo~Value-1'
	}

	def "access map key and value from inside list with null safety"() {

		when:
		def args = [ [ foo: 'Foo~Value-1' ],
		             [ bar: 'Bar~Value-2' ]
		]
		def entry = args?.getAt(0)?.entrySet()?.getAt(0)
		def argsListElement99 = args?.getAt(99)?.entrySet()?.getAt(0)
		def argsListElement0MapEntry1 = args?.getAt(0)?.entrySet()?.getAt(1)

		then:
		entry.key == 'foo'
		entry.value == 'Foo~Value-1'
		argsListElement99 == null
		argsListElement0MapEntry1 == null
	}

	def "access map key and value from inside empty list"() {

		when:
		def args = [ ]
		def entry = args?.getAt(0)?.entrySet()?.getAt(0)

		then:
		entry == null
	}

}
