package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("COMMENT")
@Table(name = "comment_like")
class CommentLike(
    member: Member,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    var comment: Comment,

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,
) : ContentLike(
    member = member,
)