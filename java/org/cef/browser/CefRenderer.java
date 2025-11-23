// Copyright (c) 2014 The Chromium Embedded Framework Authors.
// All rights reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.
//
// Minimal renderer used by off-screen rendering (OSR) paths. This implementation is
// intentionally lightweight and focuses on keeping CI builds compiling; it performs no
// actual OpenGL texturing beyond clearing state.

package org.cef.browser;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import java.awt.Rectangle;
import java.nio.ByteBuffer;

class CefRenderer {
    private int textureId_ = 0;

    CefRenderer(boolean transparent) {
        // The transparent flag is not needed for this minimal implementation.
    }

    void initialize(GL2 gl) {
        // No-op: nothing to initialize for the stub renderer.
    }

    void cleanup(GL2 gl) {
        if (textureId_ != 0) {
            int[] tex = new int[] {textureId_};
            gl.glDeleteTextures(1, tex, 0);
            textureId_ = 0;
        }
    }

    void render(GL2 gl) {
        // Simple clear to avoid GL errors during CI runs.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    }

    void onPaint(GL2 gl, boolean popup, Rectangle[] dirtyRects, ByteBuffer buffer, int width,
            int height) {
        // Stub: OSR pixels are not uploaded in this minimal renderer.
    }

    void clearPopupRects() {}

    void onPopupSize(Rectangle size) {}

    int getTextureID() {
        return textureId_;
    }
}
