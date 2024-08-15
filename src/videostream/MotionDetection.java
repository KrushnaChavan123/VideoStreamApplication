package videostream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class MotionDetection {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not detected.");
            return;
        }

        BackgroundSubtractorMOG2 bgSubtractor = Video.createBackgroundSubtractorMOG2();

        Mat frame = new Mat();
        Mat fgMask = new Mat();
        while (true) {
            capture.read(frame);
            if (frame.empty()) {
                System.out.println("No video frame captured.");
                break;
            }

            // Apply background subtraction
            bgSubtractor.apply(frame, fgMask);

            // Optional: Post-process the foreground mask (e.g., smoothing, thresholding)
            Imgproc.GaussianBlur(fgMask, fgMask, new Size(5, 5), 0);

            // Show the foreground mask
            HighGui.imshow("Motion Tracking", fgMask);
            if (HighGui.waitKey(30) >= 0) {
                break;
            }
        }
        capture.release();
        HighGui.destroyAllWindows();
    }
}