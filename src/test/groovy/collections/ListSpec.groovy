package collections

import spock.lang.Specification

class ListSpec extends Specification {

	def "flatten list of strings into comma-delimited list"() {
		given:
		def list = ['apple','banana','peach']

		expect:
		list.join(',') == 'apple,banana,peach'
	}
}
