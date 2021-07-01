package com.github.wdestroier.mcflurries.view;

import java.awt.Color;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.github.wdestroier.mcflurries.controller.CouponController;

public class CouponView {

	private CouponController couponController;

	private JFrame frame;
	private JLabel couponImageLabel;
	private ImageButton saveButton;
	private ImageButton generateButton;
	private JFileChooser fileChooser;

	public CouponView() {
		couponController = new CouponController(this);

		frame = new JFrame();

		frame.setBounds(0, 0, 470, 395);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("McFlurry Generator");
		frame.setIconImage(getIcon("/assets/small_mcflurry.png").getImage());
		frame.getContentPane().setLayout(null);

		couponImageLabel = new JLabel();
		couponImageLabel.setBounds(10, 11, 434, 281);
		couponImageLabel.setBorder(new LineBorder(Color.WHITE, 2, true));
		couponImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setCouponImage(getIcon("/assets/blank_coupon.png"));
		frame.getContentPane().add(couponImageLabel);

		var generateIcon = getIcon("/assets/generate.png");
		var disabledGenerateIcon = getIcon("/assets/disabled_generate.png");
		generateButton = new ImageButton(generateIcon, disabledGenerateIcon);
		generateButton.setBounds(150, 298, 54, 48);
		generateButton.addActionListener(e -> {
			generateButton.setEnabled(false);
			update(generateButton);

			try {
				couponController.generateCoupon();

				saveButton.setEnabled(true);
				update(saveButton);
			} finally {
				generateButton.setEnabled(true);
				update(generateButton);
			}
		});
		frame.getContentPane().add(generateButton);

		var saveIcon = getIcon("/assets/save.png");
		var disabledSaveIcon = getIcon("/assets/disabled_save.png");
		saveButton = new ImageButton(saveIcon, disabledSaveIcon);
		saveButton.setBounds(251, 298, 64, 48);
		saveButton.setEnabled(false);
		saveButton.addActionListener(e -> {
			var parentFile = fileChooser.getSelectedFile();

			if (parentFile != null && !parentFile.isDirectory()) {
				parentFile = parentFile.getParentFile();
			}

			var randomInt = Math.abs(ThreadLocalRandom.current().nextInt());
			var fileName = "Coupon-" + randomInt + ".png";
			var selectedFile = new File(parentFile, fileName);

			fileChooser.setSelectedFile(selectedFile);

			var option = fileChooser.showSaveDialog(saveButton);

			if (option == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();

				couponController.saveCoupon(selectedFile);
			}
		});
		frame.getContentPane().add(saveButton);

		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save coupon");
	}

	private void update(JComponent component) {
		component.update(component.getGraphics());
	}

	private ImageIcon getIcon(String name) {
		return new ImageIcon(getClass().getResource(name));
	}

	public Icon getCouponImage() {
		return couponImageLabel.getIcon();
	}

	public void setCouponImage(Icon icon) {
		couponImageLabel.setIcon(icon);
	}

	public void open() {
		frame.setVisible(true);
	}

	public void close() {
		frame.setVisible(false);

		frame.dispose();
	}

}
