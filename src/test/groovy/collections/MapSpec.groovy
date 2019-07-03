package collections

import spock.lang.Specification

class MapSpec extends Specification {

	def "spread operator on map"() {
		given:
		def map = [a: 1, b: 2, c: 3]

		when:
		def keys = map*.key

		then:
		keys == ['a', 'b', 'c']
	}

	def "spread operator and sort on map"() {
		given:
		def map = [z: 1, x: 2, y: 3]

		when:
		def keys = map*.key
		def sortedKeys = map.sort()*.key

		then:
		keys == ['z', 'x', 'y']
		sortedKeys == ['x', 'y', 'z']
	}

}
