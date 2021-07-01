package com.github.wdestroier.mcflurries.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {

	private static final long serialVersionUID = 1L;

	private Image image, disabledImage;
	private ImageObserver observer, disabledObserver;

	public ImageButton(ImageIcon icon, ImageIcon disabledIcon) {
		image = icon.getImage();
		observer = icon.getImageObserver();
		disabledImage = disabledIcon.getImage();
		disabledObserver = disabledIcon.getImageObserver();

		setContentAreaFilled(false);
	}

	@Override
	public void paint(Graphics g) {
		if (isEnabled()) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), observer);
		} else {
			g.drawImage(disabledImage, 0, 0, getWidth(), getHeight(), disabledObserver);
		}
	}

}
