//package com.lixiaoming.recycleviewtest.utils;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//
//import com.rt.BASE64Decoder;
//import com.rt.BASE64Encoder;
////import org.apache.commons.logging.Log;
////import org.apache.commons.logging.LogFactory;
////import sun.misc.BASE64Decoder;
////import sun.misc.BASE64Encoder;
//
//
///**
//* @ClassName: RSAEncrypt
//* @Description: RSA加解密类
//* @author: ms
//* @date: 2017-11-22
//*/
//@SuppressWarnings("restriction")
//public class RSAEncryptor {
//
//	private static Log logger = LogFactory.getLog(RSAEncryptor.class);
//
//	/**
//	 * RSA最大加密明文大小
//	 */
//	private static final int MAX_ENCRYPT_BLOCK = 117;
//
//	/**
//	 * RSA最大解密密文大小
//	 */
//	private static final int MAX_DECRYPT_BLOCK = 128;
//
//	/**
//	 * 私钥
//	 */
//	private static RSAPrivateKey privateKey;
//
//	/**
//	 * 公钥
//	 */
//	private static RSAPublicKey publicKey;
//
//	/**
//	 * 字节数据转字符串专用集合
//	 */
//	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
//			'f' };
//
//	static {
//		try {
//			String path = RSAEncryptor.class.getResource("").getPath();
//			//公钥
//			String publicKeyStr = loadPublicKeyByFile(path);
//			loadPublicKey(publicKeyStr);
//			//私钥
//			String privateKeyStr = loadPrivateKeyByFile(path);
//			loadPrivateKey(privateKeyStr);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//
//	}
//
//	public static void main(String[] args) throws Exception {
//
//		try {
//			String test = "拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAud拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAud拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些拟发布内容是否首发=planstoreIs拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,Starting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布内容是否首发=planstoreIsStarting,拟发布内容首发在何时何地=planstoreStartingLocation,拟发布主题及发布内容概要=planstoreSummary,希望发布时长=planstoreDuration,希望拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,拟发布的专业观众由哪些人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,ienceFormMessage,人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,ienceFormMessage,人组成=planstoreAudienceForm,拟发布的专业观众的人员组成其他描述=planstoreAudienceFormMessage,";
//			String testRSAEnWith64 = RSAEncryptor.encryptWithBase64(test);
//			String testRSADeWith64 = RSAEncryptor.decryptWithBase64(testRSAEnWith64);
//			System.out.println("\n加密后: \n" + testRSAEnWith64);
//			System.out.println("\n解密后: \n" + testRSADeWith64);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static String decryptWithBase64(String base64String) throws Exception {
//		byte[] binaryData = decrypt(getPrivateKey(), new BASE64Decoder().decodeBuffer(base64String));
//		String string = new String(binaryData);
//		return string;
//	}
//
//	public static String encryptWithBase64(String string) throws Exception {
//		byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
//		String base64String = new BASE64Encoder().encodeBuffer(binaryData);
//		return base64String;
//	}
//
//	public String getKeyFromFile(String filePath) throws Exception {
//		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
//		String line = null;
//		List<String> list = new ArrayList<String>();
//		while ((line = bufferedReader.readLine()) != null) {
//			list.add(line);
//		}
//		// remove the firt line and last line
//		StringBuilder stringBuilder = new StringBuilder();
//		for (int i = 1; i < list.size() - 1; i++) {
//			stringBuilder.append(list.get(i)).append("\r");
//		}
//
//		String key = stringBuilder.toString();
//		return key;
//	}
//
//	/**
//     * 从文件中输入流中加载公钥
//     *
//     * @param path
//     *            公钥输入流
//     * @throws Exception
//     *             加载公钥时产生的异常
//     */
//    public static String loadPublicKeyByFile(String path) throws Exception {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path + "/keys/publicKey.keystore"));
//            String readLine = null;
//            StringBuilder sb = new StringBuilder();
//            while ((readLine = br.readLine()) != null) {
//                sb.append(readLine);
//            }
//            br.close();
//            return sb.toString();
//        } catch (IOException e) {
//            throw new Exception("公钥数据流读取错误");
//        } catch (NullPointerException e) {
//            throw new Exception("公钥输入流为空");
//        }
//    }
//
//    /**
//     * 从文件中加载私钥
//     *
//     * @param path
//     *            私钥文件名
//     * @return 是否成功
//     * @throws Exception
//     */
//    public static String loadPrivateKeyByFile(String path) throws Exception {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path + "/keys/privateKey.keystore"));
//            String readLine = null;
//            StringBuilder sb = new StringBuilder();
//            while ((readLine = br.readLine()) != null) {
//                sb.append(readLine);
//            }
//            br.close();
//            return sb.toString();
//        } catch (IOException e) {
//            throw new Exception("私钥数据读取错误");
//        } catch (NullPointerException e) {
//            throw new Exception("私钥输入流为空");
//        }
//    }
//
//	/**
//	 * 从字符串中加载公钥
//	 *
//	 * @param publicKeyStr
//	 *            公钥数据字符串
//	 * @throws Exception
//	 *             加载公钥时产生的异常
//	 */
//	public static void loadPublicKey(String publicKeyStr) throws Exception {
//		try {
//			BASE64Decoder base64Decoder = new BASE64Decoder();
//			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//			publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//		} catch (NoSuchAlgorithmException e) {
//			throw new Exception("无此算法");
//		} catch (InvalidKeySpecException e) {
//			throw new Exception("公钥非法");
//		} catch (IOException e) {
//			throw new Exception("公钥数据内容读取错误");
//		} catch (NullPointerException e) {
//			throw new Exception("公钥数据为空");
//		}
//	}
//
//	/**
//	 * 从字符串中加载私钥
//	 *
//	 * @param privateKeyStr
//	 *            私钥数据字符串
//	 * @throws Exception
//	 *             加载私钥时产生的异常
//	 */
//	public static void loadPrivateKey(String privateKeyStr) throws Exception {
//		try {
//			BASE64Decoder base64Decoder = new BASE64Decoder();
//			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
//			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//		} catch (NoSuchAlgorithmException e) {
//			throw new Exception("无此算法");
//		} catch (InvalidKeySpecException e) {
//			e.printStackTrace();
//			throw new Exception("私钥非法");
//		} catch (IOException e) {
//			throw new Exception("私钥数据内容读取错误");
//		} catch (NullPointerException e) {
//			throw new Exception("私钥数据为空");
//		}
//	}
//
//	/**
//	 * 加密过程
//	 *
//	 * @param publicKey
//	 *            公钥
//	 * @param plainTextData
//	 *            明文数据
//	 * @return
//	 * @throws Exception
//	 *             加密过程中的异常信息
//	 */
//	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
//		if (publicKey == null) {
//			throw new Exception("加密公钥为空, 请设置");
//		}
//		Cipher cipher = null;
//		try {
//			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
//			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//			/** 执行分组加密操作 */
//			int inputLen = plainTextData.length;
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			int offSet = 0;
//			byte[] cache;
//			int i = 0;
//			// 对数据分段加密
//			while (inputLen - offSet > 0) {
//				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//					cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);
//				} else {
//					cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
//				}
//				out.write(cache, 0, cache.length);
//				i++;
//				offSet = i * MAX_ENCRYPT_BLOCK;
//			}
//			byte[] encryptedData = out.toByteArray();
//			out.close();
//			return encryptedData;
//		} catch (NoSuchAlgorithmException e) {
//			throw new Exception("无此加密算法");
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			return null;
//		} catch (InvalidKeyException e) {
//			throw new Exception("加密公钥非法,请检查");
//		} catch (IllegalBlockSizeException e) {
//			throw new Exception("明文长度非法");
//		} catch (BadPaddingException e) {
//			throw new Exception("明文数据已损坏");
//		}
//	}
//
//	/**
//	 * 解密过程
//	 *
//	 * @param privateKey
//	 *            私钥
//	 * @param cipherData
//	 *            密文数据
//	 * @return 明文
//	 * @throws Exception
//	 *             解密过程中的异常信息
//	 */
//	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
//		if (privateKey == null) {
//			throw new Exception("解密私钥为空, 请设置");
//		}
//		Cipher cipher = null;
//		try {
//			cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
//			cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//			int inputLen = cipherData.length;
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			int offSet = 0;
//			byte[] cache;
//			int i = 0;
//			// 对数据分段解密
//			while (inputLen - offSet > 0) {
//				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//					cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
//				} else {
//					cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
//				}
//				out.write(cache, 0, cache.length);
//				i++;
//				offSet = i * MAX_DECRYPT_BLOCK;
//			}
//			byte[] decryptedData = out.toByteArray();
//			out.close();
//			return decryptedData;
//		} catch (NoSuchAlgorithmException e) {
//			throw new Exception("无此解密算法");
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			return null;
//		} catch (InvalidKeyException e) {
//			throw new Exception("解密私钥非法,请检查");
//		} catch (IllegalBlockSizeException e) {
//			throw new Exception("密文长度非法");
//		} catch (BadPaddingException e) {
//			throw new Exception("密文数据已损坏");
//		}
//	}
//
//	/**
//	 * 字节数据转十六进制字符串
//	 *
//	 * @param data
//	 *            输入数据
//	 * @return 十六进制内容
//	 */
//	public static String byteArrayToString(byte[] data) {
//		StringBuilder stringBuilder = new StringBuilder();
//		for (int i = 0; i < data.length; i++) {
//			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
//			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
//			// 取出字节的低四位 作为索引得到相应的十六进制标识符
//			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
//			if (i < data.length - 1) {
//				stringBuilder.append(' ');
//			}
//		}
//		return stringBuilder.toString();
//	}
//
//
//	/**
//	 * 获取私钥
//	 *
//	 * @return 当前的私钥对象
//	 */
//	public static RSAPrivateKey getPrivateKey() {
//		return privateKey;
//	}
//
//	/**
//	 * 获取公钥
//	 *
//	 * @return 当前的公钥对象
//	 */
//	public static RSAPublicKey getPublicKey() {
//		return publicKey;
//	}
//
//}