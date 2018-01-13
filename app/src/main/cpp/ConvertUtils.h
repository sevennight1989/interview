//
// Created by Percy on 1-11 0011.
//

#include <string.h>

#ifndef ANDROID_CAMERA2BASIC_CONVERTUTILS_H
#define ANDROID_CAMERA2BASIC_CONVERTUTILS_H

#endif //ANDROID_CAMERA2BASIC_CONVERTUTILS_H

int convertYUVtoARGB(int, int, int);

void convertYUV420_NV21toGray(unsigned char *, size_t, size_t);

void convertYUV420_NV21toHalfy(unsigned char *, size_t, size_t);

void convertYUV420_NV21toARGB8888(unsigned char *, int *, int, int);

void rgb24_colorbar(int, int, unsigned char *);

void convertByteToColor(unsigned char *, int *, int, int);