package com.ninesix.remotevotingsystem

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
	
	private val TAG = "MainActivity"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		val highAccuracyOpts = FaceDetectorOptions.Builder()
			.setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
			.setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
			.setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
			.enableTracking()
			.setMinFaceSize(0.9f)
			.build()
		
		val realTimeOpts = FaceDetectorOptions.Builder()
			.setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
			.build()
		
		val image: InputImage
		try {
			image = InputImage.fromFilePath(this, Uri.fromFile(File("storage/emulated/0/Android/media/com.ninesix.remotevotingsystem/1.jpg")))
			val detector = FaceDetection.getClient(highAccuracyOpts)
			
			val result = detector.process(image)
				.addOnSuccessListener { faces ->
					Log.d(TAG, "onCreate: $faces")
					
					for(face in faces)
						if(face.trackingId != null)
							Log.d(TAG, "onCreate2: ${face.trackingId}")
				}
				.addOnFailureListener { e ->
					Log.d(TAG, "onCreate: $e")
				}
			
		} catch (e: IOException) {
			e.printStackTrace()
			Log.d(TAG, "onCreate: $e")
		}
	}
}