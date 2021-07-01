package com.github.wdestroier.mcflurry.browser;

public interface WebBrowser {

	void start();

	void stop();

	void get(String url);

	void click(String xpath);

	void send(String xpath, CharSequence... keys);

	String getScreenshot(String xpath);

}
