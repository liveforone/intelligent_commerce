package intelligent_commerce.intelligent_commerce.member.service.query

import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberQueryService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun getMemberByIdentity(identity: String) = memberRepository.findOneDtoByIdentity(identity)
}