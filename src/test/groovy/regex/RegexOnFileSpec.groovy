package regex

import spock.lang.Specification

class RegexOnFileSpec extends Specification {

    def "read sonar.projectKey value"() {
        when:
        def matcher = SONAR_FILE_CONTENTS =~ /sonar\.projectKey=(.*)/

        then:
        matcher[0][1] == "com.sap.dm.dme:dme.plant.ui.mta:master"
    }

	def "change sonar.projectKey suffix"() {
		when:
		def projectKey = "com.sap.dm.dme:dme.plant.ui.mta:master"
		def newProjectKey = projectKey.replaceAll(/([\w.]+):([\w.]+):([\w.]+)/) { all, k1, k2, k3 ->
			"${k1}:${k2}:rc"
		}

		then:
		newProjectKey == "com.sap.dm.dme:dme.plant.ui.mta:rc"
	}

	def "change sonar.projectKey suffix - method 2"() {
		when:
		def projectKey = "com.sap.dm.dme:dme.plant.ui.mta:master"
		def newProjectKey = projectKey.substring(0, projectKey.lastIndexOf(':')) + ":rc"

		then:
		newProjectKey == "com.sap.dm.dme:dme.plant.ui.mta:rc"
	}


	static final String SONAR_FILE_CONTENTS ='''# Root project information
sonar.projectKey=com.sap.dm.dme:dme.plant.ui.mta:master
sonar.projectName=DME Plant UI5
sonar.projectVersion=4.0.0
'''
}
