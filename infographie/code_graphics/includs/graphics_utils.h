#include "graphics.h"

void init_graph_() {
    int g = DETECT, m;
    initgraph(&g, &m, (char *) "");
}

void repere(int xc, int yc) {
    int xm = getmaxx() / 2;
    int ym = getmaxy() / 2;
    line(xc - xm, yc + 0, xc + xm, yc + 0);
    line(xc + 0, yc - ym, xc + 0, yc + ym);
}

void pixel5(int x, int y, int color) {
    x = x + 320;
    y = 240 - y;
    putpixel(x, y, color);
    putpixel(x + 1, y - 1, color);
    putpixel(x - 1, y + 1, color);
    putpixel(x + 1, y + 1, color);
    putpixel(x - 1, y - 1, color);
}

void pixel9(int x, int y, int color) {
    x = x + 320;
    y = 240 - y;
    putpixel(x, y, color);
    putpixel(x + 1, y, color);
    putpixel(x, y + 1, color);
    putpixel(x - 1, y, color);
    putpixel(x, y - 1, color);
    putpixel(x + 1, y - 1, color);
    putpixel(x - 1, y + 1, color);
    putpixel(x + 1, y + 1, color);
    putpixel(x - 1, y - 1, color);
}

void pixel13(int x, int y, int color) {
    x = x + 320;
    y = 240 - y;
    putpixel(x, y, color);
    putpixel(x + 1, y, color);
    putpixel(x, y + 1, color);
    putpixel(x + 1, y + 1, color);
    putpixel(x - 1, y, color);
    putpixel(x, y - 1, color);
    putpixel(x - 1, y - 1, color);
    putpixel(x + 1, y - 1, color);
    putpixel(x - 1, y + 1, color);
    putpixel(x + 2, y, color);
    putpixel(x, y + 2, color);
    putpixel(x + 2, y + 2, color);
    putpixel(x - 2, y, color);
    putpixel(x, y - 2, color);
    putpixel(x - 2, y - 2, color);
    putpixel(x + 2, y - 2, color);
    putpixel(x - 2, y + 2, color);
}

void pixel(int x, int y, int color, int lw=1) {
    switch (lw) {
        case 5:
            pixel5(x, y, color);
            break;
        case 9:
            pixel9(x, y, color);
            break;
        case 13:
            pixel13(x, y, color);
            break;
        default:
            x = x + 320;
            y = 240 - y;
            putpixel(x, y, color);
            break;
    }
}