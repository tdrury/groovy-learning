package regex

import spock.lang.Specification

class MultiLineRegexSpec extends Specification {

	def "match 1 line"() {

		expect:
		MULTILINE_TEXT ==~ /(?ms).*"version":\s*"1\.0\.0".*/
	}

	def "extract text"() {
		when:
		def matcher = YAML_MULTILINE_TEXT =~ /(?ms).*version:\s*(.*?)$/

		then:
		matcher[0][1] == '7.0.0-20200408235118+b5c9e83eef3f923b8157e95b2485f9677933bb76'
	}

	final def YAML_MULTILINE_TEXT = '''
---
_schema-version: '3.1'
ID: groovy-learning-ms
version: 7.0.0-20200408235118+b5c9e83eef3f923b8157e95b2485f9677933bb76
modules:
- name: groovy-ms
  type: java.tomcat
  path: groovy-ms.war
  parameters:
    host: "${org}-${space}-groovy-ms"
    buildpack: java_buildpack
    memory: 1G
    health-check-timeout: 600
  properties:
    DT_CLUSTER_ID: groovy-ms
    SESSION_TIMEOUT: 30
    SPRING_PROFILES_ACTIVE: cloud,postgres
    JBP_CONFIG_RESOURCE_CONFIGURATION: '["tomcat/conf/server.xml": {"connector.maxHttpHeaderSize":131072}]'
'''


	final def MULTILINE_TEXT = '''
{
    "name": "foo.ui",
    "version": "1.0.0",
    "engines": {
        "node": ">= 6.0.0",
        "npm": ">= 3.0.0"
    },
    "devDependencies": {
        "qunit": "^2.9.2"
    },
    "scripts": {
        "lint": "grunt lint",
    }
}
'''

}
