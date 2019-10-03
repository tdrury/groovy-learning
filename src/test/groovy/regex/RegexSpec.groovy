package regex

import spock.lang.Specification

class RegexSpec extends Specification {

	def "simple match"() {

		when:
		def name = 'foo-blue'

		then:
		name ==~ /foo-(blue|green)/
		(name ==~ /bar-(blue|green)/) == false
	}

    def "parse git branch"() {
        when:
        def gitBranch = "origin/master"
        def (remote, branch) = gitBranch.split("/")

        then:
        remote == "origin"
        branch == "master"
    }

    def "parse https-formatted git url"() {
        when:
        def gitUrl = "https://github.wdf.sap.corp/DigitalManufacturing/dme-plant-ms.git"
        def matcher =  gitUrl =~ /((git@|https:\/\/)([\w\.@]+)(\/|:))([\w,\-,\_]+)\/([\w,\-,\_]+).git/
        println "matcher[0]=${matcher[0]}"

        then:
        matcher.hasGroup()
        println "matcher[0] size=${matcher[0].size()}"
        matcher[0].size() == 7
        matcher[0][5] == "DigitalManufacturing"
        matcher[0][6] == "dme-plant-ms"
    }

    def "parse ssh-formatted git url"() {
        when:
        def gitUrl = "git@github.wdf.sap.corp:DigitalManufacturing/dme-plant-ms.git"
        def matcher =  gitUrl =~ /((git@|https:\/\/)([\w\.@]+)(\/|:))([\w,\-,\_]+)\/([\w,\-,\_]+).git/
        println "matcher[0]=${matcher[0]}"

        then:
        matcher.hasGroup()
        matcher[0].size() == 7
        matcher[0][5] == "DigitalManufacturing"
        matcher[0][6] == "dme-plant-ms"
    }

    def "return multiple values"() {
        when:
        def (String owner, String repo) = getOwnerAndRepoFromUrl("git@github.wdf.sap.corp:DigitalManufacturing/dme-plant-ms.git")

        then:
        owner == "DigitalManufacturing"
        repo == "dme-plant-ms"
    }

	def "parse String representation from yaml of list of maps where map has one entry"() {

		given:
		// comes from build system via yaml
		def ARGS='[[foo:Foo~Value-1], [bar:Bar~Value-2]]'
		def key, value
		def argList = []

		when:
//        def matcher = ARGS =~ /(\[([\w]+):([\w~-]+)\][\s,]?)*/
		def matcher = ARGS =~ /\[([^\]])*/
		println "matcher count=${matcher.count}"
		matcher.eachWithIndex { m,i ->
			println "m(${i})=${m}"
			println "   [0]=${m[0]}"
			def line = m[0].toString().replace('[', '')
			println "line=${line}"
			(key, value) = line.split(':')
			println "key [${key}] = value [${value}]"
			argList << [ (key): value ]
		}
		println "argList=${argList}"

		then:
		argList[0] == [ foo: 'Foo~Value-1' ]
		argList[1] == [ bar: 'Bar~Value-2' ]
	}

    def getOwnerAndRepoFromUrl(def url) {
        def matcher = url =~ /((git@|https:\/\/)([\w\.@]+)(\/|:))([\w,\-,\_]+)\/([\w,\-,\_]+).git/
        if (matcher?.hasGroup()) {
            if (matcher[0].size() == 7) {
                return [ matcher[0][5], matcher[0][6] ]
            }
        }
    }
}
