package intelligent_commerce.intelligent_commerce.member.domain

import intelligent_commerce.intelligent_commerce.converter.RoleConverter
import intelligent_commerce.intelligent_commerce.exception.exception.MemberException
import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
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
    @Column(unique = true, nullable = false) val email: String,
    @Column(nullable = false) var pw: String,
    @Column(nullable = false) var bankbookNum: String,
    @Convert(converter = RoleConverter::class) @Column(nullable = false) val auth: Role,
    @Embedded var address: Address
) : UserDetails {

    companion object {
        private fun createIdentity(): String = UUID.randomUUID().toString()

        fun create(email: String, pw: String, bankbookNum: String, auth: Role, address: Address): Member {
            val adminEmail = "admin_intelligent_commerce@gmail.com"

            return Member(
                null,
                createIdentity(),
                email,
                PasswordUtil.encodePassword(pw),
                bankbookNum,
                if (email == adminEmail) Role.ADMIN else auth,
                address
            )
        }
    }

    fun updatePw(newPassword: String, oldPassword: String) {
        if (!PasswordUtil.isMatchPassword(oldPassword, this.pw)) throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
        this.pw = PasswordUtil.encodePassword(newPassword)
    }

    fun updateBankbookNum(bankbookNum: String) {
        this.bankbookNum = bankbookNum
    }

    fun updateAddress(city: String, roadNum: String, detail: String) {
        this.address = Address(city, roadNum, detail)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authList = arrayListOf<GrantedAuthority>()
        authList.add(SimpleGrantedAuthority(auth.auth))
        return authList
    }

    override fun getPassword(): String {
        return pw
    }

    override fun getUsername(): String {
        return identity
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}