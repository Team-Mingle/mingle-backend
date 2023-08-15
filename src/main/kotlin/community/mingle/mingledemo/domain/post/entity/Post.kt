package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.entitybase.AuditLoggingBase
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentType
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "post")
class Post(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "title", nullable = false, length = 45)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "board", nullable = false)
    var board: BoardType,

    @Column(name = "category", nullable = false)
    var category: CategoryType,

    @Column(name = "anonymous", nullable = false)
    var anonymous: Boolean,

    @Column(name = "file_attached", nullable = false)
    var fileAttached: Boolean,

    @Column(name = "status", nullable = false)
    var status: ContentType,

    @Column(name = "view_count")
    var viewCount: Int,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime,
) : AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    var postImages = mutableListOf<PostImage>()
}