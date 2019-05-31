package scripts

import groovy.transform.SourceURI
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class ScriptsSpec extends Specification {

	@SourceURI
	URI scriptSource

	def "get source directory of script"() {

		when:
		Path scriptPath = Paths.get(scriptSource)
		Path scriptsDir = scriptPath.parent

		then:
		scriptsDir.toString().contains("src/test/groovy/scripts")
	}
}
