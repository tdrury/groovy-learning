package lang

import spock.lang.Specification

import java.text.NumberFormat

import static spock.util.matcher.HamcrestMatchers.closeTo

class TypeConversionSpec extends Specification {

    def "string to int"() {

        given:
        def s = "3"

        when:
        def result = (s as int) + 1

        then:
        result == 4
    }

	def "string to int - bad"() {

		given:
		def s = "a3"

		when:
		(s as int) + 1

		then:
		thrown NumberFormatException
	}

	def "string to float"() {

		given:
		def s = "3.14"

		when:
		def result = (s as float) * 2

		then:
		result closeTo(6.28, 0.01)
	}

	def "int to string"() {

		given:
		def i = 2

		when:
		def result = i + "foo"

		then:
		result == "2foo"
	}

}
