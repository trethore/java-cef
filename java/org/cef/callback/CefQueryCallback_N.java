// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.function.LongConsumer;

class CefQueryCallback_N extends CefNativeAdapter implements CefQueryCallback {
    CefQueryCallback_N() {}

    @Override
    public void success(String response) {
        try {
            N_Success(getNativeRef(null), response);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void failure(int error_code, String error_message) {
        try {
            N_Failure(getNativeRef(null), error_code, error_message);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    protected LongConsumer getDisposer() {
        return CefQueryCallback_N::disposeNative;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new CleanupInvoker(handle).dispose();
    }

    private static final class CleanupInvoker extends CefQueryCallback_N {
        private CleanupInvoker(long handle) {
            super();
            setNativeHandleUnsafe(handle);
        }

        void dispose() {
            try {
                N_Failure(getNativeRef(null), -1, "Unexpected cleanup of CefQueryCallback_N");
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }

        @Override
        protected LongConsumer getDisposer() {
            return null;
        }
    }

    private final native void N_Success(long self, String response);
    private final native void N_Failure(long self, int error_code, String error_message);
}
