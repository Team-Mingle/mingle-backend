import community.mingle.mingledemo.exception.base.AwsException
import software.amazon.awssdk.awscore.AwsResponse

internal fun <T : AwsResponse> T.throwIfError(): T {
    if (!sdkHttpResponse().isSuccessful)
        throw AwsException(this)

    return this
}
