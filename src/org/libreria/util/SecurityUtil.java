package org.libreria.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad de seguridad encargada de la encriptación de contraseñas 
 * utilizando el algoritmo de cifrado SHA-256.
 * 
 * @author Esteban Interiano
 * @version 1.0.0
 */
public class SecurityUtil {
    
    /**
     * Encripta una contraseña en texto plano utilizando el algoritmo SHA-256
     * y devuelve su representación en formato hexadecimal.
     * 
     * @param password La contraseña en texto plano que se desea hashear.
     * @return Una cadena de texto con el hash SHA-256 resultante en formato hexadecimal.
     */
    public static String hashSHA256(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
    
}
