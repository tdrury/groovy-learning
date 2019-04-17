package json

import groovy.json.JsonBuilder
import spock.lang.Specification

class JsonSpec extends Specification {

    def "groovy to json"() {
        when:
        def builder = new JsonBuilder()

        builder {
            required_status_checks {
                strict true
                contexts( [ "production-services/build-service-nova:PR-linuxx86_64-linuxx86_64" ] )
            }
            required_pull_request_reviews {
                dismiss_stale_reviews false
                require_code_owner_reviews true
                required_approving_review_count 1
            }
            enforce_admins true
            restrictions {
                teams( [ "DME-CC-Reviewers" ] )
            }
        }
        println "builder=${builder.toString()}"

        then:
        builder.toString() == '{"required_status_checks":{"strict":true,"contexts":["production-services/build-service-nova:PR-linuxx86_64-linuxx86_64"]},"required_pull_request_reviews":{"dismiss_stale_reviews":false,"require_code_owner_reviews":true,"required_approving_review_count":1},"enforce_admins":true,"restrictions":{"teams":["DME-CC-Reviewers"]}}'

    }
}
