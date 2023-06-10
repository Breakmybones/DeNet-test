package com.example.denet.data.repository

import com.example.denet.data.utils.toNodeModel
import com.example.denet.domain.model.NodeModel
import com.example.denet.domain.repository.NodeRepository
import javax.inject.Inject

class NodeRepositoryImpl @Inject constructor(
    private val nodeDataSource: NodeDataSource
): NodeRepository {

    override suspend fun insertNode(parentName: String?) {
       nodeDataSource.insertNode(parentName)
    }

    override suspend fun getChildrenByName(parentName: String): List<NodeModel> {
        val response = nodeDataSource.getChildrenByName(parentName)
        val nodes = mutableListOf<NodeModel>()
        if (response != null) {
            for (node in response) {
                nodes.add(node.toNodeModel())
            }
        }
        return nodes

    }

    override suspend fun getNodeByName(name: String): NodeModel? {
        return nodeDataSource.getNodeByName(name)?.toNodeModel()
    }

    override suspend fun deleteNode(name: String) {
        nodeDataSource.deleteNode(name)
    }

    override suspend fun getParentNode(): NodeModel? {
        return nodeDataSource.getParentNode()?.toNodeModel()
    }

    override suspend fun getAllNodes(): List<NodeModel>? {
        val response = nodeDataSource.getAllNodes() ?: return null
        val nodes = mutableListOf<NodeModel>()
        for (node in response) {
            nodes.add(node.toNodeModel())
        }
        return nodes
    }
}