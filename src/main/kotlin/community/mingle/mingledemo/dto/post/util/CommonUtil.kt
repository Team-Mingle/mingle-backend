package community.mingle.mingledemo.dto.post.util

object CommonUtil {

    fun nicknameOrAnonymous(
        nickname: String,
        anonymous: Boolean,
    ): String {
        return if (anonymous) {
            ANONYMOUS_NICKNAME
        } else nickname
    }

    private const val ANONYMOUS_NICKNAME = "익명"

}