package yaml

import org.yaml.snakeyaml.Yaml
import spock.lang.Specification

class YamlSpec extends Specification {

	static final String TEST1_YAML_PATH = "src/test/resources/test1.yaml"

	Yaml parser = new Yaml()
	List yaml

	def setup() {
		yaml = parser.load((TEST1_YAML_PATH as File).text)
	}

	def "read yaml"() {
		expect:
		yaml != null
	}

	def "get deep node"() {
		when:
		def msDev = yaml.find { it.name == 'J MS DEV'}
		def dc = msDev.iterations.find { it.name == 'acme-dc-ms' }
		def upstream = dc.'stages[name=Version and Build].tasks[name=Version].arguments.addEnvVars.UPSTREAM_JOBNAMES'
		//println "upstream=${upstream}"

		then:
		msDev.name == 'J MS DEV'
		upstream[0].dmdme == 'J~lib~DEV~acme-jacoco-lib~Pl-acme-jacoco-lib-Version~and~Build-Version'
		upstream[1].dmdme == 'J~lib~DEV~acme-ext-lib~Pl-acme-ext-lib-Version~and~Build-Version'
	}

	def "find all acme-frm-lib"() {

		when:
		def libTriggers = [:]
		def pipelines = yaml.findAll { it =~ /J Lib .*/}
		pipelines.each { pipeline ->
			//println "pipeline: ${pipeline.name}"
			def lib = pipeline.iterations.find { it.name == 'acme-frm-lib' }
			//println "   lib=${lib.name}"
			def triggerJobs = lib.'stages[name=PostPipe].tasks[name=PostPipe].arguments.addEnvVars.TRIGGER_JOBNAMES'
			//println "triggerJobs=${triggerJobs}"

			def apps = [ ]
			triggerJobs.each { triggerJob ->
				triggerJob.each { host, job ->
					apps << host + '_' + getAppFromJobName(job)
				}
			}

			libTriggers[pipeline.name] = apps
		}
		//println "libTriggers=${libTriggers}"

		libTriggers.each { pipeline, apps ->
			println "pipeline: ${pipeline}"
			//println "   jobs=${jobs}"
			apps.each { println "   ${it}" }
		}

		then:
		pipelines != null
		libTriggers.'J Lib DEV' != libTriggers.'J Lib RC'
		libTriggers.'J Lib RC' == libTriggers.'J Lib RELEASE'
	}


	def "test jobname regex"() {
		when:
		def jobname = 'J~lib~RC~acmef-servreg-api~Pl-acmef-servreg-api-Trigger-Trigger'

		then:
		getAppFromJobName(jobname) == 'acmef-servreg-api'
	}

	def getAppFromJobName(def jobname) {
		def matcher = jobname =~ /\w+~\w+~\w+~([\w-]+)~.*/
		def app = null
		matcher.each { full, match1 ->
			app = match1
		}
		return app
	}
}
