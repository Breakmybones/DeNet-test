package com.example.denet.presentation.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denet.R
import com.example.denet.databinding.FragmentNodeBinding
import com.example.denet.App
import com.example.denet.domain.model.NodeModel
import com.example.denet.domain.usecase.*
import com.example.denet.presentation.presenter.NodeViewModel
import com.example.denet.presentation.ui.recycler.NodeAdapter
import javax.inject.Inject

class NodeFragment : Fragment(R.layout.fragment_node){

    private var binding: FragmentNodeBinding? = null
    private var adapter: NodeAdapter? = null
    private var nodesListValue: List<NodeModel>? = null
    private var currentNodeValue: NodeModel? = null
    private var isEmpty: Boolean? = null

    @Inject
    lateinit var getChildrenUseCase: GetChildrenUseCase

    @Inject
    lateinit var insertNodeUseCase: InsertNodeUseCase

    @Inject
    lateinit var deleteNodeUseCase: DeleteNodeUseCase

    @Inject
    lateinit var getNodeUseCase: GetNodeUseCase

    @Inject
    lateinit var getParentNodeUseCase: GetParentNodeUseCase

    @Inject
    lateinit var getAllNodesUseCase: GetAllNodesUseCase

    private val viewModel: NodeViewModel by viewModels{
        NodeViewModel.provideFactory(
            deleteNodeUseCase,
            insertNodeUseCase,
            getChildrenUseCase,
            getNodeUseCase,
            getParentNodeUseCase,
            getAllNodesUseCase
        )
    }

    override fun onAttach(context: Context) {
        App.appComponent.injectNodeFragment(this)
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insertNode(null)

        viewModel.getAllNodes()

        if (isEmpty == true) {
            viewModel.insertNode(null)
        }

        binding = FragmentNodeBinding.bind(view)
        adapter = NodeAdapter(
            {onNodeClick(it)},
            {createDialog(it)},
            {deleteNode(it)}
        )
        binding?.rvNodes?.adapter = adapter
        binding?.rvNodes?.layoutManager = LinearLayoutManager(requireContext())
        binding?.btnBack?.setOnClickListener {
            returnBack(currentNodeValue)
        }

        getParentNode()

        observeViewModel()
    }

    private fun setListAdapterConfig() {
        binding?.rvNodes?.adapter = adapter
        if (!(nodesListValue.isNullOrEmpty())) {
            adapter?.submitList(nodesListValue)
        }
    }

    private fun setListAdapterConfigWhenNodesDeleted() {
        binding?.rvNodes?.adapter = adapter
        if (nodesListValue == null) {
            adapter?.submitList(listOf())
        }
        else {
            adapter?.submitList(nodesListValue)
        }
    }

    private fun onNodeClick(it: NodeModel) {
        viewModel.getNodesListByParentName(it.name)
        viewModel.getNodeByName(it.name)
        if (nodesListValue == null) {
            Toast.makeText(requireContext(), "This node has no children", Toast.LENGTH_SHORT).show()
        }
        else {
            setListAdapterConfig()
        }
    }

    private fun getParentNode() {
        viewModel.getParentNode()
    }

    private fun deleteNode(node: NodeModel?) {
        node?.name?.let { viewModel.deleteNode(it) }
        currentNodeValue?.let { viewModel.getNodesListByParentName(it.name) }
        setListAdapterConfigWhenNodesDeleted()
    }

    private fun returnBack(previousNode: NodeModel?) {
        if (previousNode?.parentName == null) {
            viewModel.getParentNode()
        }
        else {
            viewModel.getNodesListByParentName(previousNode.parentName)
            viewModel.getNodeByName(previousNode.parentName)
        }
        setListAdapterConfig()
    }

    private fun createDialog(node: NodeModel?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add a new node?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            if (node != null) {
                viewModel.insertNode(node.name)
            }
        })
        builder.setNegativeButton("No"){dialog, i ->

        }
        builder.show()
    }

    private fun observeViewModel() {
        with(viewModel) {
            nodesList.observe(viewLifecycleOwner) {
                nodesListValue = it
                setListAdapterConfig()
            }
            node.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                currentNodeValue = it
            }
            parentNode.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                adapter?.submitList(listOf(it))
            }
            isTreeEmpty.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                isEmpty = it
            }
        }
    }
}