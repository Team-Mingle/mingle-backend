package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "post_scrap")
class PostScrap(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}