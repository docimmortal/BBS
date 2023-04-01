package com.bbs.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUtilities {

	public static String imageToString(byte[] image) {
	   return Base64.encodeBase64String(image);
	}
	
	public static BufferedImage convertFromClob(byte[] byteimage) throws IOException {
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteimage));
		return image;
	}
}
