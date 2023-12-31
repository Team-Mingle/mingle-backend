package community.mingle.mingledemo.domain.member.entity

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.entitybase.AuditLoggingBase
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "member")
class Member(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    var university: University,

    @Column(name = "nickname", nullable = false, length = 45)
    var nickname: String,

    @Column(name = "email", nullable = false, length = 100)
    var email: String,

    @Column(name = "password", nullable = false, length = 100)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: MemberStatus,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: MemberRole,

    @Column(name = "agreed_at")
    var agreedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @Column(name = "fcm_token")
    var fcmToken: String? = null,
): AuditLoggingBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    var posts = mutableListOf<Post>()
}