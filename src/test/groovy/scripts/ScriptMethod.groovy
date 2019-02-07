package scripts

def call(Map parameters = [:], Closure body = null) {

    //println "[ScriptMethod] parameters="+parameters

    // return list of strings consisting of concatenated key+value pairs
    parameters.collect { k,v -> k+v }
}