package com.bsp.cloud.rekognition.controller;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image/detect")
public class ImageController {


    @PostMapping("/text")
    public  void detectedText(@RequestParam String bucket, String photo){
        AWSCredentials credentials  = new ProfileCredentialsProvider().getCredentials();

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)));
        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> labels = result.getTextDetections();

            System.out.println("Detected labels for " + photo);
            for (TextDetection label: labels) {
                System.out.println("Detected: " + label.getDetectedText());
                System.out.println("Confidence: " + label.getConfidence().toString());
                System.out.println("Id : " + label.getId());
                System.out.println("Type: " + label.getType());
                System.out.println("Geometry:"+label.getGeometry());
                System.out.println();            }
        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }


}
