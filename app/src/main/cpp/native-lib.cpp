//
// Created by Percy on 1-10 0010.
//

#include "ConvertUtils.c"
#include "native-lib.h"


extern "C"
JNIEXPORT void JNICALL Java_com_smart_interview_main_camera_convert
        (JNIEnv *env, jobject thiz, jintArray out, jbyteArray buffer, jint width, jint height) {

    unsigned char *pbuffer = (unsigned char *) env->GetByteArrayElements(buffer, NULL);
    int *color = env->GetIntArrayElements(out, NULL);
//    convertYUV420_NV21toGray(pbuffer, (size_t) width, (size_t) height);
//    convertYUV420_NV21toHalfy(pbuffer, (size_t) width, (size_t) height);
    convertYUV420_NV21toARGB8888(pbuffer, color, width, height);
    env->ReleaseByteArrayElements(buffer, (jbyte *) pbuffer, 0);
    env->ReleaseIntArrayElements(out, color, 0);
}

JNIEXPORT void JNICALL Java_com_smart_interview_main_camera_convertByteToColor
        (JNIEnv *env, jobject thiz, jbyteArray buffer, jintArray out, jint width, jint height){
    unsigned char *pbuffer = (unsigned char *) env->GetByteArrayElements(buffer, NULL);
    int *color = env->GetIntArrayElements(out, NULL);
    convertByteToColor(pbuffer,color,width,height);
    env->ReleaseByteArrayElements(buffer, (jbyte *) pbuffer, 0);
    env->ReleaseIntArrayElements(out, color, 0);
}