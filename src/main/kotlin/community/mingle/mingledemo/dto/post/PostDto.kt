package community.mingle.mingledemo.dto.post

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.domain.post.entity.PostLike
import community.mingle.mingledemo.domain.post.entity.PostScrap
import community.mingle.mingledemo.domain.report.entity.PostReport
import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class PostDto(
    val id: Long?,
    val memberDto: MemberDto,
    val title: String,
    val content: String,
    val boardType: BoardType,
    val categoryType: CategoryType,
    val anonymous: Boolean,
    val fileAttached: Boolean,
    val status: ContentStatusType,
    val viewCount: Int,
    val likes: List<PostLike>,
    val scraps: List<PostScrap>,
    val comments: List<Comment>,
    val reports: List<PostReport>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
)
