package community.mingle.mingledemo.domain.report.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.Comment
import jakarta.persistence.*

@Entity
@DiscriminatorValue("POST")
class CommentReport(
    reporterMember: Member,
    reportedMember: Member,
    reason: String,


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = true)
    var comment: Comment,
) : Report(
    reporterMember = reporterMember,
    reportedMember = reportedMember,
    reason = reason
)