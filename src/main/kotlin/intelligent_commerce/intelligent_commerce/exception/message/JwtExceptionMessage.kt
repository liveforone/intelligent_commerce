package intelligent_commerce.intelligent_commerce.exception.message

enum class JwtExceptionMessage(val status: Int, val message: String) {
    TOKEN_IS_NULL(404, "Token Is Null"), INVALID_Exception_MESSAGE(403, "Invalid JWT Token"),
    EXPIRED_Exception_MESSAGE(400, "Expired JWT Token"), UNSUPPORTED_Exception_MESSAGE(403, "Unsupported JWT Token"),
    EMPTY_CLAIMS(404, "JWT claims string is empty.")
}