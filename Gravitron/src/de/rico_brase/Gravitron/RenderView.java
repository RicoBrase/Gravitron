package de.rico_brase.Gravitron;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class RenderView extends JFrame{
	
	private static final long serialVersionUID = -7707830433189857592L;
	String math;
	
	public RenderView(String title, String math){
		super(title);
		this.math = math;
		this.setLocationRelativeTo(null);
		render();
	}
	
	public void render(){
		BufferedImage b = null;
		try{
			
			TeXFormula fomule = new TeXFormula(math);
			 TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
			 b = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			 ti.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		JLabel result = new JLabel(new ImageIcon(b));
		this.getContentPane().add(result);
		this.pack();
		this.setVisible(true);
	}

}
