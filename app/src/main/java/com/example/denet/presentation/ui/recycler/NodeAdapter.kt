package com.example.denet.presentation.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.denet.R
import com.example.denet.domain.model.NodeModel

class NodeAdapter(
    private val actionShowChildren: (NodeModel) -> Unit,
    private val onAddNewNode: (NodeModel) -> Unit,
    private val onDeleteClick: (NodeModel) -> Unit
): ListAdapter<NodeModel, RecyclerView.ViewHolder>(
    object: DiffUtil.ItemCallback<NodeModel>() {
        override fun areItemsTheSame(
            oldItem: NodeModel,
            newItem: NodeModel
        ): Boolean = (oldItem as? NodeModel)?.name == (newItem as? NodeModel)?.name

        override fun areContentsTheSame(
            oldItem: NodeModel,
            newItem: NodeModel): Boolean  = oldItem == newItem
    }
) {
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int)
    {
        val currentItem = currentList[position]
        when (holder) {
            is NodeHolder -> holder.onBind(currentItem as NodeModel)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is NodeModel -> R.layout.item_node
            else -> throw IllegalArgumentException("Error recycler view")
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_node -> NodeHolder.create(parent, actionShowChildren, onAddNewNode,  onDeleteClick)
            else -> throw IllegalArgumentException("Error!")
        }
}