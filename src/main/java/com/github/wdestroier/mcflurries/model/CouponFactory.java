package com.github.wdestroier.mcflurries.model;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wdestroier.mcflurries.browser.ChromeWebBrowser;
import com.github.wdestroier.mcflurries.browser.WebBrowser;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CouponFactory {

	private static Logger logger = LoggerFactory.getLogger(CouponFactory.class);

	private WebBrowser browser;
	private Lorem lorem;

	public CouponFactory() {
		this(new ChromeWebBrowser(), LoremIpsum.getInstance());
	}

	public synchronized Coupon create() {
		browser.start();

		browser.get("https://www.mcexperiencia.com/");

		pickCountry();
		fillPurchaseInformation();
		pickOrderPlace();
		pickMealPlace();
		try {
			pickHadChilds();
		} catch (Exception e) {
			logger.info("Pick had childs screen is absent.");
		}
		pickOverallSatisfaction();
		pickSatisfaction();
		pickDeliveredOrderCorrectly();
		pickReturnLikelyhood();
		fillComment();
		pickExperiencedProblems();
		fillPersonalInformation();
		pickReceivePromotions();

		var screenshot = getCouponScreenshot();

		browser.stop();

		return new Coupon(screenshot);
	}

	private void pickCountry() {
		// Pick Brazil
		click("//*[@id=\"surveyQuestions\"]/div[1]/div[2]/div[3]/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void fillPurchaseInformation() {
		// Write any McDonalds CNPJ
		// May be different for other countries
		send("//*[@id=\"InputCNPJ\"]", "42.591.651/1713-80");

		// Change the calendar page to last month
		click("//*[@id=\"ui-datepicker-div\"]/div/a[1]/span");

		// Pick any day from the calendar
		click("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[2]/td[1]/a");

		// Pick the purchase time
		click("//*[@id=\"InputHour\"]/option[15]");
		click("//*[@id=\"InputMinute\"]/option[2]");

		// Agree with the terms and conditions
		click("//*[@id=\"surveyQuestions\"]/div[2]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickOrderPlace() {
		// Pick dessert center
		click("//*[@id=\"FNSR000001\"]/div[2]/div/div[2]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickMealPlace() {
		// Pick outside the restaurant
		click("//*[@id=\"FNSR000002\"]/div[2]/div/div[2]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickHadChilds() {
		// Pick no
		click("//*[@id=\"FNSR000003\"]/div[2]/div/div[2]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickOverallSatisfaction() {
		// Pick highly satisfied
		click("//*[@id=\"FNSR000004\"]/td[2]/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickSatisfaction() {
		// Pick highly satisfied with everthing
		click("//*[@id=\"FNSR000007\"]/td[2]/span");
		click("//*[@id=\"FNSR000006\"]/td[2]/span");
		click("//*[@id=\"FNSR000011\"]/td[2]/span");
		click("//*[@id=\"FNSR000008\"]/td[2]/span");
		try {
			click("//*[@id=\"FNSR000010\"]/td[2]/span");
		} catch (Exception e) {
			logger.info("An item from the pick satisfaction screen is absent.");
		}
		click("//*[@id=\"FNSR000009\"]/td[2]/span");
		click("//*[@id=\"FNSR000012\"]/td[2]/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickDeliveredOrderCorrectly() {
		// Pick yes
		click("//*[@id=\"FNSR000013\"]/td[2]/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickReturnLikelyhood() {
		// Pick highly likely
		click("//*[@id=\"FNSR000015\"]/div[2]/div/div[1]/span/span");

		// Pick the probability of recommending it to a friend (10)
		click("//*[@id=\"FNSR000016\"]/div[2]/div/div[1]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void fillComment() {
		// Type anything that is long enough
		send("/html/body/div[1]/div[3]/div[2]/form/div/div[1]/div[2]/div/div/div[2]"
				+ "/textarea", getRandomComment());

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickExperiencedProblems() {
		// Pick no
		click("//*[@id=\"FNSR000020\"]/td[3]/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void fillPersonalInformation() {
		// Type any name
		send("//*[@id=\"S000036\"]", lorem.getName());
		send("//*[@id=\"S000028\"]", lorem.getLastName());

		// Type any number
		send("//*[@id=\"S000035\"]", lorem.getPhone());

		// Type any email
		var email = lorem.getEmail();

		send("//*[@id=\"S000033\"]", email);
		send("//*[@id=\"S000034\"]", email);

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private void pickReceivePromotions() {
		// Pick no
		click("//*[@id=\"FNSR000040\"]/div[2]/div/div[2]/span/span");

		// Next page
		click("//*[@id=\"NextButton\"]");
	}

	private String getCouponScreenshot() {
		return browser.getScreenshot("//*[@id=\"CouponDemoImage\"]");
	}

	private String getRandomComment() {
		return lorem.getWords(ThreadLocalRandom.current().nextInt(30, 40));
	}

	private void send(String xpath, CharSequence... keys) {
		browser.send(xpath, keys);
	}

	private void click(String xpath) {
		browser.click(xpath);
	}

}
