package json

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import spock.lang.Specification

class JsonSpec extends Specification {

	def "groovy to json"() {
		when:
		def builder = new JsonBuilder()

		builder {
			required_status_checks {
				strict true
				contexts(["production-services/build-service-nova:PR-linuxx86_64-linuxx86_64"])
			}
			required_pull_request_reviews {
				dismiss_stale_reviews false
				require_code_owner_reviews true
				required_approving_review_count 1
			}
			enforce_admins true
			restrictions {
				teams(["DME-CC-Reviewers"])
			}
		}
		println "builder=${builder.toString()}"

		then:
		builder.toString() == '{"required_status_checks":{"strict":true,"contexts":["production-services/build-service-nova:PR-linuxx86_64-linuxx86_64"]},"required_pull_request_reviews":{"dismiss_stale_reviews":false,"require_code_owner_reviews":true,"required_approving_review_count":1},"enforce_admins":true,"restrictions":{"teams":["DME-CC-Reviewers"]}}'

	}


	def JSON1 = '''{
    "component": {
        "id": "1234",
        "key": "com.acme:burdell:master",
        "name": "ACME - Burdell Microservice master",
        "qualifier": "TRK",
        "measures": [
            {
                "metric": "coverage",
                "value": "85.8",
                "periods": [
                    {
                        "index": 1,
                        "value": "-1.2000000000000028"
                    }
                ]
            }
        ]
    }
}'''

	def "json to groovy"() {
		when:
		def json = new JsonSlurper().parseText(JSON1)

		then:
		json.component.id == "1234"
		json.component.measures[0].metric == "coverage"
		json.component.measures.find { it.metric == "coverage" }.value == "85.8"

	}
}
