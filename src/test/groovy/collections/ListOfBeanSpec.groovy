package collections

import spock.lang.Specification

class ListOfBeanSpec extends Specification {

	def "create set from attribute of bean"() {
		given:
		def beans = [
				new POB(firstName: "Ned", lastName: "Flanders", age: 42),
				new POB(firstName: "Homer", lastName: "Simpson", age: 42),
				new POB(firstName: "Bart", lastName: "Simpson", age: 12),
				new POB(firstName: "Marge", lastName: "Simpson", age: 41),
				new POB(firstName: "Fred", lastName: "Flintstone", age: 40)
		]

		when:
		Set<String> lastNames = beans.collect { it.lastName }
		def sortedLastNames = lastNames.sort()

		then:
		lastNames.size() == 3
		lastNames.containsAll("Flanders", "Simpson", "Flintstone")
		sortedLastNames[0] == "Flanders"
		sortedLastNames[1] == "Flintstone"
		sortedLastNames[2] == "Simpson"
	}
}

class POB {
	def firstName
	def lastName
	def age
}
