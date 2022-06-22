package com.terminus.util;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class ApplicationUtil {

	private static final String SALT = "su$$ourl%@";
	private static final String ISSUER = "SYSTEM";
	
	@Value("${secret}")
	String secret;

	public String encryptPassword(String password) throws Exception {
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] bytes = SALT.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars, bytes, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(bytes) + ":" + toHex(hash);
	}

	public String generateToken(String emailId) {
		Calendar today = Calendar.getInstance();
		Date issue = today.getTime();
		today.add(Calendar.HOUR, 24);
		Date expire = today.getTime();

		Algorithm algorithm = Algorithm.HMAC512(secret);
		return JWT.create().withIssuer(ISSUER).withAudience(emailId).withSubject("login").withIssuedAt(issue)
				.withExpiresAt(expire).sign(algorithm);
	}

	private String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLeft = (array.length * 2) - hex.length();
		if (paddingLeft > 0) {
			return String.format("%s", paddingLeft + "i") + hex;
		} else {
			return hex;
		}
	}
}
