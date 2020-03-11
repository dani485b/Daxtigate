
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
/*
class MyMouseWheelListener implements MouseWheelListener {
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        for (Component component : ((JPanel)e.getComponent()).getComponents()) {
            ImagePanel imgP = (ImagePanel) component;

            Rectangle rect = imgP.getBounds();
            rect.y += e.getWheelRotation()*-30;
            imgP.setBounds(rect);
            imgP.repaint();
        }
    }
}

class MyMouseMotionAdapter extends MouseMotionAdapter {
    private final MainWindow mainW;
    private final JPanel mainPicturePanel;

    MyMouseMotionAdapter(MainWindow mainW, JPanel mainPicturePanel) {
        this.mainW = mainW;
        this.mainPicturePanel = mainPicturePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - mainW.mousePt.x;
        int dy = e.getY() - mainW.mousePt.y;
        mainW.origin.setLocation(mainW.origin.x + dx, mainW.origin.y + dy);
        mainW.mousePt = e.getPoint();
        System.out.println(dy);

        for (Component component : mainPicturePanel.getComponents()) {
            ImagePanel imgP = (ImagePanel) component;

            Rectangle rect = imgP.getBounds();
            rect.y += dy*2;
            imgP.setBounds(rect);
            imgP.repaint();
        }
    }
}*/