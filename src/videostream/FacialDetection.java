package videostream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FacialDetection {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not detected.");
            return;
        }

        CascadeClassifier faceCascade = new CascadeClassifier("C:/Users/krush/Downloads/haarcascade_frontalcatface.xml");

        Mat frame = new Mat();
        while (true) {
            capture.read(frame);
            if (frame.empty()) {
                System.out.println("No video frame captured.");
                break;
            }

            Mat grayFrame = new Mat();
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(grayFrame, faces);

            for (Rect rect : faces.toArray()) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }

            HighGui.imshow("Facial Recognition", frame);
            if (HighGui.waitKey(30) >= 0) {
                break;
            }
        }
        capture.release();
        HighGui.destroyAllWindows();
    }
}