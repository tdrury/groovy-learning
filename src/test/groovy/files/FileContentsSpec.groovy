package files

import spock.lang.Specification

class FileContentsSpec extends Specification {

    File propertiesFile = new File('src/test/resources', 'replace.properties')

    def "replace line in Properties file"() {

        given:
        File outputFile = new File('build/tmp', 'replace_1.properties')
        def sourceRegex = /sonar\.projectVersion=(.*)/
        def replacement = "sonar.projectVersion=7.8.9"
        def contents = propertiesFile.text

        when:
        def newContents = contents.replaceAll(sourceRegex, replacement)
        outputFile.withWriter {
            it << newContents
        }
        def original = propertiesFile.readLines()
        def outputLines = outputFile.readLines()

        then:
        original.size() == 13
        outputLines.size() == 13
        outputLines.eachWithIndex { line, i ->
            if (!line.startsWith("sonar.projectVersion")) {
                assert line == original[i]
                println "line: ${line}"
            } else {
                assert line == "sonar.projectVersion=7.8.9"
                println "*line: ${line}"
            }
        }
    }
}
