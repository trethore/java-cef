// Copyright (c) 2024 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.browser;

import org.cef.callback.CefNative;
import org.cef.misc.CefCleanup;

class CefRegistration_N extends CefRegistration implements CefNative {
    // Used internally to store a pointer to the CEF object.
    private long N_CefHandle = 0;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();

    @Override
    public void setNativeRef(String identifier, long nativeRef) {
        N_CefHandle = nativeRef;
        cleanup.register(this, nativeRef, CefRegistration_N::disposeNative);
    }

    @Override
    public long getNativeRef(String identifier) {
        return N_CefHandle;
    }

    @Override
    public void dispose() {
        long handle = N_CefHandle;
        N_CefHandle = 0;
        cleanup.clean(handle, CefRegistration_N::disposeNative);
    }

    private final native void N_Dispose(long self);

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new NativeDisposer(handle).dispose();
    }

    private static final class NativeDisposer extends CefRegistration_N {
        private NativeDisposer(long handle) {
            super();
            N_CefHandle = handle;
        }

        void dispose() {
            try {
                N_Dispose(N_CefHandle);
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }
    }
}
