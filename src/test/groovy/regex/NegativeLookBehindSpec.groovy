package regex

import spock.lang.Specification

class NegativeLookBehindSpec extends Specification {

    def setup() {
    }

    def "filter list of git repos"() {
        given:
        // had to use negative look-behind (?<!...) because the preceding .* is greedy.
        // filter repos that end with '-it', '-perf', '-broker' and non-master branches
        def repoNameFilter = /^MyOrg\/(acme|ac)-.*(?<!-it|-perf|-broker)\/master$/

        def repos = ['MyOrg/acme-user-ms/master',
                     'MyOrg/acme-labor-ms/master',
                     'MyOrg/acme-labor-ui/rel-1.0',
                     'MyOrg/acme-cart-it/master',
                     'MyOrg/acme-cart-perf/master',
                     'MyOrg/acme-shopping-ms-mta/master',
                     'MyOrg/acme-legacy-lib/master',
                     'MyOrg/acme-parent-poms/master',
                     'MyOrg/ac-integration/master',
                     'MyOrg/ac-integration-broker/master',
                     'MyOrg/acme-dev-tools/master',
                     'MyOrg/acme-devops/master',
                     'MyOrg/acme-approuter-mta/master'
        ]

        when:
        def matching = repos.findAll { it =~ repoNameFilter }

        then:
        matching == ['MyOrg/acme-user-ms/master',
                     'MyOrg/acme-labor-ms/master',
                     'MyOrg/acme-shopping-ms-mta/master',
                     'MyOrg/acme-legacy-lib/master',
                     'MyOrg/acme-parent-poms/master',
                     'MyOrg/ac-integration/master',
                     'MyOrg/acme-dev-tools/master',
                     'MyOrg/acme-devops/master',
                     'MyOrg/acme-approuter-mta/master'
        ]
    }

}


