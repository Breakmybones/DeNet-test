package com.example.denet.di

import android.content.Context
import com.example.denet.data.database.AppDataBase
import com.example.denet.data.repository.NodeDataSource
import com.example.denet.presentation.ui.NodeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NodeModule::class, DataBaseModule::class])
@Singleton
interface AppComponent {

    fun injectNodeFragment(nodeFragment: NodeFragment)

    fun provideNodeDataSource(): NodeDataSource

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}