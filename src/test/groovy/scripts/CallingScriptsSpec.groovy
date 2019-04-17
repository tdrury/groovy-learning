package scripts

import spock.lang.Specification

class CallingScriptsSpec extends Specification {

    GroovyShell shell

    def setup() {
        shell = new GroovyShell()
    }

    def "call named method in script"() {
        given:
        def script = shell.parse(new File('src/test/groovy/scripts', 'SimpleScript.groovy'))

        when:
        def result = script.sayHello('world')

        then:
        result == 'hello world'
    }

    def "call generic script method with parameters"() {
        given:
        def script = shell.parse(new File('src/test/groovy/scripts', 'ScriptMethod.groovy'))

        when:
        def result = script 'key1': 'value1', 'key2': 'value2'

        then:
        result == ['key1value1', 'key2value2' ]
    }

}