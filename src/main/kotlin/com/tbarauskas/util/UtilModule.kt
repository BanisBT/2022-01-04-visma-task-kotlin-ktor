package com.tbarauskas.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.koin.dsl.module

val utilModule = module {
    single {
        jacksonObjectMapper()
    }
}
