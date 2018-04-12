package com.lixiaoming.recycleviewtest.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by lixiaoming on 2017/10/30.
 * 
 * DES加密 目的：登录是避免明文传输
 */

public class DESUtils {
    /**加解密需要的key*/
    private static String SecretKeySpec_Key = "95880288";
    /**加解密需要的向量iv*/
    private static byte[] IvParameterSpec_Iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /** 加密 */
    public static String encrypt(String encryptdData) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 创建密码
            SecretKeySpec key = new SecretKeySpec(SecretKeySpec_Key.getBytes(), "DES");
            IvParameterSpec iv = new IvParameterSpec(IvParameterSpec_Iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(encryptdData.getBytes());
            return new BASE64Encoder().encode(result); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * des解密
     * @param decryptString
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString)  {

        byte[] byteMi = null;

        try {
            byteMi = new BASE64Decoder().decodeBuffer(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(IvParameterSpec_Iv);
            SecretKeySpec key = new SecretKeySpec(SecretKeySpec_Key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData,"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
