package community.mingle.mingledemo.eventhandler.event

import community.mingle.mingledemo.domain.post.entity.Comment
import org.springframework.context.ApplicationEvent

class CommentCreateEvent(
        source: Any,
        val data: Comment
): ApplicationEvent(source)