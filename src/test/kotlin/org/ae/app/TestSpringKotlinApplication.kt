package org.ae.app

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SpringKotlinApplication>().with(TestcontainersConfiguration::class).run(*args)
}
