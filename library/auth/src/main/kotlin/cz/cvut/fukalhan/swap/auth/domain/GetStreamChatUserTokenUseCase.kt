package cz.cvut.fukalhan.swap.auth.domain

class GetStreamChatUserTokenUseCase(private val repository: AuthRepository) {
    suspend fun getChatToken(): String? {
        return repository.getStreamChatUserToken()
    }
}
