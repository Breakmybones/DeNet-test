package com.example.denet.domain.usecase

import com.example.denet.domain.model.NodeModel
import com.example.denet.domain.repository.NodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetChildrenUseCase @Inject constructor(
    private val nodeRepository: NodeRepository,
) {
    suspend operator fun invoke(parenName: String): List<NodeModel>? = withContext(Dispatchers.IO) {
            nodeRepository.getChildrenByName(parenName)
        }
}