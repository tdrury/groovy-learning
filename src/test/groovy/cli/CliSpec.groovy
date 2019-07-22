package cli

import groovy.cli.commons.CliBuilder
import spock.lang.Specification

class CliSpec extends Specification {

	def "boolean arg"() {

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

	def "string arg"() {

		given:
		def cli = new CliBuilder(usage: "groovy ${this.class.simpleName} [<options>]", header: "options:")
		cli.with {
			h longOpt: 'help', "Show usage information"
			f (longOpt: 'file', args: 1, argName: 'file', "a file ...")
		}

		when:
		def options = cli.parse("-h -f /foo/bar.txt".split())

		then:
		options.f == "/foo/bar.txt"
	}

	def "two args"() {

		given:
		def cli = new CliBuilder()
		cli.with {
			p (longOpt: 'params', args: 2, argName: 'params', "a couple parameters")
		}

		when:
		def options = cli.parse("-p one two".split())

		then:
		options.ps == ["one", "two"] // note plural 'p'
	}

	def "two args with comma separator"() {

		given:
		def cli = new CliBuilder()
		cli.with {
			p (longOpt: 'params', args: 2, valueSeparator: ',', argName: 'params', "a couple parameters")
		}

		when:
		def options = cli.parse("-p one,two".split())

		then:
		options.ps == ["one", "two"] // note plural 'p'
	}

	def "Integer arg"() {

		given:
		def cli = new CliBuilder()
		cli.with {
			i (longOpt: 'int', args: 1, argName: 'int', type: Integer, "a integer")
		}

		when:
		def options = cli.parse("-i 1234".split())

		then:
		options.i == 1234
	}

	def "default value arg"() {

		given:
		def cli = new CliBuilder()
		cli.with {
			i (longOpt: 'int', args: 1, argName: 'int', type: Integer, defaultValue: "999", "a integer")
		}

		when:
		def options = cli.parse("-i 1234".split())
		def optionsDefaultArgs = cli.parse("".split())

		then:
		options.i == 1234
		optionsDefaultArgs.i == 999
	}


}
