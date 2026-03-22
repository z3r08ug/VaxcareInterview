package com.z3r08ug.vaxcareinterview.domain.repository

import com.z3r08ug.vaxcareinterview.domain.model.Book

interface BookRepository {
    suspend fun getBooks(): List<Book>
}
