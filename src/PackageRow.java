import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PackageRow extends JPanel {
    static final int ICON_SIZE = 64;
    private int elementCount;

    PackageRow(String packageName, int elementCount) {
        this.elementCount = elementCount;

        setOpaque(false);
        setBounds(0,elementCount*70, 2000, MainPackagePanel.ROWHEIGHT);
        setLayout(null);

        PackageIconPanel iconPanel = new PackageIconPanel(packageName);
        add(iconPanel);
    }

    void updateLocationY(){
        setLocation(0, (int) (elementCount*70+((MainPackagePanel)getParent()).getScrollOffsetY()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(0.0f));
        MainPackagePanel mpp = (MainPackagePanel)getParent();

        int squareWidth = (int)(mpp.getZoomScale()*50)+100;
        int squareHeight = 32;
        int cornerSize = 10;

        g2.setPaint(new Color(90, 90, 90));
        g2.fill(new Rectangle2D.Double(0,0, getBounds().height, getBounds().height));

        g2.setPaint(new Color(143, 20, 130));
        g2.fill(new RoundRectangle2D.Double(cornerSize+1000+mpp.getDragX(), squareHeight/2f, squareWidth, squareHeight, cornerSize, cornerSize));

        super.paintComponent(g);
    }
}