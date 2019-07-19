package cli

import groovy.cli.commons.CliBuilder
import spock.lang.Specification

class CliSpec extends Specification {

	def "simple help"() {

		given:
		def args = "-h".split()
		def cli = new CliBuilder()
		cli.with {
			h longOpt: 'help', "Show usage information"
		}
		cli.width = 140

		when:
		def options = cli.parse(args)

		then:
		options.h
	}

	def "get a string argument"() {

		given:
		def args = "-h -f /foo/bar.txt".split()
		def cli = new CliBuilder(usage: "groovy ${this.class.simpleName} [<options>]", header: "options:")
		cli.with {
			h longOpt: 'help', "Show usage information"
			f (longOpt: 'file', args: 1, argName: 'file', "a file ...")

		}

		when:
		def options = cli.parse(args)

		then:
		options.f == "/foo/bar.txt"
	}

}
