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

public class ObjectDetection {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not detected.");
            return;
        }

        // Load the Haar Cascade classifier for object detection
        CascadeClassifier objectCascade = new CascadeClassifier("C:/Users/krush/Downloads/haarcascade_frontalcatface.xml");

        Mat frame = new Mat();
        while (true) {
            capture.read(frame);
            if (frame.empty()) {
                System.out.println("No video frame captured.");
                break;
            }

            Mat grayFrame = new Mat();
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

            // Detect objects (e.g., faces) in the frame
            MatOfRect objects = new MatOfRect();
            objectCascade.detectMultiScale(grayFrame, objects);

            // Draw rectangles around detected objects
            for (Rect rect : objects.toArray()) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }

            HighGui.imshow("Object Detection", frame);
            if (HighGui.waitKey(30) >= 0) {
                break;
            }
        }
        capture.release();
        HighGui.destroyAllWindows();
    }
}