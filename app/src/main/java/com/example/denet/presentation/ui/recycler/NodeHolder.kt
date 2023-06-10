package com.example.denet.presentation.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.denet.databinding.ItemNodeBinding
import com.example.denet.domain.model.NodeModel

class NodeHolder(
    private val binding: ItemNodeBinding,
    private val actionShowChildren: (NodeModel) -> Unit,
    private val onAddNewNode: (NodeModel) -> Unit,
    private val onDeleteClick: (NodeModel) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    private var nodeModel: NodeModel? = null

    init {
        itemView.setOnClickListener {
            nodeModel?.also(actionShowChildren)
        }
        itemView.setOnLongClickListener {
            nodeModel?.also(onAddNewNode)
            true
        }
        with(binding) {
            btnDelete.setOnClickListener {
                nodeModel?.also(onDeleteClick)
            }
        }
    }

    fun onBind(nodeModel: NodeModel) {
        this.nodeModel = nodeModel
        with(binding) {
            tvName.text = nodeModel.name
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            actionShowChildren: (NodeModel) -> Unit,
            onAddNewNode: (NodeModel) -> Unit,
            onDeleteClick: (NodeModel) -> Unit
        ): NodeHolder = NodeHolder(
            binding = ItemNodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionShowChildren,
            onAddNewNode,
            onDeleteClick
        )
    }
}