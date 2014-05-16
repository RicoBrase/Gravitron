package de.rico_brase.Gravitron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class Gravitron extends JFrame{

	private static final long serialVersionUID = 9207870341382703312L;
	
	public static JTextField height;
	public static JLabel text;
	public static JButton calculate;
	
	public static String time = "t(h) = %ss";
	public static String speed = "v(h) = %s\\frac {m} {s}";
	
	public Gravitron(){
		super("Gravitron");
		this.setResizable(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 350, 120);
		this.setLocationRelativeTo(null);
		init();
	}
	
	public static void main(String[] args){
		new Gravitron();
	}
	
	public void init(){		
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			
		}
		
		Gravitron.height = new JTextField();
		Gravitron.height.setBounds(5, 20, 325, 25);
		Gravitron.height.addKeyListener(new GravitronKeyListener());
		
		Gravitron.text = new JLabel("Die Falhöhe des Objektes in Metern:");
		Gravitron.text.setBounds(5, 5, 325, 15);
		
		Gravitron.calculate = new JButton("Berechnen (sehr CPU-Lastig!)");
		Gravitron.calculate.setBounds(5, 50, 325, 25);
		Gravitron.calculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double h = Double.parseDouble(Gravitron.height.getText());
				double t = Math.sqrt((2.0 * h)/9.81);
				double v = Math.sqrt(2.0 * 9.81 * h);
				BigDecimal timeDec = new BigDecimal(t);
				timeDec = timeDec.setScale(5, BigDecimal.ROUND_HALF_UP);
				BigDecimal speedDec = new BigDecimal(v);
				speedDec = speedDec.setScale(5, BigDecimal.ROUND_HALF_UP);
				new RenderView("Ergebnis - Zeit", String.format(time, timeDec));
				new RenderView("Ergebnis - Geschwindigkeit", String.format(speed, speedDec));
			}
		});
		
		this.add(Gravitron.text);
		this.add(Gravitron.height);
		this.add(Gravitron.calculate);
		
		this.setVisible(true);
		//new RenderView("v(x) = 20\\frac {km} {h}");
	}

}
