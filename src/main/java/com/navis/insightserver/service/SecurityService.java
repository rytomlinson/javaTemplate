package com.navis.insightserver.service;

import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.SurveyResponseKeyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Created by darrell-shofstall on 1/3/18.
 */
@Service
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    public static String privateKey = "asdkljhfasdfo87098wq45hljasdgf87";
    public static String surveyModeDemo = ":survey-mode/demo";
    public static String surveyModeNormal = ":survey-mode/normal";

    public static String generateSurveyResponseKey(Long surveyId, Long requestId, String source, String surveyMode) {

        StringBuilder builder = new StringBuilder();
        builder.append(surveyId)
                .append(requestId)
                .append(source)
                .append(surveyMode)
                .append(privateKey);

        String result;
        try {
            result = generateStrongHash(builder.toString());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundExceptionDTO(builder.toString(), "survey.response.key.generation.error");
        } catch (InvalidKeySpecException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundExceptionDTO(builder.toString(), "survey.response.key.generation.error");
        }

        StringJoiner joiner = new StringJoiner(",");
        if (surveyId != null && requestId != null) {
            joiner.add(surveyId.toString()).add(source).add(surveyMode).add(result).add(requestId.toString());
        }

        String encodedString = base64Encode(joiner.toString());

        return encodedString;
    }

    public static SurveyResponseKeyDTO decodeSurveyResponseKey(String key) {
        String decodedString = base64Decode(key);
        Boolean isNoMatch = true;

        String[] parts = decodedString.split(Pattern.quote(","));

        StringBuilder builder = new StringBuilder();
        builder.append(parts[0])
                .append(parts[4])
                .append(parts[1])
                .append(parts[2])
                .append(privateKey);

        try {
            isNoMatch = validatePassword(builder.toString(), parts[3]);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        } catch (InvalidKeySpecException e) {
            log.error(e.getMessage());
        }

        if(isNoMatch) { throw new ResourceNotFoundExceptionDTO(builder.toString(), "survey.response.key.invalid");}

        return convertToDto(parts);

    }

    public static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    public static String base64Decode(String token) {
        byte[] decodedBytes = Base64.decode(token.getBytes());
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

    public static String base64EncodeInt(BigInteger token) {
        byte[] encodedBytes = Base64.encode(token.toByteArray());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    private static String generateStrongHash(String token) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        Integer iterations = 1; //iterations = 1000
        char[] chars = token.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 160);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return base64EncodeInt(BigInteger.valueOf(iterations.intValue())) + "$" + toHex(salt) + "$" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8]; //byte[16]
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split("$");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static SurveyResponseKeyDTO convertToDto(String[] parts) {
        SurveyResponseKeyDTO surveyResponseKeyDTO = new SurveyResponseKeyDTO();
        Long surveyId = Long.valueOf(parts[0]).longValue();
        Long requestId = Long.valueOf(parts[4]).longValue();
        String source = parts[1];
        String surveyMode = parts[2];

        surveyResponseKeyDTO.setSurveyId(surveyId);
        surveyResponseKeyDTO.setRequestId(requestId);
        surveyResponseKeyDTO.setSource(source);
        surveyResponseKeyDTO.setSurveyMode(surveyMode);

        return surveyResponseKeyDTO;
    }

}
