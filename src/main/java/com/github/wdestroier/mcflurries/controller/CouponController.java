package com.github.wdestroier.mcflurries.controller;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.github.wdestroier.mcflurries.model.CouponFactory;
import com.github.wdestroier.mcflurries.view.CouponView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CouponController {

	private CouponView couponView;
	private final CouponFactory couponFactory = new CouponFactory();
	private final Base64.Decoder base64Decoder = Base64.getDecoder();

	public void generateCoupon() {
		var coupon = couponFactory.create();

		var decodedImage = base64Decoder.decode(coupon.getEncodedImage());
		var image = new ImageIcon(decodedImage);

		couponView.setCouponImage(image);
	}

	public void saveCoupon(File file) {
		var icon = couponView.getCouponImage();

		var image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();

		try {
			ImageIO.write(image, "png", file);
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
