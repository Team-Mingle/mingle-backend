package community.mingle.mingledemo.domain.notification.facade

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.notification.service.CommentNotificationService
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.domain.post.service.CommentService
import community.mingle.mingledemo.enums.NotificationType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentNotificationFacade(
    private val commentService: CommentService,
    private val commentNotificationService: CommentNotificationService,
) {

    @Transactional
    fun create(
        comment: Comment
    ) {
        val postWriter = comment.post.member

        val parentCommentWriter = comment.parentCommentId?.let {
            commentService.getById(it).member
        }

        val mentionedMember = comment.mentionId?.let {
            commentService.getById(it).member
        }

        val commentWriter = comment.member

        val deDuplicatedMembers = deDuplicateMember(
            postWriter = postWriter,
            parentCommentWriter = parentCommentWriter,
            mentionedMember = mentionedMember,
            commentWriter = commentWriter
        )

        deDuplicatedMembers.forEach { member ->
            val notificationType =
                when (member.value) {
                    POST_WRITER -> NotificationType.COMMENT
                    PARENT_COMMENT_WRITER -> NotificationType.COCOMMENT
                    MENTIONED_MEMBER -> NotificationType.COCOMMENT
                    else -> NotificationType.COMMENT
                }
            commentNotificationService.create(
                memberId = member.key.id!!,
                comment = comment,
                notificationType = notificationType
            )
        }


    }

    private fun deDuplicateMember(
        postWriter: Member,
        parentCommentWriter: Member?,
        mentionedMember: Member?,
        commentWriter: Member
    ): HashMap<Member, String> {
        val deDuplicatedMemberMap = HashMap<Member, String>()
        deDuplicatedMemberMap[postWriter] = POST_WRITER
        deDuplicatedMemberMap[parentCommentWriter] = PARENT_COMMENT_WRITER
        deDuplicatedMemberMap[mentionedMember] = MENTIONED_MEMBER

        deDuplicatedMemberMap.remove(commentWriter)
        
        return deDuplicatedMemberMap
    }

    private operator fun <K, V> HashMap<K, V>.set(key: K?, value: V) {
        if (key != null) {
            put(key, value)
        }
    }

    companion object {
        private const val POST_WRITER = "postWriter"
        private const val PARENT_COMMENT_WRITER = "parentCommentWriter"
        private const val MENTIONED_MEMBER = "mentionedMember"
    }
}


