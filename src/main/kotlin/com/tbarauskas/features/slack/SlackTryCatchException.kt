package com.tbarauskas.features.slack

data class SlackTryCatchException(
    val error: String
) : RuntimeException(){
}
