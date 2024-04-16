package com.newcord.gatewayservice.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtValidator {

    @Value("${jwt.secret}") // application.properties 등에 보관한다.
    private String secretKey;

    @Value("${jwt.accessTokenValiditySeconds}")
    private long accessTokenValiditySeconds;

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            System.out.println("qqqwww");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    public String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + (accessTokenValiditySeconds * 1000))) // 토큰 유효시각 설정 (30분)
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘과, secret 값
                .compact();
    }

//    public boolean validateToken(String token) {
//        if (StringUtils.isEmpty(token)) {
//            return false;
//        }
//
//        try {
//            System.out.println("qqqwww");
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            // 여기서 다른 유효성 검사를 추가할 수 있습니다. 예를 들어, 만료된 토큰인지 확인할 수 있습니다.
//            System.out.print(claimsJws);
//            return true;
//        } catch (Exception e) {
//            // 유효하지 않은 토큰
//            System.out.println(e.toString());
//            return false;
//        }
//    }
}
