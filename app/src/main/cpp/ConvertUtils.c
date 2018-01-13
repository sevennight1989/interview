#include "ConvertUtils.h"
#define BAR_NUMBER 8

//去掉颜色
void convertYUV420_NV21toGray(unsigned char *data, size_t width, size_t height) {
    memset(data + width * height, 128, width * height / 2);
}

//亮度减半
void convertYUV420_NV21toHalfy(unsigned char *data, size_t width, size_t height) {
    int i;
    for(i=0;i<width*height;i++){
        unsigned char temp = (unsigned char) (data[i] / 2);
        data[i] = temp;
    }
}

void convertYUV420_NV21toARGB8888(unsigned char *data, int *color, int width, int height) {
    int size = width * height;
    int offset = size;
    int u, v, y1, y2, y3, y4;
    int i, k;
    for (i = 0, k = 0; i < size; i += 2, k += 2) {
        y1 = data[i] & 0xff;
        y2 = data[i + 1] & 0xff;
        y3 = data[width + i] & 0xff;
        y4 = data[width + i + 1] & 0xff;
        u = data[offset + k] & 0xff;
        v = data[offset + k + 1] & 0xff;
        u = u - 128;
        v = v - 128;
        color[i] = convertYUVtoARGB(y1, u, v);
        color[i + 1] = convertYUVtoARGB(y2, u, v);
        color[width + i] = convertYUVtoARGB(y3, u, v);
        color[width + i + 1] = convertYUVtoARGB(y4, u, v);
        if (i != 0 && (i + 2) % width == 0)
            i += width;
    }
}


int convertYUVtoARGB(int y, int u, int v) {
    int r, g, b;
    r = y + (int) 1.402f * u;
    g = y - (int) (0.344f * v + 0.714f * u);
    b = y + (int) 1.772f * v;
    r = r > 255 ? 255 : r < 0 ? 0 : r;
    g = g > 255 ? 255 : g < 0 ? 0 : g;
    b = b > 255 ? 255 : b < 0 ? 0 : b;
    return 0xff000000 | (r << 16) | (g << 8) | b;
}



void rgb24_colorbar(int width,int height, unsigned char * data){
    int barWidth;
    int i, j = 0;
    barWidth = width / BAR_NUMBER;
    for (j = 0; j < height; j++) {
        for (i = 0; i < width; i++) {
            int barnum = i / barWidth;
            switch (barnum) {
                case 0: {
                    data[(j * width + i) * 3 + 0] = 255;
                    data[(j * width + i) * 3 + 1] = 255;
                    data[(j * width + i) * 3 + 2] = 255;
                    break;
                }
                case 1: {
                    data[(j * width + i) * 3 + 0] = 255;
                    data[(j * width + i) * 3 + 1] = 255;
                    data[(j * width + i) * 3 + 2] = 0;
                    break;
                }
                case 2: {
                    data[(j * width + i) * 3 + 0] = 0;
                    data[(j * width + i) * 3 + 1] = 255;
                    data[(j * width + i) * 3 + 2] = 255;
                    break;
                }
                case 3: {
                    data[(j * width + i) * 3 + 0] = 0;
                    data[(j * width + i) * 3 + 1] = 255;
                    data[(j * width + i) * 3 + 2] = 0;
                    break;
                }
                case 4: {
                    data[(j * width + i) * 3 + 0] = 255;
                    data[(j * width + i) * 3 + 1] = 0;
                    data[(j * width + i) * 3 + 2] = 255;
                    break;
                }
                case 5: {
                    data[(j * width + i) * 3 + 0] = 255;
                    data[(j * width + i) * 3 + 1] = 0;
                    data[(j * width + i) * 3 + 2] = 0;
                    break;
                }
                case 6: {
                    data[(j * width + i) * 3 + 0] = 0;
                    data[(j * width + i) * 3 + 1] = 0;
                    data[(j * width + i) * 3 + 2] = 255;

                    break;
                }
                case 7: {
                    data[(j * width + i) * 3 + 0] = 0;
                    data[(j * width + i) * 3 + 1] = 0;
                    data[(j * width + i) * 3 + 2] = 0;
                    break;
                }
            }
        }
    }
}

//对应java byte[] 装int[]
void convertByteToColor(unsigned char * data,int *color,int width,int height){
    int i;
    int len = width * height;
    for(i = 0;i< len;++i){
        color[i] = (data[i * 3] << 16 & 0x00FF0000) |
                   (data[i * 3 + 1] << 8 & 0x0000FF00) |
                   (data[i * 3 + 2] & 0x000000FF) |
                   0xFF000000;
    }
}

