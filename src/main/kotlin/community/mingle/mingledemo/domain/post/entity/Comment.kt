package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.report.entity.CommentReport
import community.mingle.mingledemo.entitybase.AuditLoggingBase
import community.mingle.mingledemo.entitybase.CommentEntityListener
import community.mingle.mingledemo.enums.ContentStatusType
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime

@Entity
@EntityListeners(CommentEntityListener::class)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: ContentStatusType = ContentStatusType.ACTIVE,
) : AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    @BatchSize(size = 10)
    var likes = mutableListOf<CommentLike>()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    @BatchSize(size = 10)
    var reports = mutableListOf<CommentReport>()
}