package collections

import spock.lang.Shared
import spock.lang.Specification

class ListOfBeanSpec extends Specification {

	@Shared
	List<Person> people

	def setupSpec() {
		people = [
			new Person(firstName: "Ned", lastName: "Flanders", age: 42),
			new Person(firstName: "Homer", lastName: "Simpson", age: 42),
			new Person(firstName: "Bart", lastName: "Simpson", age: 12),
			new Person(firstName: "Marge", lastName: "Simpson", age: 41),
			new Person(firstName: "Fred", lastName: "Flintstone", age: 40)
		]
	}

	def "create set from attribute of bean"() {
		when:
		Set<String> lastNames = people.collect { it.lastName }
		def sortedLastNames = lastNames.sort()

		then:
		lastNames.size() == 3
		lastNames.containsAll("Flanders", "Simpson", "Flintstone")
		sortedLastNames[0] == "Flanders"
		sortedLastNames[1] == "Flintstone"
		sortedLastNames[2] == "Simpson"
	}

	def "sort list of beans by bean attribute"() {
		when:
		def sortedByAge =      people.sort { it.age }
		def sortedAgesAsList = people.sort { it.age }.collect { it.age }
		def sortedAgesAsSet =  people.sort { it.age }.collect { it.age } as Set

		then:
		sortedByAge[0].age == 12
		sortedByAge[1].age == 40
		sortedByAge[2].age == 41
		sortedByAge[3].age == 42
		sortedByAge[4].age == 42
		sortedAgesAsList == [ 12, 40, 41, 42, 42 ]
		sortedAgesAsSet == [ 12, 40, 41, 42 ] as Set
	}
}

class Person {
	String firstName
	String lastName
	int age
}
