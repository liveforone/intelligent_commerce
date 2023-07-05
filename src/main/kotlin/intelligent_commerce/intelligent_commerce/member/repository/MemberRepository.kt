package intelligent_commerce.intelligent_commerce.member.repository

import intelligent_commerce.intelligent_commerce.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository