import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class CookieMain extends JFrame {

	JLabel counterLabel, perSecLabel;
	JButton button1, button2, button3;
	int cookieCounter, timerSpeed, cursorNumer, cursorPrice, grandmaNumber, grandmaPrice, bakeryNumber, bakeryPrice;
	double perSecond;
	boolean timerOn;
	Font font1, font2;
	CookieHandler cHandler = new CookieHandler();
	MouseHandler mHandler = new MouseHandler();
	Timer timer;
	JTextArea messageText;
	static final Color BACKGROUND_COLOR = new Color(45, 114, 143);

	public static void main(String[] args) {

		CookieMain a = new CookieMain();

	}

	public CookieMain() {
		cookieCounter = 0;
		perSecond = 0;
		cursorNumer = 0;
		cursorPrice = 10;
		grandmaNumber = 0;
		grandmaPrice = 50;
		bakeryNumber = 0;
		bakeryPrice = 250;
		timerOn = false;
		createFont();
		createUI();
	}

	public void createFont() {
		font1 = new Font("Verdana", Font.PLAIN, 32);
		font2 = new Font("Comic Sans MS", Font.PLAIN, 16);
	}

	public void createUI() {

		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(BACKGROUND_COLOR);
		this.setLocationRelativeTo(null);

		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 800, 600);
		gamePanel.setBackground(BACKGROUND_COLOR);
		gamePanel.setLayout(null);
		this.add(gamePanel);

		JPanel cookiePanel = new JPanel();
		cookiePanel.setBounds(100, 200, 200, 220);
		cookiePanel.setBackground(BACKGROUND_COLOR);

		ImageIcon cookieIcon = new ImageIcon("res/cookie.png");

		JButton cookieButton = new JButton();
		cookieButton.setBackground(null);
		cookieButton.setFocusPainted(false);
		cookieButton.setBorder(null);
		cookieButton.setIcon(cookieIcon);
		cookieButton.addActionListener(cHandler);
		cookieButton.setActionCommand("cookie");
		cookieButton.setBorderPainted(false);
		cookiePanel.add(cookieButton);

		JPanel counterPanel = new JPanel();
		counterPanel.setBounds(100, 100, 250, 100);
		counterPanel.setBackground(BACKGROUND_COLOR);
		counterPanel.setLayout(new GridLayout(2, 1));

		counterLabel = new JLabel(cookieCounter + " cookies!");
		counterLabel.setForeground(Color.white);
		counterLabel.setFont(font1);
		counterPanel.add(counterLabel);

		perSecLabel = new JLabel();
		perSecLabel.setForeground(Color.white);
		perSecLabel.setFont(font2);
		counterPanel.add(perSecLabel);

		JPanel itemPanel = new JPanel();
		itemPanel.setBounds(450, 170, 250, 250);
		itemPanel.setBackground(BACKGROUND_COLOR);
		itemPanel.setLayout(new GridLayout(3, 1));
		gamePanel.add(itemPanel);

		button1 = new JButton("Cursor");
		button1.setFont(font1);
		button1.setFocusPainted(false);
		button1.addActionListener(cHandler);
		button1.setActionCommand("Cursor");
		button1.addMouseListener(mHandler);
		itemPanel.add(button1);

		button2 = new JButton("?");
		button2.setFont(font1);
		button2.setFocusPainted(false);
		button2.addActionListener(cHandler);
		button2.setActionCommand("Grandma");
		button2.addMouseListener(mHandler);
		button2.setEnabled(false);
		itemPanel.add(button2);

		button3 = new JButton("?");
		button3.setFont(font1);
		button3.setFocusPainted(false);
		button3.addActionListener(cHandler);
		button3.setActionCommand("Bakery");
		button3.addMouseListener(mHandler);
		button3.setEnabled(false);
		itemPanel.add(button3);

		JPanel messagePanel = new JPanel();
		messagePanel.setBounds(450, 70, 250, 100);
		messagePanel.setBackground(BACKGROUND_COLOR);

		messageText = new JTextArea();
		messageText.setBounds(150, 70, 250, 150);
		messageText.setText("Welcome to Cookie Clicker!");
		messageText.setBackground(BACKGROUND_COLOR);
		messageText.setForeground(Color.white);
		messageText.setFont(font2);
		messageText.setLineWrap(true);
		messageText.setWrapStyleWord(true);
		messageText.setEditable(false);
		messagePanel.add(messageText);

		gamePanel.add(messagePanel);
		gamePanel.add(cookiePanel);
		gamePanel.add(counterPanel);

		this.setVisible(true);

	}

	public void setTimer() {
		timer = new Timer(timerSpeed, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cookieCounter++;
				counterLabel.setText(cookieCounter + " cookies! ");

				if (!button2.isEnabled() && cookieCounter >= grandmaPrice) {
					button2.setEnabled(true);
					button2.setText("Grandma (" + grandmaNumber + ")");
				}

				if (!button3.isEnabled() && cookieCounter >= bakeryPrice) {
					button3.setEnabled(true);
					button3.setText("Bakery (" + bakeryNumber + ")");
				}
			}
		});

	}

	public void timerUpdate() {
		if (!timerOn) {
			timerOn = true;
		} else if (timerOn) {
			timer.stop();
		}

		double speed = 1 / perSecond * 1000;
		timerSpeed = (int) Math.round(speed);
		String s = String.format("% .1f", perSecond);
		perSecLabel.setText("per second: " + s);

		setTimer();
		timer.start();
	}

	public class CookieHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String actionString = event.getActionCommand();
			switch (actionString) {
			case "cookie": {
				cookieCounter++;
				counterLabel.setText(cookieCounter + " cookies!");
				break;
			}
			case "Cursor": {
				if (cookieCounter >= cursorPrice) {
					cookieCounter -= cursorPrice;
					cursorPrice += 10;
					counterLabel.setText(cookieCounter + " cookies!");
					messageText.setText("Cursor\n[Price:  " + cursorPrice + "]\nAutoclicks once every 5 seconds");

					cursorNumer++;
					button1.setText("Cursor (" + cursorNumer + ")");
					perSecond += 0.2;
					timerUpdate();
				} else {
					messageText.setText("you need more cookies!");
				}
				break;

			}
			case "Grandma": {
				if (cookieCounter >= grandmaPrice) {
					cookieCounter -= grandmaPrice;
					grandmaPrice += 20;
					counterLabel.setText(cookieCounter + " cookies!");
					messageText.setText(
							"Grandma\n[Price:  " + grandmaPrice + "]\nEach grandma produces 1 cookie every second");

					grandmaNumber++;
					button2.setText("Grandma (" + grandmaNumber + ")");
					perSecond += 1;
					timerUpdate();
				} else {
					messageText.setText("you need more cookies!");
				}
				break;

			}
			case "Bakery": {
				if (cookieCounter >= bakeryPrice) {
					cookieCounter -= bakeryPrice;
					bakeryPrice += 50;
					counterLabel.setText(cookieCounter + " cookies!");

					bakeryNumber++;
					button3.setText("Bakery (" + bakeryNumber + ")");
					perSecond += 5;
					timerUpdate();
				} else {
					messageText.setText("you need more cookies!");
				}
				break;

			}

			}
		}
	}

	public class MouseHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			JButton button = (JButton) e.getSource();
			if (button == button1) {
				messageText.setText("Cursor\n[Price:  " + cursorPrice + "]\nAutoclicks once every 10 seconds");
			}
			if (button == button2) {
				if (button2.isEnabled()) {
					messageText.setText(
							"Grandma\n[Price:  " + grandmaPrice + "]\nEach grandma produces 1 cookie every second");
				} else {
					messageText.setText("Button locked");
				}
			}

			if (button == button3) {
				if (button3.isEnabled()) {
					messageText.setText(
							"Bakery\n[Price:  " + bakeryPrice + "]\nEach bakery produces 5 cookie every second");
				} else {
					messageText.setText("Button locked");
				}
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			messageText.setText(null);

		}

	}

}
