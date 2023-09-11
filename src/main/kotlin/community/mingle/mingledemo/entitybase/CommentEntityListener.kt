package community.mingle.mingledemo.entitybase

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.eventhandler.event.CommentCreateEvent
import jakarta.persistence.PostPersist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class CommentEntityListener {
    @Autowired
    lateinit var applicationEventPublisher: ApplicationEventPublisher

    @PostPersist
    fun handleEntityCreate(comment: Comment) {
        applicationEventPublisher.publishEvent(
                CommentCreateEvent(
                        source = this,
                        data = comment
                )
        )
    }
}