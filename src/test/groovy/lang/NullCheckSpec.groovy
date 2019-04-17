package lang

import spock.lang.Specification

class NullCheckSpec extends Specification {

    def "inline null check"() {

        when:
        def params = [ foo: "foo", empty: "" ]

        then:
        params.foo.trim().length() > 0
        params.empty.trim().length() == 0
        params.undefined == null
        (params.undefined?.trim()?.length() > 0 ) == false
    }
}
