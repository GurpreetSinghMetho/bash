package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.utils.Const;

import java.util.List;

public class FacePOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("faceAttributes")
        public FaceAttributes faceAttributes;
        @SerializedName("faceLandmarks")
        public FaceLandmarks faceLandmarks;
        @SerializedName("faceRectangle")
        public FaceRectangle faceRectangle;
        @SerializedName("recognitionModel")
        public String recognitionModel;
        @SerializedName("faceId")
        public String faceId;
    }

    public static class FaceAttributes {
        @SerializedName("noise")
        public Noise noise;
        @SerializedName("exposure")
        public Exposure exposure;
        @SerializedName("blur")
        public Blur blur;
        @SerializedName("accessories")
        public List<Accessories> accessories;
        @SerializedName("occlusion")
        public Occlusion occlusion;
        @SerializedName("makeup")
        public Makeup makeup;
        @SerializedName("hair")
        public Hair hair;
        @SerializedName("emotion")
        public Emotion emotion;
        @SerializedName("headPose")
        public HeadPose headPose;
        @SerializedName("glasses")
        public String glasses;
        @SerializedName("facialHair")
        public FacialHair facialHair;
        @SerializedName("smile")
        public double smile;
        @SerializedName("gender")
        public String gender;
        @SerializedName("age")
        public double age;
    }

    public static class Noise {
        @SerializedName("value")
        public double value;
        @SerializedName("noiseLevel")
        public String noiseLevel;
    }

    public static class Exposure {
        @SerializedName("value")
        public double value;
        @SerializedName("exposureLevel")
        public String exposureLevel;
    }

    public static class Blur {
        @SerializedName("value")
        public double value;
        @SerializedName("blurLevel")
        public String blurLevel;
    }

    public static class Accessories {
        @SerializedName("confidence")
        public double confidence;
        @SerializedName("type")
        public String type;
    }

    public static class Occlusion {
        @SerializedName("mouthOccluded")
        public boolean mouthOccluded;
        @SerializedName("eyeOccluded")
        public boolean eyeOccluded;
        @SerializedName("foreheadOccluded")
        public boolean foreheadOccluded;
    }

    public static class Makeup {
        @SerializedName("lipMakeup")
        public boolean lipMakeup;
        @SerializedName("eyeMakeup")
        public boolean eyeMakeup;
    }

    public static class Hair {
        @SerializedName("hairColor")
        public List<HairColor> hairColor;
        @SerializedName("invisible")
        public boolean invisible;
        @SerializedName("bald")
        public double bald;

        public String getHairColorName() {
            String colorName = Const.HAIR_BLACK;
            double value = 0;
            for (HairColor item : hairColor) {
                if (value < item.confidence) {
                    value = item.confidence;
                    colorName = item.color;
                }
            }
            return colorName;
        }
    }

    public static class HairColor {
        @SerializedName("confidence")
        public double confidence;
        @SerializedName("color")
        public String color;
    }

    public static class Emotion {
        @SerializedName("surprise")
        public double surprise;
        @SerializedName("sadness")
        public double sadness;
        @SerializedName("neutral")
        public double neutral;
        @SerializedName("happiness")
        public double happiness;
        @SerializedName("fear")
        public double fear;
        @SerializedName("disgust")
        public double disgust;
        @SerializedName("contempt")
        public double contempt;
        @SerializedName("anger")
        public double anger;
    }

    public static class HeadPose {
        @SerializedName("pitch")
        public double pitch;
        @SerializedName("yaw")
        public double yaw;
        @SerializedName("roll")
        public double roll;
    }

    public static class FacialHair {
        @SerializedName("sideburns")
        public double sideburns;
        @SerializedName("beard")
        public double beard;
        @SerializedName("moustache")
        public double moustache;
    }

    public static class FaceLandmarks {
        @SerializedName("underLipBottom")
        public UnderLipBottom underLipBottom;
        @SerializedName("underLipTop")
        public UnderLipTop underLipTop;
        @SerializedName("upperLipBottom")
        public UpperLipBottom upperLipBottom;
        @SerializedName("upperLipTop")
        public UpperLipTop upperLipTop;
        @SerializedName("noseRightAlarOutTip")
        public NoseRightAlarOutTip noseRightAlarOutTip;
        @SerializedName("noseLeftAlarOutTip")
        public NoseLeftAlarOutTip noseLeftAlarOutTip;
        @SerializedName("noseRightAlarTop")
        public NoseRightAlarTop noseRightAlarTop;
        @SerializedName("noseLeftAlarTop")
        public NoseLeftAlarTop noseLeftAlarTop;
        @SerializedName("noseRootRight")
        public NoseRootRight noseRootRight;
        @SerializedName("noseRootLeft")
        public NoseRootLeft noseRootLeft;
        @SerializedName("eyeRightOuter")
        public EyeRightOuter eyeRightOuter;
        @SerializedName("eyeRightBottom")
        public EyeRightBottom eyeRightBottom;
        @SerializedName("eyeRightTop")
        public EyeRightTop eyeRightTop;
        @SerializedName("eyeRightInner")
        public EyeRightInner eyeRightInner;
        @SerializedName("eyebrowRightOuter")
        public EyebrowRightOuter eyebrowRightOuter;
        @SerializedName("eyebrowRightInner")
        public EyebrowRightInner eyebrowRightInner;
        @SerializedName("eyeLeftInner")
        public EyeLeftInner eyeLeftInner;
        @SerializedName("eyeLeftBottom")
        public EyeLeftBottom eyeLeftBottom;
        @SerializedName("eyeLeftTop")
        public EyeLeftTop eyeLeftTop;
        @SerializedName("eyeLeftOuter")
        public EyeLeftOuter eyeLeftOuter;
        @SerializedName("eyebrowLeftInner")
        public EyebrowLeftInner eyebrowLeftInner;
        @SerializedName("eyebrowLeftOuter")
        public EyebrowLeftOuter eyebrowLeftOuter;
        @SerializedName("mouthRight")
        public MouthRight mouthRight;
        @SerializedName("mouthLeft")
        public MouthLeft mouthLeft;
        @SerializedName("noseTip")
        public NoseTip noseTip;
        @SerializedName("pupilRight")
        public PupilRight pupilRight;
        @SerializedName("pupilLeft")
        public PupilLeft pupilLeft;
    }

    public static class UnderLipBottom {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class UnderLipTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class UpperLipBottom {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class UpperLipTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseRightAlarOutTip {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseLeftAlarOutTip {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseRightAlarTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseLeftAlarTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseRootRight {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseRootLeft {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeRightOuter {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeRightBottom {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeRightTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeRightInner {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyebrowRightOuter {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyebrowRightInner {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeLeftInner {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeLeftBottom {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeLeftTop {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyeLeftOuter {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyebrowLeftInner {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class EyebrowLeftOuter {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class MouthRight {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class MouthLeft {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class NoseTip {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class PupilRight {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class PupilLeft {
        @SerializedName("y")
        public double y;
        @SerializedName("x")
        public double x;
    }

    public static class FaceRectangle {
        @SerializedName("top")
        public double top;
        @SerializedName("left")
        public double left;
        @SerializedName("height")
        public double height;
        @SerializedName("width")
        public double width;
    }
}
