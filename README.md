# FRC2024

When Adding a vision measurement which we will eventually. 

    Pose3d visionMeasurement3d =
        objectToRobotPose(m_objectInField, m_robotToCamera, m_cameraToObjectEntry);

    // Convert robot pose from Pose3d to Pose2d needed to apply vision measurements.
    Pose2d visionMeasurement2d = visionMeasurement3d.toPose2d();

    use these lines to convert vision details into a pose and then use this line 

    m_poseEstimator.addVisionMeasurement(getPose(), Timer.getFPGATimestamp() - (tl/1000.0) - (cl/1000.0) );


    the second part is to imporve the latency for real-time updates