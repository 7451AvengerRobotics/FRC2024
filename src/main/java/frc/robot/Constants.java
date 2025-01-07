package frc.robot;


import static edu.wpi.first.units.Units.Meters;

import java.util.Optional;

import static edu.wpi.first.units.Units.Inches;



import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj.DriverStation;
import frc.util.AllianceFlipUtil;

public final class Constants {

    public class FieldK {
        public static final Measure<Distance> kFieldLength = Meters.of(16.54);
        public static final Measure<Distance> kFieldWidth = Meters.of(8.21);

        public static final AprilTagFieldLayout kFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();

        // taken from 6328. All in blue alliance origin.
        /* speaker constants */
        public static final class SpeakerK {


            private static final Measure<Distance> kTopX = Inches.of(18.055);
            private static final Measure<Distance> kTopZ = Inches.of(83.091);
            public static final Translation3d kTopRight = new Translation3d(
                kTopX, Inches.of(238.815), kTopZ);
            public static final Translation3d kTopLeft = new Translation3d(
                kTopX, Inches.of(197.765), kTopZ);

            private static final Measure<Distance> kBotX = Inches.of(0);
            private static final Measure<Distance> kBotZ = Inches.of(78.324);
            // private static final Translation3d kBotRight = new Translation3d(
            // kBotX, Inches.of(238.815), kBotZ);
            public static final Translation3d kBotLeft = new Translation3d(
                kBotX, Inches.of(197.765), kBotZ);

            public static final Translation3d kBlueCenterOpening = kBotLeft.interpolate(kTopRight, 0.5);
            public static final Pose3d kBlueCenterOpeningPose3d = new Pose3d(
                kBlueCenterOpening, new Rotation3d());
            public static final Translation3d kRedCenterOpening = AllianceFlipUtil.flip(kBlueCenterOpening);
            public static final Pose3d kRedCenterOpeningPose3d = new Pose3d(
                kRedCenterOpening, new Rotation3d());

            public static final Measure<Distance> kAimOffset = Inches.of(25);
        }

        public static class Vision {
            public static final String kCameraName = "YOUR CAMERA NAME";
            // Cam mounted facing forward, half a meter forward of center, half a meter up from center.
            public static final Transform3d kRobotToCam =
                    new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0, 0, 0));

            // The layout of the AprilTags on the field
            public static final AprilTagFieldLayout kTagLayout =
                    AprilTagFields.kDefaultField.loadAprilTagLayoutField();

            // The standard deviations of our vision estimated poses, which affect correction rate
            // (Fake values. Experiment and determine estimation noise on an actual robot.)
            public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
            public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
        
        }

        /* amp constants */
        public static final Measure<Distance> kXToAmp = Inches.of(49.5);
        public static final Measure<Distance> kYToAmp = Inches.of(286.765);
        public static final Measure<Distance> kZToAmp = Inches.of(35);

        public static final Translation3d kBlueAmpPose = new Translation3d(
            kXToAmp, kYToAmp, kZToAmp);

        public static final Translation3d kRedAmpPose = new Translation3d(
            kFieldLength.minus(kXToAmp), kFieldWidth.minus(kYToAmp), kZToAmp);

        /* stage constants */
        public static final double kBlueStageClearanceDs = Units.inchesToMeters(188.5);
        public static final double kBlueStageClearanceRight = Units.inchesToMeters(88.3);
        public static final double kBlueStageClearanceCenter = Units.inchesToMeters(243.2);
        public static final double kBlueStageClearanceLeft = Units.inchesToMeters(234.9);

        public static final double kRedStageClearanceDs = Units.inchesToMeters(542.2);
        public static final double kRedStageClearanceRight = Units.inchesToMeters(88.3);
        public static final double kRedStageClearanceCenter = Units.inchesToMeters(407.9);
        public static final double kRedStageClearanceLeft = Units.inchesToMeters(234.9);
    }

   






/*
 * ------5------------1-2
 * ----6--10--9-------4-3
 * -------------------8-7
 * -------------------11-12
 */


    public static final int w = 5;
    public static final int a = 6;
    public static final int s = 10;
    public static final int d = 9;
    public static final int one = 1;
    public static final int two = 2;
    public static final int three = 4;
    public static final int four = 3;
    public static final int five = 8;
    public static final int six = 7;
    public static final int seven = 11;
    public static final int eight = 12;
    // public static final int testVortex1ID = 12;
    // public static final int testVortex2ID = 13
    public static final int shooterTop = 27;
    public static final int shooterBottom = 28;
    public static final int feeder = 32;
    public static final int mElevator1 = 22;
    public static final int mElevator2 = 23;
    public static final int pivot = 24;
    public static final int intake = 20;
    public static final int index = 21;
    public static final int climber1 = 25;
    public static final int climber2 = 26;

    public class FieldConstants {
        public static final double FIELD_LENGTH_X_METERS = Units.inchesToMeters(651.223);
        public static final double FIELD_WIDTH_Y_METERS = Units.inchesToMeters(323.277);
    
        public static final Pose2d BLUE_SPEAKER_POSE =
                new Pose2d(new Translation2d(0, 5.547868), Rotation2d.fromDegrees(0));
    
        public static final Pose2d RED_SPEAKER_POSE =
                new Pose2d(new Translation2d(16.1, 5.547868), Rotation2d.fromDegrees(180));

            
        public static final Pose2d RANDOM_OBJ =
                new Pose2d(new Translation2d(10, 3), Rotation2d.fromDegrees(0));
    
        public static final Pose2d BLUE_AMP_POSE =
                new Pose2d(
                        Units.inchesToMeters(72.455),
                        FIELD_WIDTH_Y_METERS - Units.inchesToMeters(20),
                        Rotation2d.fromRadians(-Math.PI / 2)
                );
    
    
        private static Pose2d getAllianceFlippedPose(final Pose2d blueAlliancePose, final Pose2d redAlliancePose) {
            final Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
            if (alliance.isPresent()) {
                if (alliance.get() == DriverStation.Alliance.Red) {
                    return redAlliancePose;
                } else {
                    return blueAlliancePose;
                }
            } else {
                // return blue side pose if unknown alliance
                return blueAlliancePose;
            }
        }
        public static Pose2d getSpeakerPose() {
            return getAllianceFlippedPose(BLUE_SPEAKER_POSE, RED_SPEAKER_POSE);
        }
    }


}
