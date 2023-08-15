package community.mingle.mingledemo.domain.report.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.Post
import jakarta.persistence.*

@Entity
@DiscriminatorValue("POST")
class PostReport(
    reporterMember: Member,
    reportedMember: Member,
    reason: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = true)
    var post: Post,
) : Report(
    reporterMember = reporterMember,
    reportedMember = reportedMember,
    reason = reason
)