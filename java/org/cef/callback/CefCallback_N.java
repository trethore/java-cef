// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.function.LongConsumer;

class CefCallback_N extends CefNativeAdapter implements CefCallback {
    CefCallback_N() {}

    @Override
    public void Continue() {
        try {
            N_Continue(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        cleanNativeResources(CefCallback_N::disposeNative);
    }

    @Override
    protected LongConsumer getDisposer() {
        return CefCallback_N::disposeNative;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new CleanupInvoker(handle).dispose();
    }

    private static final class CleanupInvoker extends CefCallback_N {
        private CleanupInvoker(long handle) {
            super();
            setNativeHandleUnsafe(handle);
        }

        public void dispose() {
            try {
                N_Cancel(getNativeRef(null));
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }

        @Override
        protected LongConsumer getDisposer() {
            return null;
        }
    }

    protected final native void N_Continue(long self);
    protected final native void N_Cancel(long self);
}
