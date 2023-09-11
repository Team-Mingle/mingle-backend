package community.mingle.mingledemo.domain.notification.repository

import community.mingle.mingledemo.domain.notification.entity.CommentNotification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentNotificationRepository: JpaRepository<CommentNotification, Long> {
}