LOCAL_PATH := $(call my-dir)

	CVROOT := /Users/keerthiacharya/Downloads/OpenCV-android-sdk/sdk/native/jni
    include $(CLEAR_VARS)
    OPENCV_INSTALL_MODULES:=on
    OPENCV_LIB_TYPE:=STATIC
    include $(CVROOT)/OpenCV.mk

	LOCAL_SRC_FILES := ayush_practice_facedetection2_OpenCvNative.cpp

	LOCAL_LDLIBS += -llog
	LOCAL_MODULE := MyLibs


	include $(BUILD_SHARED_LIBRARY)