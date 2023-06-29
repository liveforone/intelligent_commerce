package intelligent_commerce.intelligent_commerce.authenticationInfo

import intelligent_commerce.intelligent_commerce.exception.exception.JwtCustomException
import intelligent_commerce.intelligent_commerce.exception.message.JwtExceptionMessage
import intelligent_commerce.intelligent_commerce.jwt.constant.JwtConstant
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Component
class AuthenticationInfo(@Value(JwtConstant.SECRET_KEY_PATH) secretKey: String) {

    private val key: Key

    init {
        val keyBytes: ByteArray = Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun getUsername(request: HttpServletRequest): String {
        val token = resolveToken(request)
        val claims = parseClaims(token)
        return claims.subject
    }

    fun getAuth(request: HttpServletRequest): String {
        val token = resolveToken(request)
        val claims = parseClaims(token)
        return claims[JwtConstant.CLAIM_NAME].toString()
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(JwtConstant.HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstant.BEARER_TOKEN)) {
            bearerToken.substring(JwtConstant.TOKEN_SUB_INDEX)
        } else{
            throw JwtCustomException(JwtExceptionMessage.EMPTY_CLAIMS)
        }
    }

    private fun parseClaims(accessToken: String): Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
}