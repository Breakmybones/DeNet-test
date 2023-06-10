package com.example.denet.di

import com.example.denet.data.repository.NodeRepositoryImpl
import com.example.denet.domain.repository.NodeRepository
import dagger.Binds
import dagger.Module

@Module
interface NodeModule {

    @Binds
    fun bindNodeRepository(repository: NodeRepositoryImpl): NodeRepository
}