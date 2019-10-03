package files

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class PathSpec extends Specification {

	def "relative directory"() {

		when:
		def rootDir = new File(".")
		def srcDir = new File(rootDir, "src/main/groovy")
		def rootPath = Paths.get(rootDir.toURI())
		println "rootPath=${rootPath}, root=${rootPath.root}, parent=${rootPath.parent}"
		def srcPath = Paths.get(srcDir.toURI())
		println "relative: ${srcPath.relativize(rootPath)}"
		println "relative: ${rootPath.relativize(srcPath)}"

		then:
		rootPath != null
		rootPath.relativize(srcPath).toString() == "src/main/groovy"
		srcPath.relativize(rootPath).toString() == "../../.."
	}
}
