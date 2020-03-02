package regex

import spock.lang.Specification

class VersionRegexSpec extends Specification {

	def "parse 3 component version"() {
		expect:
		parseVersion("3.4.15") == ['3', '4', '15']
		parseVersion("3.4.15-SNAPSHOT") == ['3', '4', '15', 'SNAPSHOT']
	}

	def "parse 1 component versions"() {
		expect:
		parseVersion("3") == ['3']
		parseVersion("3-SNAPSHOT") == ['3', 'SNAPSHOT']
	}

	def "parse 2 component versions"() {
		expect:
		parseVersion("3.14") == ['3', '14']
		parseVersion("0.99") == ['0', '99']
		parseVersion("3.14-SNAPSHOT") == ['3', '14', 'SNAPSHOT']
		parseVersion("0.99-SNAPSHOT") == ['0', '99', 'SNAPSHOT']
	}

	def "parse 4 component versions"() {
		expect:
		parseVersion("9.1.2.3") == ['9', '1', '2', '3']
		parseVersion("9.1.2.3-SNAPSHOT") == ['9', '1', '2', '3', 'SNAPSHOT']
	}

	def "parse build number versions"() {
		expect:
		parseVersion("3.4.5-20190415201900+22a641a6064dcd33f1b1c4fc6221a71518af97ea") == ['3', '4', '5', '20190415201900', '22a641a6064dcd33f1b1c4fc6221a71518af97ea']
	}

	def parseVersion(String version) {
		def versionPattern = /(\d+)(.(\d+))?(.(\d+))?(.(\d+))?(-(\d+|SNAPSHOT))?([+_]([0-9abcdef]+))?/
		def matcher = (version =~ versionPattern)
//		println "matcher=${matcher}, hasGroup=${matcher.hasGroup()}, count=${matcher.size()}, matcher[0].size=${matcher[0].size()}"
//		if (matcher.hasGroup()) {
//			for (int i = 0; i < matcher.size(); i++) {
//				println "${i}: ${matcher[i]}"
//			}
//		}

		// drop first component which matches everything
		def components = matcher[0].drop(1)
		// drop components that start with '.'
		components.removeAll { (it == null) || it.startsWith('.') || it.startsWith('-') || it.startsWith('+') || it.startsWith('_') }

//		println "components=${components}"
		return components
	}
}
