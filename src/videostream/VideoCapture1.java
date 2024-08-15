package videostream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class VideoCapture1 {
	public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not opened");
        } else {
            Mat frame = new Mat();

            while (true) {
                if (camera.read(frame)) {
                    System.out.println("Frame Obtained");
                    System.out.println("Captured Frame Width: " + frame.width() + " Height: " + frame.height());
                    Imgcodecs.imwrite("c://captureImages/camera.jpg", frame);
                    System.out.println("Image saved successfully");
                    break;
                }
            }
        }

        camera.release();
    }
}
