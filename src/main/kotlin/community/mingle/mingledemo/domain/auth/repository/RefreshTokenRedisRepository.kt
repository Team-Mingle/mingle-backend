package community.mingle.mingledemo.domain.auth.repository

import community.mingle.mingledemo.domain.auth.entity.RefreshToken
import community.mingle.mingledemo.exception.AuthenticateFailedException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

interface RefreshTokenRedisRepository : CrudRepository<RefreshToken, String> {

    companion object {
        fun RefreshTokenRedisRepository.find(refreshToken: String) = findByIdOrNull(refreshToken)?: throw AuthenticateFailedException()
    }
}