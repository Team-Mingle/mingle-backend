package community.mingle.mingledemo.domain.report.entity

import community.mingle.mingledemo.domain.member.entity.Member
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "content_type")
@Table(name = "report")
abstract class Report(

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_member_id", nullable = false)
    var reporterMember: Member,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reported_member_id", nullable = false)
    var reportedMember: Member,

    @Column(name = "reason", length = 200)
    var reason: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @NotNull
    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime
}