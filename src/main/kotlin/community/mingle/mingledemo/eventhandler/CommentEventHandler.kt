package community.mingle.mingledemo.eventhandler

import community.mingle.mingledemo.domain.notification.facade.CommentNotificationFacade
import community.mingle.mingledemo.eventhandler.event.CommentCreateEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CommentEventHandler(
    private val commentNotificationFacade: CommentNotificationFacade,
) {


    @Async
    @TransactionalEventListener(CommentCreateEvent::class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCommentCreation(
        event: CommentCreateEvent
    ) {
        val comment = event.data
        commentNotificationFacade.create(comment)
    }
}