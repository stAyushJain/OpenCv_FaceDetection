package ayush.practice.facedetection2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = MainActivity.class.getSimpleName();
    private JavaCameraView javaCameraView;
    private Mat mMat, imageGrayMat, imageCannyMat;
    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status)
            {
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    break;
            default:
            super.onManagerConnected(status);
        }
        }
    };

    static
    {
        System.loadLibrary("MyLibs");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView = (JavaCameraView)findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCameraIndex(1);
        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug())
        {
            Log.d(TAG, "openCv loaded successfully");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else {
            Log.d(TAG, "openCv not loaded successfully");

//            if not then try to reload
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mMat = new Mat(height, width, CvType.CV_8UC4);
        imageGrayMat = new Mat(height, width, CvType.CV_8UC1);
        imageCannyMat = new Mat(height, width, CvType.CV_8UC3);

    }

    @Override
    public void onCameraViewStopped() {
        mMat.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mMat = inputFrame.rgba();
        OpenCvNative.faceDetection(mMat.getNativeObjAddr());

        return mMat;
    }




}
