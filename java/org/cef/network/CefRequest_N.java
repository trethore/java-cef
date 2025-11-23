// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.network;

import org.cef.callback.CefNative;
import org.cef.misc.CefCleanup;

import java.util.Map;

class CefRequest_N extends CefRequest implements CefNative {
    // Used internally to store a pointer to the CEF object.
    protected long N_CefHandle = 0;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
        cleanup.register(this, nativeRef, CefRequest_N::disposeNative);
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    CefRequest_N() {
        super();
    }

    public static CefRequest createNative() {
        try {
            return CefRequest_N.N_Create();
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public void dispose() {
        long handle = N_CefHandle;
        N_CefHandle = 0;
        cleanup.clean(handle, CefRequest_N::disposeNative);
    }

    @Override
    public long getIdentifier() {
        try {
            return N_GetIdentifier(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        try {
            return N_IsReadOnly(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public String getURL() {
        try {
            return N_GetURL(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void setURL(String url) {
        try {
            N_SetURL(N_CefHandle, url);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getMethod() {
        try {
            return N_GetMethod(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void setMethod(String string) {
        try {
            N_SetMethod(N_CefHandle, string);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void setReferrer(String url, ReferrerPolicy policy) {
        try {
            N_SetReferrer(N_CefHandle, url, policy);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getReferrerURL() {
        try {
            return N_GetReferrerURL(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public ReferrerPolicy getReferrerPolicy() {
        try {
            return N_GetReferrerPolicy(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public CefPostData getPostData() {
        try {
            return N_GetPostData(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void setPostData(CefPostData postData) {
        try {
            N_SetPostData(N_CefHandle, postData);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getHeaderByName(String name) {
        try {
            return N_GetHeaderByName(N_CefHandle, name);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void setHeaderByName(String name, String value, boolean overwrite) {
        try {
            N_SetHeaderByName(N_CefHandle, name, value, overwrite);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void getHeaderMap(Map<String, String> headerMap) {
        try {
            N_GetHeaderMap(N_CefHandle, headerMap);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void setHeaderMap(Map<String, String> headerMap) {
        try {
            N_SetHeaderMap(N_CefHandle, headerMap);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void set(
            String url, String method, CefPostData postData, Map<String, String> headerMap) {
        try {
            N_Set(N_CefHandle, url, method, postData, headerMap);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public int getFlags() {
        try {
            return N_GetFlags(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setFlags(int flags) {
        try {
            N_SetFlags(N_CefHandle, flags);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getFirstPartyForCookies() {
        try {
            return N_GetFirstPartyForCookies(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void setFirstPartyForCookies(String url) {
        try {
            N_SetFirstPartyForCookies(N_CefHandle, url);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public ResourceType getResourceType() {
        try {
            return N_GetResourceType(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return ResourceType.RT_MAIN_FRAME;
    }

    @Override
    public TransitionType getTransitionType() {
        try {
            return N_GetTransitionType(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return TransitionType.TT_AUTO_SUBFRAME;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new NativeDisposer(handle).dispose();
    }

    private static final class NativeDisposer extends CefRequest_N {
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

    protected final native static CefRequest_N N_Create();
    protected final native void N_Dispose(long self);
    protected final native long N_GetIdentifier(long self);
    protected final native boolean N_IsReadOnly(long self);
    protected final native String N_GetURL(long self);
    protected final native void N_SetURL(long self, String url);
    protected final native String N_GetMethod(long self);
    protected final native void N_SetMethod(long self, String method);
    protected final native void N_SetReferrer(long self, String url, ReferrerPolicy policy);
    protected final native String N_GetReferrerURL(long self);
    protected final native ReferrerPolicy N_GetReferrerPolicy(long self);
    protected final native CefPostData N_GetPostData(long self);
    protected final native void N_SetPostData(long self, CefPostData postData);
    protected final native String N_GetHeaderByName(long self, String name);
    protected final native void N_SetHeaderByName(
            long self, String name, String value, boolean overwrite);
    protected final native void N_GetHeaderMap(long self, Map<String, String> headerMap);
    protected final native void N_SetHeaderMap(long self, Map<String, String> headerMap);
    protected final native void N_Set(long self, String url, String method, CefPostData postData,
            Map<String, String> headerMap);
    protected final native int N_GetFlags(long self);
    protected final native void N_SetFlags(long self, int flags);
    protected final native String N_GetFirstPartyForCookies(long self);
    protected final native void N_SetFirstPartyForCookies(long self, String url);
    protected final native ResourceType N_GetResourceType(long self);
    protected final native TransitionType N_GetTransitionType(long self);
}
