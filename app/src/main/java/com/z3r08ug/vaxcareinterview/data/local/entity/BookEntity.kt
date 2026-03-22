package com.z3r08ug.vaxcareinterview.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.domain.model.BookStatus

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String,
    @Embedded(prefix = "status_") val status: BookStatusEntity,
    val fee: Double,
    val lastEdited: String,
)

data class BookStatusEntity(
    val statusId: Int,
    val displayText: String,
    val timeCheckedIn: String?,
    val timeCheckedOut: String?,
    val dueDate: String?
)

fun BookEntity.toDomain(): Book = Book(
    id = id,
    title = title,
    author = author,
    status = BookStatus(
        id = status.statusId,
        displayText = status.displayText,
        timeCheckedIn = status.timeCheckedIn,
        timeCheckedOut = status.timeCheckedOut,
        dueDate = status.dueDate
    ),
    fee = fee,
    lastEdited = lastEdited
)

fun Book.toEntity(): BookEntity = BookEntity(
    id = id,
    title = title,
    author = author,
    status = BookStatusEntity(
        statusId = status.id,
        displayText = status.displayText,
        timeCheckedIn = status.timeCheckedIn,
        timeCheckedOut = status.timeCheckedOut,
        dueDate = status.dueDate
    ),
    fee = fee,
    lastEdited = lastEdited
)
