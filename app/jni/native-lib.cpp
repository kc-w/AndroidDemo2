#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>


/**
 * 获取相应波特率
 * @param baudrate 波特率
 * @return
 */
static speed_t getBaudrate(jint baudrate) {
    switch(baudrate)
    {
        case 0: return B0;
        case 50: return B50;
        case 75: return B75;
        case 110: return B110;
        case 134: return B134;
        case 150: return B150;
        case 200: return B200;
        case 300: return B300;
        case 600: return B600;
        case 1200: return B1200;
        case 1800: return B1800;
        case 2400: return B2400;
        case 4800: return B4800;
        case 9600: return B9600;
        case 19200: return B19200;
        case 38400: return B38400;
        case 57600: return B57600;
        case 115200: return B115200;
        case 230400: return B230400;
        case 460800: return B460800;
        case 500000: return B500000;
        case 576000: return B576000;
        case 921600: return B921600;
        case 1000000: return B1000000;
        case 1152000: return B1152000;
        case 1500000: return B1500000;
        case 2000000: return B2000000;
        case 2500000: return B2500000;
        case 3000000: return B3000000;
        case 3500000: return B3500000;
        case 4000000: return B4000000;
        default: return -1;
    }
}

/*
 * Class:     android_serialport_SerialPort
 * Method:    打开串口，主要就是返回FileDescriptor这个对象，供java层调用。
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */

extern "C" JNIEXPORT jobject JNICALL
Java_android_1serialport_1api_SerialPort_open
        (JNIEnv *env, jclass thiz, jstring path, jint baudrate, jint flags) {
    int fd;
    speed_t speed;
    jobject mFileDescriptor;

    /* 检查参数 */
    {
        speed = getBaudrate(baudrate);
        if (speed == -1) {
            return NULL;
        }
    }

    /* 打开设备 */
    {
        jboolean iscopy;
        const char *path_utf = env->GetStringUTFChars( path, &iscopy);
        //读取数据是阻塞的,加上 O_NDELAY 参数，读取数据是异步的
        fd = open(path_utf, O_RDWR | flags | O_NONBLOCK | O_NOCTTY | O_NDELAY);
        env->ReleaseStringUTFChars( path, path_utf);
        if (fd == -1) {
            return NULL;
        }
    }

    /* 设置参数 */
    {
        struct termios cfg;
        if (tcgetattr(fd, &cfg)) {
            close(fd);
            return NULL;
        }

        cfmakeraw(&cfg);
        cfsetispeed(&cfg, speed);
        cfsetospeed(&cfg, speed);

        if (tcsetattr(fd, TCSANOW, &cfg)) {
            close(fd);
            return NULL;
        }
    }

    /* 创建一个 descriptor，用来操作这个串口文件 */
    {
        jclass cFileDescriptor = env->FindClass("java/io/FileDescriptor");
        jmethodID iFileDescriptor = env->GetMethodID(cFileDescriptor, "<init>", "()V");
        jfieldID descriptorID = env->GetFieldID(cFileDescriptor, "descriptor", "I");
        mFileDescriptor = env->NewObject(cFileDescriptor, iFileDescriptor);
        env->SetIntField(mFileDescriptor, descriptorID, (jint)fd);

    }

    return mFileDescriptor;
}

/*
 * Class:     cedric_serial_SerialPort
 * Method:    关闭串口
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL
Java_android_1serialport_1api_SerialPort_close(JNIEnv *env, jobject thiz) {
    jclass SerialPortClass = env->GetObjectClass( thiz);
    jclass FileDescriptorClass = env->FindClass("java/io/FileDescriptor");

    jfieldID mFdID = env->GetFieldID(SerialPortClass, "mFd", "Ljava/io/FileDescriptor;");
    jfieldID descriptorID = env->GetFieldID(FileDescriptorClass, "descriptor", "I");

    jobject mFd = env->GetObjectField(thiz, mFdID);
    jint descriptor = env->GetIntField(mFd, descriptorID);

    close(descriptor);
}
