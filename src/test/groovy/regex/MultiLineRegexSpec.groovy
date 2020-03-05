package regex

import spock.lang.Specification

class MultiLineRegexSpec extends Specification {

	def "match 1 line"() {

		expect:
		MULTILINE_TEXT ==~ /(?ms).*"version":\s*"1\.0\.0".*/
	}


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
