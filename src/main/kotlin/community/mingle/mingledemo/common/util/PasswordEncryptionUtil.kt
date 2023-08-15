package community.mingle.mingledemo.common.util

import java.security.MessageDigest

object PasswordEncryptionUtil {
    val DEFAULT_PREFERENCE_PASSWORD = encrypt("0000")

    fun encrypt(password: String): ByteArray {
        val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(password.toByteArray())
        return messageDigest.digest()
    }

    infix fun String.isEqualToEncryptedByteArray(passwordByteArray: ByteArray?): Boolean =
        encrypt(password = this).contentEquals(passwordByteArray)

    infix fun String.isNotEqualToEncryptedByteArray(passwordByteArray: ByteArray?): Boolean =
        !isEqualToEncryptedByteArray(passwordByteArray = passwordByteArray)
}
