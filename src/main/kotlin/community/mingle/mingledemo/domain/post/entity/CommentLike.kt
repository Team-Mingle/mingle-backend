package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*

@Entity
@DiscriminatorValue("COMMENT")
@Table(name = "comment_like")
class CommentLike(
    member: Member,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    var comment: Comment,

    ) : ContentLike(
    member = member,
)