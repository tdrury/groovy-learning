package shell

import spock.lang.Specification

class ShellSpec extends Specification {

    def "run shell command"() {

        when:
        def (String out, String err) = runCommand.call("pwd")
        println "run shell command: out=[${out}]"
        println "run shell command: err=[${err}]"

        then:
        out != null
        err == ""
    }

    def "run bad shell command"() {

        when:
        def (String out, String err) = runCommand.call("blah")
        println "run bad shell command: out=[${out}]"
        println "run bad shell command: err=[${err}]"

        then:
        out == ""
        err.contains("IOException")
    }

    def runCommand = { command ->
        def out = new StringBuffer()
        def err = new StringBuffer()
        try {
            def proc = command.execute()
            proc.consumeProcessOutput(out, err)
            proc.waitForProcessOutput()
        } catch (IOException ex) {
            err << ex.toString()
        }
        return [out.toString(), err.toString()]
    }
}
