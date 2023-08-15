package community.mingle.mingledemo.domain.notification.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.NotificationType
import jakarta.persistence.*

@Entity
@Table(name = "comment_notification")
class CommentNotification(

    member: Member,
    read: Boolean,
    board: BoardType,
    category: CategoryType,
    type: NotificationType,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    var comment: Comment,

    ) : Notification(
    member = member,
    read = read,
    board = board,
    category = category,
    type = type
)