package com.github.wdestroier.mcflurry;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import com.github.wdestroier.mcflurry.coupon.factory.McFlurryCouponFactory;

public class Main {

	public static void main(String... args) {
		var couponFactory = new McFlurryCouponFactory();

		var coupon = couponFactory.createCoupon();

		System.out.println(coupon.getEncodedImage());

		try {
			var imageFileName = "Coupon-" + UUID.randomUUID().toString();
			var imageFile = File.createTempFile(imageFileName, ".png");

			var stream = new FileOutputStream(imageFile);
			stream.write(Base64.getDecoder().decode(coupon.getEncodedImage()));
			stream.close();

			Desktop.getDesktop().open(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
