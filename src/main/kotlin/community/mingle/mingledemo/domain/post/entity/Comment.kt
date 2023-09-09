package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.entitybase.AuditLoggingBase
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE comment SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "comment")
class Comment(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "parent_comment_id")
    var parentCommentId: Long?,

    @Column(name = "mention_id")
    var mentionId: Long?,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "anonymous", nullable = false)
    var anonymous: Boolean,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime,
) : AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}