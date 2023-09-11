package community.mingle.mingledemo.eventhandler

import community.mingle.mingledemo.eventhandler.event.CommentCreateEvent
import org.springframework.stereotype.Component

@Component
class CommentEventHandler {


    fun handleCommentCreation(
            event: CommentCreateEvent
    ) {
        val comment = event.data


    }
}