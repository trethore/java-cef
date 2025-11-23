// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.function.LongConsumer;

class CefPrintJobCallback_N extends CefNativeAdapter implements CefPrintJobCallback {
    CefPrintJobCallback_N() {}

    @Override
    public void Continue() {
        try {
            N_Continue(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    protected LongConsumer getDisposer() {
        return CefPrintJobCallback_N::disposeNative;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new CleanupInvoker(handle).dispose();
    }

    private static final class CleanupInvoker extends CefPrintJobCallback_N {
        private CleanupInvoker(long handle) {
            super();
            setNativeHandleUnsafe(handle);
        }

        void dispose() {
            try {
                N_Continue(getNativeRef(null));
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }

        @Override
        protected LongConsumer getDisposer() {
            return null;
        }
    }

    private final native void N_Continue(long self);
}
