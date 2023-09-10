package community.mingle.mingledemo.exception.base

import software.amazon.awssdk.awscore.AwsResponse

class AwsException(
    val requestId: String,
    val statusCode: Int,
    val statusText: String?,
) : Exception("$requestId fail with $statusCode: $statusText") {
    constructor(response: AwsResponse) : this(
        response.responseMetadata().requestId(),
        response.sdkHttpResponse().statusCode(),
        response.sdkHttpResponse().statusText().orElse(null),
    )
}
