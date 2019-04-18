package lang

import spock.lang.Specification

class StringSpec extends Specification {

    def "calculation in gstring"() {

        given:
        def s = "build_"
        def buildNumber = "10"
        def m = 3

        when:
        def result = "${s+( (buildNumber as int) % m)}"
        println "result=${result}"

        then:
        result == "build_1"
    }

    def "null in gstring is not empty"() {
        when:
        String s = null

        then:
        "${s}".length() == 4
    }

    def "return everything before dash"() {

        given:
        def filterVersion = { String version ->
            version.indexOf('-') > 0 ? version[0..version.indexOf('-')-1] : version
        }

        when:
        String snapshot = "1.2.3-SNAPSHOT"
        String release = "7.8.9"
        String buildVersion = "5.10.6-201901011533-githash"

        then:
        filterVersion(snapshot) == "1.2.3"
        filterVersion(release) == "7.8.9"
        filterVersion(buildVersion) == "5.10.6"
        //snapshot[0..Math.max(snapshot.length(),snapshot.indexOf('-'))-1] == "1.2.3"
        //release[0..Math.max(release.length(),release.indexOf('-'))-1] == "7.8.9"
        //buildVersion[0..Math.max(buildVersion.length(),buildVersion.indexOf('-'))-1] == "5.10.6"
    }

    def "remove part of string"() {

        when:
        def s = "blah-ms-docker"
        def result = s - "-docker"
        def nochange = s - "XYZ"

        then:
        result == "blah-ms"
        nochange == s
    }

}
