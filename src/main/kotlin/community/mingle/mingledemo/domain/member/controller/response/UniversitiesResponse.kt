package community.mingle.mingledemo.domain.member.controller.response

import java.io.Serializable

data class UniversitiesResponse(
    val id: Int?,
    val name: String,
    val emailDomain: String,
) : Serializable
