package com.bsp.cloud.rekognition.controller;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image/detect")
public class ImageController {
    /*
    检测图像中的文本
    bucket： S3中桶名称
    photo：  对象名称
     */
    private String rekognitionRegion="us-east-2";

    @PostMapping("/text")
    public void detectedText(@RequestParam String bucket, String photo) {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withRegion(rekognitionRegion).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)));
        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> labels = result.getTextDetections();

            System.out.println("Detected labels for " + photo);
            for (TextDetection label : labels) {
                System.out.println("Detected: " + label.getDetectedText());
                System.out.println("Confidence: " + label.getConfidence().toString());
                System.out.println("Id : " + label.getId());
                System.out.println("Type: " + label.getType());
                System.out.println("Geometry:" + JSONObject.toJSONString(label.getGeometry()));
                System.out.println();
            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/face")
    public void detectedFace(@RequestParam String bucket, String photo) {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withRegion(rekognitionRegion).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)));
        try {
            DetectFacesResult result = rekognitionClient.detectFaces(request);
            List<FaceDetail> faces = result.getFaceDetails();

            System.out.println("Detected labels for " + photo);
            for (FaceDetail face : faces) {
                System.out.println("AgeRange: "+face.getAgeRange());
                System.out.println("Beard: "+face.getBeard());
                System.out.println("Confidence: "+face.getConfidence());
                System.out.println("Smile"+face.getSmile());

                System.out.println();
            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/label")
    public void detectLabel(@RequestParam String bucket, String photo){
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)))
                .withMaxLabels(10)
                .withMinConfidence(75F);

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List <Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo);
            for (Label label: labels) {
                System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }
        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }
}


