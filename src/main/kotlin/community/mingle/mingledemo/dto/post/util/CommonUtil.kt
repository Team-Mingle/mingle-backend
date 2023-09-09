package community.mingle.mingledemo.dto.post.util

object CommonUtil {

    fun nicknameOrAnonymous(
        nickname: String,
        anonymous: Boolean,
    ): String {
        return if (anonymous) {
            "익명"
        } else nickname
    }

}