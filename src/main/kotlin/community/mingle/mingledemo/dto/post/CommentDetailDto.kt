package community.mingle.mingledemo.dto.post

data class CommentDetailDto(
    val commentDto: CommentDto,
    val isMyComment: Boolean,
    val isLiked: Boolean,
    val isReport: Boolean,
    val isAdmin: Boolean,
    val nicknameOrAnonymous: String,
    val coveredContentByStatus: String,
    val likeCount: Int,
)