// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.network;

import org.cef.callback.CefCompletionCallback;
import org.cef.callback.CefCookieVisitor;
import org.cef.callback.CefNative;
import org.cef.misc.CefCleanup;

import java.util.Vector;

class CefCookieManager_N extends CefCookieManager implements CefNative {
    // Used internally to store a pointer to the CEF object.
    protected long N_CefHandle = 0;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();
    private static CefCookieManager_N globalInstance = null;

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
        cleanup.register(this, nativeRef, CefCookieManager_N::disposeNative);
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    CefCookieManager_N() {
        super();
    }

    static synchronized final CefCookieManager_N getGlobalManagerNative() {
        if (globalInstance != null && globalInstance.N_CefHandle != 0) {
            // The global instance is still valid.
            return globalInstance;
        }

        CefCookieManager_N result = null;
        try {
            result = CefCookieManager_N.N_GetGlobalManager();
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }

        globalInstance = result;
        return globalInstance;
    }

    @Override
    public void dispose() {
        long handle = N_CefHandle;
        N_CefHandle = 0;
        cleanup.clean(handle, CefCookieManager_N::disposeNative);
    }

    @Override
    public boolean visitAllCookies(CefCookieVisitor visitor) {
        try {
            return N_VisitAllCookies(N_CefHandle, visitor);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean visitUrlCookies(String url, boolean includeHttpOnly, CefCookieVisitor visitor) {
        try {
            return N_VisitUrlCookies(N_CefHandle, url, includeHttpOnly, visitor);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setCookie(String url, CefCookie cookie) {
        try {
            return N_SetCookie(N_CefHandle, url, cookie);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCookies(String url, String cookieName) {
        try {
            return N_DeleteCookies(N_CefHandle, url, cookieName);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean flushStore(CefCompletionCallback handler) {
        try {
            return N_FlushStore(N_CefHandle, handler);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new NativeDisposer(handle).dispose();
    }

    private static final class NativeDisposer extends CefCookieManager_N {
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

    private final static native CefCookieManager_N N_GetGlobalManager();
    protected final native void N_Dispose(long self);
    protected final native boolean N_VisitAllCookies(long self, CefCookieVisitor visitor);
    protected final native boolean N_VisitUrlCookies(
            long self, String url, boolean includeHttpOnly, CefCookieVisitor visitor);
    protected final native boolean N_SetCookie(long self, String url, CefCookie cookie);
    protected final native boolean N_DeleteCookies(long self, String url, String cookieName);
    protected final native boolean N_FlushStore(long self, CefCompletionCallback handler);
}
