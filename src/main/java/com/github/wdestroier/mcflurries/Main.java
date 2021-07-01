package com.github.wdestroier.mcflurries;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wdestroier.mcflurries.view.CouponView;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String... args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.info("Could not set system look and feel.");
		}

		EventQueue.invokeLater(() -> {
			var view = new CouponView();
			view.open();
		});
	}

}
