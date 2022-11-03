package files

import spock.lang.Specification

class TraverseOnSpec extends Specification {

	def "use traverseOn"() {

		File rootDir = new File("src/test/groovy")
		rootDir.traverse(type: groovy.io.FileType.FILES, nameFilter: ~/.*Spec\.groovy/) { file ->
			println "file=${file}"
		}
	}
}
