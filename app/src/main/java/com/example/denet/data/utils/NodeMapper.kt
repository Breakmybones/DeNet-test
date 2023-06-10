package com.example.denet.data.utils

import com.example.denet.data.model.Node
import com.example.denet.domain.model.NodeModel

fun Node.toNodeModel(): NodeModel = NodeModel(
    name = name,
    parentName = parentName
)
