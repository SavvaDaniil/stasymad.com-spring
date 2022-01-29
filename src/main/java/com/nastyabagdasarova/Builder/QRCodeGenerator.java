package com.nastyabagdasarova.Builder;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.nastyabagdasarova.Listener.MainEventListener;
import com.nastyabagdasarova.Model.UserQRCode;

public class QRCodeGenerator {

	public static void generateQRCodeImage(UserQRCode userQRCode) {
		
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix;
		try {
			bitMatrix = qrCodeWriter.encode(userQRCode.getMessage(), BarcodeFormat.QR_CODE, 150, 150, hints);
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(userQRCode.getSrc()));
			
			BufferedImage img = ImageIO.read(new File(userQRCode.getSrc()));
			img = makeImageTranslucent(img, 0.25);
			BufferedImage outImage = img;
			ImageIO.write(outImage, "png", new File(userQRCode.getSrc()));
			
			img = null;
			outImage = null;
			
			
		} catch (WriterException | IOException e) {
			MainEventListener mainEventListener = new MainEventListener();
			mainEventListener.updateToEverybody("Ошибка при попытке создать QR-код пользователю",  ""
					+ "<p>Пользователь: ("+userQRCode.getId_of_user()+") </p>");
			mainEventListener = null;
			System.gc();
			e.printStackTrace();
		}
		
		
		hints = null;
		qrCodeWriter = null;
		bitMatrix = null;
		System.gc();
		
	}
	
	
	private static BufferedImage makeImageTranslucent(BufferedImage source, double alpha) {
		
	    BufferedImage target = new BufferedImage(source.getWidth(),
	        source.getHeight(), java.awt.Transparency.TRANSLUCENT);
	    // Get the images graphics
	    Graphics2D g = target.createGraphics();
	    // Set the Graphics composite to Alpha
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	        (float) alpha));
	    // Draw the image into the prepared reciver image
	    g.drawImage(source, null, 0, 0);
	    // let go of all system resources in this Graphics
	    g.dispose();
	    
	    g = null;
	    System.gc();
	    
	    // Return the image
	    return target;
	}
}
