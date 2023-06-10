package com.example.denet.domain.repository

import com.example.denet.domain.model.NodeModel

interface NodeRepository {

    suspend fun insertNode(parentName: String?)

    suspend fun getChildrenByName(parentName: String): List<NodeModel>?

    suspend fun getNodeByName(name: String): NodeModel?

    suspend fun deleteNode(name: String)

    suspend fun getParentNode(): NodeModel?

    suspend fun getAllNodes(): List<NodeModel>?
}