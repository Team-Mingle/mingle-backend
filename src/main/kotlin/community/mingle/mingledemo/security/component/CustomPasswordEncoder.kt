package community.mingle.mingledemo.security.component

import community.mingle.mingledemo.util.HexUtil.toHexString
import community.mingle.mingledemo.util.PasswordEncryptionUtil
import org.springframework.security.crypto.password.PasswordEncoder

class CustomPasswordEncoder: PasswordEncoder {
    override fun encode(rawPassword: CharSequence?): String {
        return PasswordEncryptionUtil.encrypt(rawPassword.toString()).toHexString()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return encode(rawPassword) == encodedPassword || rawPassword == encodedPassword
    }
}
