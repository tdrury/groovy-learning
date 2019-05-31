package classloader

import groovy.transform.SourceURI
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class ClassloaderSpec extends Specification {

	@SourceURI
	URI scriptSource

	def "load class"() {

		given:
		Path scriptPath = Paths.get(scriptSource)
		Path thisScriptsDir = scriptPath.parent
		Path srcTestDir = thisScriptsDir.parent.parent
		File srcTestResourcesDir = new File(srcTestDir.toFile(), "resources")

		when:
		// load and compile Github class
		File sourceFile = new File(srcTestResourcesDir, "Hello.groovy")
		Class helloClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile)

		// create github delegate
		def hello = helloClass.newInstance()

		then:
		hello.sayHi("George") == "Hi, George"
	}
}
