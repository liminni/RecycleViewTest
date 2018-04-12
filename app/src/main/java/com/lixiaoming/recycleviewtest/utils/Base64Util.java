//package com.lixiaoming.recycleviewtest.utils;
//
//import android.util.Log;
//
//import com.rt.BASE64Decoder;
//import com.rt.BASE64Encoder;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class Base64Util {
//	/***
//	 * 将文件转为base64
//	 *
//	 * @param fileName
//	 * @return
//	 * @throws IOException
//	 */
//	public static String ioToBase64(String fileName) throws IOException {
//		String strBase64 = null;
//		try {
//			InputStream in = new FileInputStream(fileName);
//			// in.available()返回文件的字节长度
//			byte[] bytes = new byte[in.available()];
//			// 将文件中的内容读入到数组中
//			in.read(bytes);
//			strBase64 = new BASE64Encoder().encode(bytes); // 将字节流数组转换为字符串
//			in.close();
//			Log.d("fate","base64转换完成");
//		} catch (FileNotFoundException fe) {
//			fe.printStackTrace();
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//		return strBase64;
//	}
//
//	/***
//	 * 将base64转为音频文件
//	 *
//	 * @param strBase64
//	 * @param  fileName 生成的新文件
//	 * @throws IOException
//	 */
//	public static void base64ToIo(String strBase64, String fileName) throws IOException {
//
//		try {
//			// 解码，然后将字节转换为文件
//			byte[] bytes = new BASE64Decoder().decodeBuffer(strBase64); // 将字符串转换为byte数组
//			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//			byte[] buffer = new byte[1024];
//			FileOutputStream out = new FileOutputStream(fileName);
//			int bytesum = 0;
//			int byteread = 0;
//			while ((byteread = in.read(buffer)) != -1) {
//				bytesum += byteread;
//				out.write(buffer, 0, byteread); // 文件写操作
//			}
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
//	}
//
//	/**========================================================================**/
//	/**
//	 * 将文件转成base64 字符串
//	 * @param
//	 * @return  *
//	 * @throws Exception
//	 */
//	public static String encodeBase64File(String path) throws Exception {
//		File file = new File(path);
//		FileInputStream inputFile = new FileInputStream(file);
//		byte[] buffer = new byte[(int) file.length()];
//		inputFile.read(buffer);
//		inputFile.close();
//		return new BASE64Encoder().encode(buffer);
//
//	}
//
//	/**
//	 * 将base64字符解码保存文件
//	 * @param base64Code
//	 * @param targetPath
//	 * @throws Exception
//	 */
//
//	public static void decoderBase64File(String base64Code, String targetPath)
//			throws Exception {
//		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
//		FileOutputStream out = new FileOutputStream(targetPath);
//		out.write(buffer);
//		out.close();
//
//	}
//
//	/**
//	 * 将base64字符保存文本文件
//	 * @param base64Code
//	 * @param targetPath
//	 * @throws Exception
//	 */
//
//	public static void toFile(String base64Code, String targetPath)
//			throws Exception {
//
//		byte[] buffer = base64Code.getBytes();
//		FileOutputStream out = new FileOutputStream(targetPath);
//		out.write(buffer);
//		out.close();
//	}
//}
