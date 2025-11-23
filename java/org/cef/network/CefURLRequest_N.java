// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.network;

import org.cef.callback.CefNative;
import org.cef.callback.CefURLRequestClient;
import org.cef.handler.CefLoadHandler.ErrorCode;
import org.cef.misc.CefCleanup;

class CefURLRequest_N extends CefURLRequest implements CefNative {
    // Used internally to store a pointer to the CEF object.
    protected long N_CefHandle = 0;
    private final CefRequest request_;
    private final CefURLRequestClient client_;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
        cleanup.register(this, nativeRef, CefURLRequest_N::disposeNative);
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    CefURLRequest_N(CefRequest request, CefURLRequestClient client) {
        super();
        request_ = request;
        client_ = client;
    }

    public static final CefURLRequest createNative(CefRequest request, CefURLRequestClient client) {
        // keep a reference to the request and client objects.
        CefURLRequest_N result = new CefURLRequest_N(request, client);
        try {
            result.N_Create(request, client);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        if (result.N_CefHandle == 0) return null;
        return result;
    }

    @Override
    public void dispose() {
        long handle = N_CefHandle;
        N_CefHandle = 0;
        cleanup.clean(handle, CefURLRequest_N::disposeNative);
    }

    @Override
    public CefRequest getRequest() {
        return request_;
    }

    @Override
    public CefURLRequestClient getClient() {
        return client_;
    }

    @Override
    public Status getRequestStatus() {
        try {
            return N_GetRequestStatus(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public ErrorCode getRequestError() {
        try {
            return N_GetRequestError(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public CefResponse getResponse() {
        try {
            return N_GetResponse(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public void cancel() {
        try {
            N_Cancel(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new NativeDisposer(handle).dispose();
    }

    private static final class NativeDisposer extends CefURLRequest_N {
        private NativeDisposer(long handle) {
            super(null, null);
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

    protected final native void N_Create(CefRequest request, CefURLRequestClient client);
    protected final native void N_Dispose(long self);
    protected final native Status N_GetRequestStatus(long self);
    protected final native ErrorCode N_GetRequestError(long self);
    protected final native CefResponse N_GetResponse(long self);
    protected final native void N_Cancel(long self);
}
