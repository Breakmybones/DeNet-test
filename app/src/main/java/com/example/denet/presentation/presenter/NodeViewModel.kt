package com.example.denet.presentation.presenter

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.denet.domain.model.NodeModel
import com.example.denet.domain.usecase.*
import kotlinx.coroutines.launch
import timber.log.Timber

class NodeViewModel (
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val insertNodeUseCase: InsertNodeUseCase,
    private val getChildrenUseCase: GetChildrenUseCase,
    private val getNodeUseCase: GetNodeUseCase,
    private val getParentNodeUseCase: GetParentNodeUseCase,
    private val getAllNodesUseCase: GetAllNodesUseCase
): ViewModel() {

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get()  = _error

    private val _nodesList = MutableLiveData<List<NodeModel>?>(null)
    val nodesList: LiveData<List<NodeModel>?>
        get()  = _nodesList

    private val _node = MutableLiveData<NodeModel?>(null)
    val node: LiveData<NodeModel?>
        get()  = _node

    private val _parentNode = MutableLiveData<NodeModel?>(null)
    val parentNode: LiveData<NodeModel?>
        get()  = _parentNode

    private val _isTreeEmpty = MutableLiveData<Boolean?>(null)
    val isTreeEmpty: LiveData<Boolean?>
        get() = _isTreeEmpty

    fun getNodesListByParentName(parentName: String) {
        viewModelScope.launch {
            try {
                if (!(getChildrenUseCase(parentName).isNullOrEmpty())) {
                    _nodesList.value = getChildrenUseCase(parentName)
                }
                else if (getChildrenUseCase(parentName).isNullOrEmpty()) {
                    _nodesList.value = null
                }
            }
            catch (error: Throwable) {
                _error.value = error
                Timber.tag("nodes list error").e(error.toString())
            }
        }
    }

    fun getNodeByName(name: String) {
        viewModelScope.launch {
            try {
                if (getNodeUseCase(name) != null) {
                    _node.value = getNodeUseCase(name)
                }
                else if (getNodeUseCase(name) == null) {
                    _node.value = null
                }
            }
            catch (error: Throwable) {
                _error.value = error
                Timber.tag("get node error").e(error.toString())
            }
        }
    }

    fun insertNode(parentName: String?) {
        viewModelScope.launch {
            try {
                insertNodeUseCase(parentName)
            }
            catch (error: Throwable) {
                _error.value = error
                Timber.tag("insert node error").e(error.toString())
            }
        }
    }

    fun deleteNode(name: String) {
        viewModelScope.launch {
            try {
                deleteNodeUseCase(name)
            }
            catch (error: Throwable) {
                _error.value = error
                Timber.tag("delete node error").e(error.toString())
            }
        }
    }

    fun getParentNode() {
        viewModelScope.launch {
            try {
                if ((getParentNodeUseCase()) != null) {
                    _parentNode.value = getParentNodeUseCase()
                }
            }
            catch (error: Throwable) {
                Timber.tag("parent error").e(error.toString())
            }
        }
    }

    fun getAllNodes() {
        viewModelScope.launch {
            try {
                _isTreeEmpty.value = getAllNodesUseCase().isNullOrEmpty()
            }
            catch (error: Throwable) {
                Timber.tag("get all nodes error").e(error.toString())
            }
        }
    }

    companion object {
        fun provideFactory(
            deleteNodeUseCase: DeleteNodeUseCase,
            insertNodeUseCase: InsertNodeUseCase,
            getChildrenUseCase: GetChildrenUseCase,
            getNodeUseCase: GetNodeUseCase,
            getParentNodeUseCase: GetParentNodeUseCase,
            getAllNodesUseCase: GetAllNodesUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NodeViewModel(
                    deleteNodeUseCase,
                    insertNodeUseCase,
                    getChildrenUseCase,
                    getNodeUseCase,
                    getParentNodeUseCase,
                    getAllNodesUseCase
                )
            }
        }
    }
}