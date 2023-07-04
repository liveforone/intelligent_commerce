package intelligent_commerce.intelligent_commerce.jwt

import intelligent_commerce.intelligent_commerce.exception.exception.JwtCustomException
import intelligent_commerce.intelligent_commerce.exception.message.JwtExceptionMessage
import intelligent_commerce.intelligent_commerce.jwt.constant.JwtConstant
import intelligent_commerce.intelligent_commerce.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(@Value(JwtConstant.SECRET_KEY_PATH) secretKey: String) {

    private val key: Key

    init {
        val keyBytes: ByteArray = Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(authentication: Authentication): TokenInfo {
        val now: Long = Date().time
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(JwtConstant.CLAIM_NAME, authentication.authorities.iterator().next().authority)
            .setExpiration(Date(now + JwtConstant.TWO_HOUR_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        val refreshToken = Jwts.builder()
            .setExpiration(Date(now + JwtConstant.TWO_HOUR_MS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return TokenInfo.create(JwtConstant.BEARER_TOKEN, accessToken, refreshToken)
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)

        val authorities: Collection<GrantedAuthority> =
            claims[JwtConstant.CLAIM_NAME].toString()
                .split(",")
                .map { role: String? -> SimpleGrantedAuthority(role) }
        val principal: UserDetails = User(
            claims.subject,
            "",
            authorities
        )
        return UsernamePasswordAuthenticationToken(
            principal,
            "",
            authorities
        )
    }

    fun validateToken(token: String?): Boolean {
        token ?: throw JwtCustomException(JwtExceptionMessage.TOKEN_IS_NULL)

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            logger().info(JwtExceptionMessage.INVALID_MESSAGE.message)
        } catch (e: ExpiredJwtException) {
            logger().info(JwtExceptionMessage.EXPIRED_MESSAGE.message)
        } catch (e: UnsupportedJwtException) {
            logger().info(JwtExceptionMessage.UNSUPPORTED_MESSAGE.message)
        } catch (e: SecurityException) {
            logger().info(JwtExceptionMessage.INVALID_MESSAGE.message)
        }
        return false
    }

    private fun parseClaims(accessToken: String?): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}