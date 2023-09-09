package community.mingle.mingledemo.dto.post

data class PostDetailDto(
    val postDto: PostDto,
    val isMyPost: Boolean,
    val isLiked: Boolean,
    val isScraped: Boolean,
    val isReported: Boolean,
    val isAdmin: Boolean,
)