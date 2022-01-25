package com.tbarauskas.features.driver

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.koin.dsl.module

val driverServiceModule = module {
    single {
        get<Jdbi>().onDemand<DriverDAO>()
    }
    single {
        DriverService(get(), get(), get())
    }
}
