package community.mingle.mingledemo.domain.notification.entity

import community.mingle.mingledemo.domain.post.entity.Post
import jakarta.persistence.*

@Entity
@Table(name = "post_notification")
class PostNotification {
    @Id
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var notification: Notification? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post? = null
}