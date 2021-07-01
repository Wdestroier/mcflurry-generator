package com.github.wdestroier.mcflurry.coupon.factory;

import com.github.wdestroier.mcflurry.browser.ChromeWebBrowser;
import com.github.wdestroier.mcflurry.browser.WebBrowser;
import com.github.wdestroier.mcflurry.coupon.McDonaldsCoupon;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CouponFactory {

	protected WebBrowser browser;
	protected Lorem lorem;

	public CouponFactory() {
		this(new ChromeWebBrowser(), LoremIpsum.getInstance());
	}

	public abstract McDonaldsCoupon createCoupon();

	protected void send(String xpath, CharSequence... keys) {
		browser.send(xpath, keys);
	}

	protected void click(String xpath) {
		browser.click(xpath);
	}

}
