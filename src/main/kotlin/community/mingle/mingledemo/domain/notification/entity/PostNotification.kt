package community.mingle.mingledemo.domain.notification.entity

import community.mingle.mingledemo.domain.post.entity.Post
import jakarta.persistence.*

@Entity
@Table(name = "post_notification")
open class PostNotification {
    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    open var notification: Notification? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    open var post: Post? = null
}