package com.zhu2chu.groovy

class First {
    static main(args) {
        def closure = { param -> println "hello ${param}" }
        closure("world")
    }
}
