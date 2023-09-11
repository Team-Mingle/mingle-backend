package community.mingle.mingledemo.domain.notification.entity

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.NotificationType
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notification")
abstract class Notification(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,

    @Column(name = "`read`", nullable = false)
    var read: Boolean? = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "board", nullable = false)
    var board: BoardType,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    var category: CategoryType,

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    var type: NotificationType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime
}