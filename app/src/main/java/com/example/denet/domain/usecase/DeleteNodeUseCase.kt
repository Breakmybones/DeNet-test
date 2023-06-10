package com.example.denet.domain.usecase

import com.example.denet.domain.repository.NodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNodeUseCase @Inject constructor(
    private val nodeRepository: NodeRepository
) {
    suspend operator fun invoke(name: String) = withContext(Dispatchers.IO) {
            nodeRepository.deleteNode(name)
        }
}