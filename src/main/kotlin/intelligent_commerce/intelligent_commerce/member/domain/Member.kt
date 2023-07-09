package intelligent_commerce.intelligent_commerce.member.domain

import intelligent_commerce.intelligent_commerce.converter.RoleConverter
import intelligent_commerce.intelligent_commerce.exception.exception.MemberException
import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.constant.MemberConstant
import intelligent_commerce.intelligent_commerce.member.domain.util.PasswordUtil
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
class Member private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(unique = true, nullable = false) val identity: String,
    @Column(nullable = false) var email: String,
    @Column(nullable = false) var pw: String,
    @Column(nullable = false) var bankbookNum: String,
    @Convert(converter = RoleConverter::class) @Column(nullable = false) val auth: Role,
    @Embedded var address: Address
) : UserDetails {

    companion object {
        private fun createIdentity(): String = UUID.randomUUID().toString()

        private fun isAdmin(email: String): Boolean = (email == MemberConstant.ADMIN_EMAIL)

        fun create(email: String, pw: String, bankbookNum: String, auth: Role, address: Address): Member {
            return Member(
                id = null,
                identity = createIdentity(),
                email,
                pw = PasswordUtil.encodePassword(pw),
                bankbookNum,
                auth = if (isAdmin(email)) Role.ADMIN else auth,
                address
            )
        }
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePw(newPassword: String, oldPassword: String) {
        if (!PasswordUtil.isMatchPassword(oldPassword, pw)) throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
        pw = PasswordUtil.encodePassword(newPassword)
    }

    fun updateBankbookNum(bankbookNum: String) {
        this.bankbookNum = bankbookNum
    }

    fun updateAddress(city: String, roadNum: String, detail: String) {
        this.address = Address(city, roadNum, detail)
    }

    fun isSeller(): Boolean {
        return auth == Role.SELLER
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf<GrantedAuthority>(SimpleGrantedAuthority(auth.auth))
    }

    override fun getPassword(): String = pw

    override fun getUsername(): String = identity

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}