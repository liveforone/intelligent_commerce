package intelligent_commerce.intelligent_commerce.member.service.command

import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.member.domain.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService @Autowired constructor(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return createUserDetails(memberRepository.findOneByEmail(email))
    }

    private fun createUserDetails(member: Member): UserDetails {
        return when (member.auth) {
            Role.ADMIN -> {
                createAdmin(member)
            }
            Role.SELLER -> {
                createDriver(member)
            }
            else -> {
                createMember(member)
            }
        }
    }

    private fun createAdmin(member: Member): UserDetails {
        val adminRole = "ADMIN"
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(adminRole)
            .build()
    }

    private fun createDriver(member: Member): UserDetails {
        val driverRole = "DRIVER"
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(driverRole)
            .build()
    }

    private fun createMember(member: Member): UserDetails {
        val memberRole = "MEMBER"
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(memberRole)
            .build()
    }
}