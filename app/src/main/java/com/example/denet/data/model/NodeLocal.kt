package com.example.denet.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "nodes", foreignKeys = [
    ForeignKey(
        entity = Node::class,
        parentColumns = ["name"],
        childColumns = ["parentName"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Node(
    @PrimaryKey
    val name: String,
    val parentName: String? = null
)
