LOCAL_PATH :=$(call my-dir)
include $(CLEAR_VARS)
#编译后的so文件名
LOCAL_MODULE :=native-lib
#需要编译的c文件名
LOCAL_SRC_FILES :=native-lib.cpp
LOCAL_CERTIFICATE := platform
#编译为动态文件
include $(BUILD_SHARED_LIBRARY)