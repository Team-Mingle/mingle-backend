package community.mingle.mingledemo.domain.post.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "post_image")
class PostImage(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @Column(name = "url", nullable = false)
    var url: String,

    @Column(name = "deleted_at", nullable = false)
    var deletedAt: LocalDateTime,
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