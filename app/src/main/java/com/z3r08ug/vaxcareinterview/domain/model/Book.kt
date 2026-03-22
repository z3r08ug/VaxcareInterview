package com.z3r08ug.vaxcareinterview.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val status: BookStatus,
    val fee: Double,
    val lastEdited: String,
)

data class BookStatus(
    val id: Int,
    val displayText: String,
    val timeCheckedIn: String? = null,
    val timeCheckedOut: String? = null,
    val dueDate: String? = null
)
