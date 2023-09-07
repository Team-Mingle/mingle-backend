package community.mingle.mingledemo.domain.post.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*

@Entity
@DiscriminatorValue("POST")
@Table(name = "post_like")
open class PostLike(
    member: Member,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,



    ) : ContentLike(
    member = member,
)