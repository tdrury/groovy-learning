/**
 * this class is intentionally placed in the src/test/resources directory so that it can be loaded
 * and compiled by the ClassloaderSpec test class
 */

class Hello {

	def sayHi(def name) {
		"Hi, ${name}"
	}
}
