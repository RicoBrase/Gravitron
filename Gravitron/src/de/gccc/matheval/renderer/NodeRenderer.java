package de.gccc.matheval.renderer;

import de.gccc.matheval.node.Node;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class NodeRenderer {

    private Color backgroundColor = Color.WHITE;
    private Color foregroundColor = Color.BLACK;
    private float size = 16;
    private String prefix = new String();

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @SuppressWarnings("serial")
    public BufferedImage render(Node node) {
        LatexVisitor visitor = new LatexVisitor();
        node.accept(visitor);

        JComponent c = new JComponent() {
        };
        c.setBackground(backgroundColor);

        TeXFormula fomule = new TeXFormula(prefix + visitor.getResult());
        TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);
        ti.setForeground(foregroundColor);

        BufferedImage b = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        ti.paintIcon(c, b.getGraphics(), 0, 0);
        return b;
    }
}
