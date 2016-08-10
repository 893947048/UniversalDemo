package com.mc.universal_library.encryption;


import org.apache.commons.codec.binary.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 运算模式CBC。<br>
 * 在CBC模式下使用key,向量iv;<br>
 * 对字符加密时，双方采用的都是UTF-8编码
 * @version 1.0.0
 */

/**目前主流的加密方式有：（对称加密）AES、DES        （非对称加密）RSA、DSA
 * DES算法的入口参数有三个：Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥；Data也为8个字节64位，是要被加密或被解密的数据；Mode为DES的工作方式，有两种：加密或解密。
 　　DES算法是这样工作的：如Mode为加密，则用Key 去把数据Data进行加密， 生成Data的密码形式（64位）作为DES的输出结果；如Mode为解密，则用Key去把密码形式的数据Data解密，还原为Data的明码形式（64位）作为DES的输出结果。在通信网络的两端，双方约定一致的Key，在通信的源点用Key对核心数据进行DES加密，然后以密码形式在公共通信网（如电话网）中传输到通信网络的终点，数据到达目的地后，用同样的Key对密码数据进行解密，便再现了明码形式的核心数据。这样，便保证了核心数据（如PIN、MAC等）在公共通信网中传输的安全性和可靠性。
 　　通过定期在通信网络的源端和目的端同时改用新的Key，便能更进一步提高数据的保密性，这正是现在金融交易网络的流行做法。
 * @author Administrator
 *CBC是工作模式，DES一共有电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）四种模式，
 *
 *3DES是DES加密算法的一种模式，它使用3条64位的密钥对数据进行三次加密。数据加密标准（DES）是美国的一种由来已久的加密标准，它使用对称密钥加密法。

　　3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加密算法。

AES

　　AES(Advanced Encryption Standard)：高级加密标准，是下一代的加密算法标准，速度快，安全级别高。
 */
public class DesUtil {

	// 向量
	private static final byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
	/**
	 * CBC解密
	 *
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] desDecodeCBC(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESKeySpec spec = new DESKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC解密
	 *
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64加密后的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static String desDecodeCBC(String key, String data) throws Exception {
		byte[] _data = Base64.decodeBase64(data);
		byte[] _key = key.getBytes("UTF-8");
		byte[] bOut = desDecodeCBC(_key, _data);

		return new String(bOut, "UTF-8");
	}

	/**
	 * CBC加密
	 *
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] desEncodeCBC(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESKeySpec spec = new DESKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); // 加密方法／运算模式／填充模式
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC加密
	 *
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64加密后的密文
	 * @throws Exception
	 */
	public static String desEncodeCBC(String key, String data) throws Exception {
		byte[] _data = data.getBytes("UTF-8");
		byte[] _key = key.getBytes("UTF-8");
		byte[] bOut = desEncodeCBC(_key, _data);
		return Base64.encodeBase64String(bOut); // Base64加密后的密文
	}

	public static void main(String[] args) throws Exception {

		String key = "werweteryuuiu";
		String data = "学习测试";

		System.out.println("加密解密 测试 ");
		String str1 = desEncodeCBC(key, data);// 加密
		String str2 = desDecodeCBC(key, str1);// 解密
		System.out.println("密文===="+str1);
		System.out.println("明文===="+str2);

	}
}

