package com.unaerp.kotlinrestapi.Model

data class RandomFact(
    val id: String,
    val text: String,
    val source: String,
    val source_url: String,
    val language: String,
    val permalink: String
)
