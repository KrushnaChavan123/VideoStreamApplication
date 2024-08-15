package videostream;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Visualization extends JFrame {

    private BufferedImage img;
    private final VideoCapture capture = new VideoCapture(0);
    private VideoPanel videoPanel;

    public Visualization() {
        setTitle("Video Display");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        videoPanel = new VideoPanel();
        add(videoPanel);
        setVisible(true);
    }

    private class VideoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                g.drawImage(img, 0, 0, null);
            }
        }

        public void updateImage(Mat frame) {
            if (!frame.empty()) {
                img = matToBufferedImage(frame);
                repaint();
            }
        }

        private BufferedImage matToBufferedImage(Mat mat) {
            int width = mat.width();
            int height = mat.height();
            BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            byte[] data = new byte[width * height * (int)mat.elemSize()];
            mat.get(0, 0, data);
            bImage.getRaster().setDataElements(0, 0, width, height, data);
            return bImage;
        }
    }

    public static void main(String[] args) {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        Visualization frame = new Visualization();
        VideoCapture capture = new VideoCapture(0);
        Mat mat = new Mat();

        while (capture.read(mat)) {
            frame.videoPanel.updateImage(mat);
            try {
                Thread.sleep(30); // Adjust the sleep time to match your frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        capture.release();
    }
}