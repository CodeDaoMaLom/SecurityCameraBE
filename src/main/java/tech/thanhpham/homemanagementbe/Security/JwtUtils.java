package tech.thanhpham.homemanagementbe.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import tech.thanhpham.homemanagementbe.Security.securityConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils implements Serializable {
    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(securityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }


    public String generateToken(UUID uuid) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(uuid))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + securityConstants.JWT_EXP))
                .signWith(SignatureAlgorithm.HS512, securityConstants.JWT_SECRET)
                .compact();
        return token;
    }

    //validate token
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
