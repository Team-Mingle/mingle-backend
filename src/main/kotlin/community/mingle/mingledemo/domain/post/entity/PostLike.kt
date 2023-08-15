package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("POST")
@Table(name = "post_like")
open class PostLike(
    member: Member,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,
) : ContentLike(
    member = member,
)