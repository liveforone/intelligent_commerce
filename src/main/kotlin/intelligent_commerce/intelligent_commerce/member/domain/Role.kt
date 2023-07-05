package intelligent_commerce.intelligent_commerce.member.domain

enum class Role(val auth:String) {
    MEMBER("ROLE_MEMBER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN")
}