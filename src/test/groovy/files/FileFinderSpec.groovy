package files

import spock.lang.Specification

class FileFinderSpec extends Specification {

	final TEST_DIR = "src/test"

	def "find directories at any level starting with 'c'"() {

		when:
		def rootDir = new File(TEST_DIR)
		def dirs = []
		rootDir.eachDirRecurse {
			if (it.name.startsWith("c")) {
				// canonical path will be full path to directory, but we want the path relative to the root
				// of TEST_DIR, so remove the leading dirs to TEST_DIR and prepend TEST_DIR to path.
				dirs << TEST_DIR + (it.canonicalPath - rootDir.canonicalPath)
			}
		}
		//println "dirs=${dirs}"

		then:
		dirs.containsAll(['src/test/groovy/cli', 'src/test/groovy/classloader', 'src/test/groovy/closures', 'src/test/groovy/collections'])
	}

	def "canonical path"() {
		when:
		def rootDir = new File('.')
		def sourceMainGroovyDir = new File(rootDir, "src/test/groovy")

		then:
		rootDir.exists()
		sourceMainGroovyDir.exists()
		sourceMainGroovyDir.canonicalPath - rootDir.canonicalPath == "/src/test/groovy"
	}

	def "File paths"() {
		when:
		def rootDir = new File('.')
		def sourceMainGroovyDir = new File(rootDir, "src/test/groovy")
		def thisTest = new File("src/test/groovy/files", "FileFinderSpec.groovy")

		println "thisTest=[${thisTest}]"
		println "   toString=[${thisTest.toString()}]"
		println "   canonical file=[${thisTest.canonicalFile}]"
		println "   canonical path=[${thisTest.canonicalPath}]"
		println "   absolute file=[${thisTest.absoluteFile}]"
		println "   absolute path=[${thisTest.absolutePath}]"

		then:
		rootDir.exists()
		sourceMainGroovyDir.exists()
		sourceMainGroovyDir.canonicalPath - rootDir.canonicalPath == "/src/test/groovy"
	}

	def "File no arg"() {
		when:
		File.
		def f = new File()
		println "f=[${f}]"

		then:
		f.exists()
	}
}
