package community.mingle.mingledemo.domain.notification.service

import community.mingle.mingledemo.domain.notification.entity.CommentNotification
import community.mingle.mingledemo.domain.notification.repository.CommentNotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentNotificationService(
        private val commentNotificationRepository: CommentNotificationRepository,
) {

    @Transactional
    fun create() {
        CommentNotification(

        )
    }
}