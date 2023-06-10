package com.example.denet.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.denet.data.model.Node


@Dao
interface NodeDao {

    @Query("INSERT INTO nodes (name, parentName) VALUES (:nodeName, :parentName)")
    suspend fun insert(nodeName: String, parentName: String?)

    @Query("SELECT * FROM nodes WHERE parentName = :parentName")
    suspend fun getChildren(parentName: String): List<Node>?

    @Query("SELECT * FROM nodes WHERE name = :name")
    suspend fun getNodeByName(name: String): Node?

    @Query("DELETE FROM nodes WHERE name = :name")
    suspend fun delete(name: String)

    @Query("SELECT * FROM nodes WHERE parentName is null")
    suspend fun getParentNode(): Node?

    @Query("SELECT * FROM nodes")
    suspend fun getAllNodes(): List<Node>?
}