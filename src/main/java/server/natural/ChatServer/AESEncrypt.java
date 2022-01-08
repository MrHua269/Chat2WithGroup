package server.natural.ChatServer;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
//todo compile the codes
public class AESEncrypt {
    private static final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
    private static final java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
    //private static final String key= Utils.config.getString("EncryptKey");
   // private static final String iv = Utils.config.getString("EncryptIV");
    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    public static byte[] encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static byte[] decrypt(byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(encrypted);

            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
public static void main(String[] s){
    try {
        String s1 = new String(encrypt("233"),"UTF-8");
        System.out.println(s1.getBytes(StandardCharsets.UTF_8));
        System.out.println(s1);
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

}

}
