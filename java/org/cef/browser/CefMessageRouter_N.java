// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.browser;

import org.cef.callback.CefNative;
import org.cef.handler.CefMessageRouterHandler;
import org.cef.misc.CefCleanup;

class CefMessageRouter_N extends CefMessageRouter implements CefNative {
    // Used internally to store a pointer to the CEF object.
    protected long N_CefHandle = 0;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
        cleanup.register(this, nativeRef, CefMessageRouter_N::disposeNative);
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    private CefMessageRouter_N() {
        super();
    }

    public static final CefMessageRouter createNative(CefMessageRouterConfig config) {
        try {
            return CefMessageRouter_N.N_Create(config);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public void dispose() {
        long handle = N_CefHandle;
        N_CefHandle = 0;
        cleanup.clean(handle, CefMessageRouter_N::disposeNative);
    }

    @Override
    public boolean addHandler(CefMessageRouterHandler handler, boolean first) {
        try {
            return N_AddHandler(N_CefHandle, handler, first);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeHandler(CefMessageRouterHandler handler) {
        try {
            return N_RemoveHandler(N_CefHandle, handler);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public void cancelPending(CefBrowser browser, CefMessageRouterHandler handler) {
        try {
            N_CancelPending(N_CefHandle, browser, handler);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new NativeDisposer(handle).dispose();
    }

    private static final class NativeDisposer extends CefMessageRouter_N {
        private NativeDisposer(long handle) {
            super();
            N_CefHandle = handle;
        }

        public void dispose() {
            try {
                N_Dispose(N_CefHandle);
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }
    }

    protected final native static CefMessageRouter_N N_Create(CefMessageRouterConfig config);
    protected final native void N_Dispose(long self);
    protected final native boolean N_AddHandler(
            long self, CefMessageRouterHandler handler, boolean first);
    protected final native boolean N_RemoveHandler(long self, CefMessageRouterHandler handler);
    protected final native void N_CancelPending(
            long self, CefBrowser browser, CefMessageRouterHandler handler);
}
