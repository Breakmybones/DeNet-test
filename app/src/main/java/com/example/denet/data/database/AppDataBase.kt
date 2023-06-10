package com.example.denet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.denet.data.dao.NodeDao
import com.example.denet.data.model.Node


@Database(entities = [Node::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getNodeDao(): NodeDao

}
