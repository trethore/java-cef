// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.function.LongConsumer;

class CefJSDialogCallback_N extends CefNativeAdapter implements CefJSDialogCallback {
    CefJSDialogCallback_N() {}

    @Override
    public void Continue(boolean success, String user_input) {
        try {
            N_Continue(getNativeRef(null), success, user_input);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    protected LongConsumer getDisposer() {
        return CefJSDialogCallback_N::disposeNative;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new CleanupInvoker(handle).dispose();
    }

    private static final class CleanupInvoker extends CefJSDialogCallback_N {
        private CleanupInvoker(long handle) {
            super();
            setNativeHandleUnsafe(handle);
        }

        void dispose() {
            try {
                N_Continue(getNativeRef(null), false, "");
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }

        @Override
        protected LongConsumer getDisposer() {
            return null;
        }
    }

    private final native void N_Continue(long self, boolean success, String user_input);
}
