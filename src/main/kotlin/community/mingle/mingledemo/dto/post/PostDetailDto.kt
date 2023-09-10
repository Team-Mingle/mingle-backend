package community.mingle.mingledemo.dto.post

data class PostDetailDto(
    val postDto: PostDto,
    val isMyPost: Boolean,
    val isLiked: Boolean,
    val isScraped: Boolean,
    val isReport: Boolean,
    val isAdmin: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val scrapCount: Int,
)