package com.example.denet.domain.usecase

import com.example.denet.domain.repository.NodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertNodeUseCase @Inject constructor(
    private val nodeRepository: NodeRepository,
) {
    suspend operator fun invoke(parentName: String?) {
        return withContext(Dispatchers.IO) {
            nodeRepository.insertNode(parentName)
        }
    }
}