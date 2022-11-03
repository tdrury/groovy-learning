package collections

import spock.lang.Specification

class StringToMapSpec extends Specification {

	def "string to map"() {
		given:
		def s = "ARG1=FOO ARG2=BAR   ARG3=3 ARG4=BLAH_BLAH  NOVAL= "

		when:
		def m = [:]
		s.split().each {
			def kv = it.split('=')
			println "it=[${it}] kv=${kv}"
			if (kv.size() > 1) {
				m[kv[0]] = kv[1]
			}
		}

		then:
		m['ARG1'] == 'FOO'
		m.'ARG2' == 'BAR'
		m.'ARG3' == "3"
		m.'ARG4' == "BLAH_BLAH"
		m.'NOVAL' == null
	}
}
