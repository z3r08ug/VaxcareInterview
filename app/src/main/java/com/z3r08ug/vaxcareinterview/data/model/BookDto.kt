package com.z3r08ug.vaxcareinterview.data.model

import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: Int,
    val title: String,
    val author: String,
    val status: BookStatusDto,
    val fee: Double,
    val lastEdited: String,
)

@Serializable
data class BookStatusDto(
    val id: Int,
    val displayText: String,
    val timeCheckedIn: String? = null,
    val timeCheckedOut: String? = null,
    val dueDate: String? = null
)

fun BookDto.toDomain(): Book = Book(
    id = id,
    title = title,
    author = author,
    status = status.toDomain(),
    fee = fee,
    lastEdited = lastEdited
)

fun BookStatusDto.toDomain(): BookStatus = BookStatus(
    id = id,
    displayText = displayText,
    timeCheckedIn = timeCheckedIn,
    timeCheckedOut = timeCheckedOut,
    dueDate = dueDate
)
