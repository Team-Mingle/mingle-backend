package community.mingle.mingledemo.dto.post

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.PostLike
import community.mingle.mingledemo.domain.post.entity.PostScrap
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class PostDto(
        val id: Long?,
        val member: Member,
        val title: String,
        val content: String,
        val boardType: BoardType,
        val categoryType: CategoryType,
        val anonymous: Boolean,
        val fileAttached: Boolean,
        val status: ContentStatusType,
        val viewCount: Int,
        val postLikes: List<PostLike>,
        val postScraps: List<PostScrap>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val deletedAt: LocalDateTime?,
)
