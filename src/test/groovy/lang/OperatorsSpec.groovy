package lang

import spock.lang.Specification

class OperatorsSpec extends Specification {

	def "Elvis operator - normal usage"() {
		given:
		def normal = "Hello"
		def empty = ""
		def undefined = null

		expect:
		(normal ?: "Bye") == "Hello"
		(empty ?: "Not Empty") == "Not Empty"
		(undefined ?: "Defined") == "Defined"
	}

	def "Elvis operator with integer types"() {
		given:
		def ten = 10
		def one = 1
		def zero = 0

		expect:
		(ten ?: 99) == 10
		(one ?: 99) == 1
		(zero ?: 99) != 0 // this is weird and probably not intended
		(zero ?: 99) == 99 // it occurs because groovy "truth" says 0 is false.
	}
}
