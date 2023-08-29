package community.mingle.mingledemo.dto.post

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentStatusType

data class PostDto(
    val id: Long?,
    val member: Member,
    val title: String,
    val content: String,
    val board: BoardType,
    val category: CategoryType,
    val anonymous: Boolean,
    val fileAttached: Boolean,
    val status: ContentStatusType,
    val viewCount: Int,
)
