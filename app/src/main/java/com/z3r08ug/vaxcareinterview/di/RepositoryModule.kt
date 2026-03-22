package com.z3r08ug.vaxcareinterview.di

import com.z3r08ug.vaxcareinterview.data.repository.BookRepositoryImpl
import com.z3r08ug.vaxcareinterview.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl,
    ): BookRepository
}
