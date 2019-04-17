package apis.github

import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import spock.lang.Specification

class GitHubSpec extends Specification {

    def "get primary email"() {

        given:
        def responseJson = (new JsonSlurper()).parseText("""[
  {
    "email": "gburdell@gatech.edu",
    "verified": true,
    "primary": true,
    "visibility": "public"
  }
]""")

        Map requestDelegate = [response: [:], uri: [:], headers: [:]]
        HTTPBuilder mockHTTPBuilder = Mock() {
            // page 1 of results
            1 * request(Method.GET, _) >> { Method m, Closure c ->
                c.delegate = requestDelegate
                c.call()
                assert c.delegate.uri.path == "/api/v3/user/emails"
                assert c.delegate.uri.query == [page: 1]
                requestDelegate.response.success([status: [:], statusLine: [:]], responseJson)
            }
            // page 2 of results (returns null)
            1 * request(Method.GET, _) >> { Method m, Closure c ->
                c.delegate = requestDelegate
                c.call()
                assert c.delegate.uri.path == "/api/v3/user/emails"
                assert c.delegate.uri.query == [page: 2]
                requestDelegate.response.success([status: [:], statusLine: [:]], null)
            }
        }

        def authToken = "token abcdef"
        def acceptContent = "application/vnd.github.v3+json"

        when:

        def github = mockHTTPBuilder
        github.setHeaders([Authorization: authToken, Accept: acceptContent])
        github.ignoreSSLIssues()

        def emails = []
        def page = 1
        while (page > 0) {
            github.request(Method.GET) { req ->
                uri.path = '/api/v3/user/emails'
                uri.query = [page: page]
                response.success = { resp, json ->
                    json.each { email ->
                        println "get primary email: got email: ${email}"
                        emails += email
                    }
                    page++
                    if (!json) page = -1
                }
                response.failure = { resp ->
                    println "get primary email: FAILED: ${resp.statusLine}"
                    page = -1
                }
            }
        }
        def primaryEmail = emails.find {
            it.primary == true
        }

        then:

        primaryEmail.email == 'gburdell@gatech.edu'
    }
}
