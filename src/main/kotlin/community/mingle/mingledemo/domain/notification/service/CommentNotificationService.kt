package community.mingle.mingledemo.domain.notification.service

import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.repository.MemberRepository.Companion.find
import community.mingle.mingledemo.domain.notification.entity.CommentNotification
import community.mingle.mingledemo.domain.notification.repository.CommentNotificationRepository
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.enums.NotificationType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentNotificationService(
    private val commentNotificationRepository: CommentNotificationRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun create(
        memberId: Long,
        comment: Comment,
        notificationType: NotificationType,
    ): CommentNotification {
        val member = memberRepository.find(memberId)
        return CommentNotification(
            member = member,
            board = comment.post.boardType,
            category = comment.post.categoryType,
            type = notificationType,
            comment = comment,
        ).run { commentNotificationRepository.save(this) }
    }
}