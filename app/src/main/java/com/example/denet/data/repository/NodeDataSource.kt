package com.example.denet.data.repository

import com.example.denet.data.database.AppDataBase
import com.example.denet.data.model.Node
import com.example.denet.data.utils.generateNodeName
import javax.inject.Inject

class NodeDataSource @Inject constructor(private val database: AppDataBase) {

    private val nodeDao by lazy {
        database.getNodeDao()
    }

    suspend fun insertNode(parentName: String?) {
        nodeDao.insert(generateNodeName(parentName), parentName)
    }

    suspend fun getChildrenByName(parentName: String): List<Node>? {
        return nodeDao.getChildren(parentName)
    }

    suspend fun getNodeByName(name: String): Node? {
        return nodeDao.getNodeByName(name)
    }

    suspend fun deleteNode(name: String) {
        nodeDao.delete(name)
    }

    suspend fun getParentNode(): Node? {
        return nodeDao.getParentNode()
    }

    suspend fun getAllNodes(): List<Node>? {
        return nodeDao.getAllNodes()
    }
}