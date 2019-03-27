package com.example.wangzhixiang.librarytest.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoFactory {
    /*
    wangzhixiang:
    publicKey 加密，privateKey 解密
    publicKey 分发给客户端 用户加密AES密钥
    privateKey 保留在服务器端 解密传来的AES密钥
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Map<String, String> RSAFactory() throws NoSuchAlgorithmException {
        Map<String, String> key = new HashMap<>();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String privateKeyStr = Base64Encode(privateKeyBytes);
        byte[] publicKeyBytes = privateKey.getEncoded();
        String publicKeyStr = Base64Encode(publicKeyBytes);
        key.put("privateKey", privateKeyStr);
        key.put("publicKey", publicKeyStr);
        return key;
//        Log.i("bytes", decode("123"));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] rsaDecrypt(String str, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] inputBytes = Base64Decode(str);
        byte[] privateKeyBytes=Base64Decode(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return cipher.doFinal(inputBytes);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String rsaEncrypt(String str, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        byte[] inputBytes = Base64Decode(str);
        byte[] publicKeyBytes=Base64Decode(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new PKCS8EncodedKeySpec(publicKeyBytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        String outStr = new String(cipher.doFinal(inputBytes));
        return outStr;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String aesDecrypt(String str, byte[] aesKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        SecretKey key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] inputBytes = Base64Decode(str);
        return new String(cipher.doFinal(inputBytes), "utf-8");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String aesEncrypt(String str, byte[] aesKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        SecretKey key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryBytes = cipher.doFinal(str.getBytes("utf-8"));
        return Base64Encode(encryBytes);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String Base64Encode(byte[] key){
        return java.util.Base64.getEncoder().encodeToString(key);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static byte[] Base64Decode(String str){
        return java.util.Base64.getDecoder().decode(str);
    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String decode(String str){
//        byte[] bytes = new byte[]{98,48,48,48,87,48,48,48,48,48};
////        Log.i("bytes", new String(bytes));
//        String test = java.util.Base64.getEncoder().encodeToString(bytes);
//        return new String(Base64.decode(test, Base64.DEFAULT));
//    }
    public static String getAppVerify(Map<String, String> params){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String key:params.keySet()){
            sb.append("\""+key + "\":");
            sb.append("\"" + params.get(key) +"\",");
        }
        String sbtest = sb.substring(0, sb.length()-1) + "}";
        char[] array = sbtest.toCharArray();
        for (int i =0; i<array.length;i++){
            array[i] = (char)((array[i] +32)%128) ;
        }
        return Md5(Md5(String.valueOf(array)));
    }
    private static String Md5(String input){
        StringBuffer result = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(input.getBytes());
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
