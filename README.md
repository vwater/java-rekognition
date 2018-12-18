# java-rekognition
Prerequisites:  
检测图片中的文本：
         hold an aws account.  

         add IAM user with the access property of AmazonRekognitionFullAccess and AmazonS3ReadOnlyAccess.  
         
         intstall AWS CLI in desktop.  
         
         create access key for the above user in IAM.  
         
         configure access key on your desktop,just run the following command on your desktop: 'aws configure' in win cmd command line.  
         
         then enter the access key ID、secret access key、region(the region should be same S3 bucket, and the region should be reach on aws).
           
           
        
检测视频中的标签

配置用户对 Amazon Rekognition Video 的访问，并配置 Amazon Rekognition Video 对 Amazon SNS 的访问。有关更多信息，请参阅配置 Amazon Rekognition Video。

通过使用 Amazon SNS 控制台创建 Amazon SNS 主题。在主题名称前加上 AmazonRekognition。记下主题的 Amazon 资源名称 (ARN)。确保该主题与您使用的 AWS 终端节点位于同一区域。

通过使用 Amazon SQS 控制台创建 Amazon SQS 标准队列。记录队列 ARN.

为队列订阅主题 (您在步骤 2 中创建)。

为向 Amazon SQS 队列发送消息的 Amazon SNS 主题授予权限。

将 .mp4、.mov 或 .avi 格式的视频文件上传到您的 S3 存储桶。对于测试，请上传时长不超过 30 秒的视频。

有关说明，请参阅 Amazon Simple Storage Service 控制台用户指南 中的将对象上传到 Amazon S3。

使用以下 AWS SDK for Java 代码检测视频中的标签。

将 topicArn、roleArn 和 queueUrl 替换为您之前记下的 Amazon SNS 主题 ARN、IAM 角色 ARN 和 Amazon SQS 队列 URL。

将 bucket 和 video 的值替换为您在步骤 6 中指定的存储桶和视频文件名。
