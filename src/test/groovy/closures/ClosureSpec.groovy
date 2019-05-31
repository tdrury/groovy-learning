package closures

import spock.lang.Specification

class ClosureSpec extends Specification {

	def "simple closure"() {

		def yellName = { name ->
			name.toUpperCase()
		}

		expect:
		yellName.call("Fred") == "FRED"
	}

	def "simple closure with typed arg"() {

		def yellName = { String name ->
			name.toUpperCase()
		}

		expect:
		yellName.call("Fred") == "FRED"
	}

	def "simple closure called with wrong type"() {

		given:
		def yellName = { name ->
			name.toUpperCase()
		}

		when:
		// if closure arg was typed as String, then IDE would flag the following line
		yellName(1)

		then:
		thrown(MissingMethodException)
	}

	def "simple closure via Closure"() {

		Closure yellName = { name ->
			name.toUpperCase()
		}

		expect:
		yellName("George") == "GEORGE"
	}

	def "closure using operations that work on different types"() {

		Closure dup = { s ->
			s + s
		}

		expect:
		dup("Harry") == "HarryHarry"
		dup(1) == 2
	}
}

