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
                createSeller(member)
            }
            else -> {
                createMember(member)
            }
        }
    }

    private fun createAdmin(member: Member): UserDetails {
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(Role.ADMIN.name)
            .build()
    }

    private fun createSeller(member: Member): UserDetails {
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(Role.SELLER.name)
            .build()
    }

    private fun createMember(member: Member): UserDetails {
        return User.builder()
            .username(member.identity)
            .password(member.password)
            .roles(Role.MEMBER.name)
            .build()
    }
}