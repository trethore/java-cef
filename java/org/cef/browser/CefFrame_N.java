// Copyright (c) 2017 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.browser;

import org.cef.callback.CefNativeAdapter;
import java.util.function.LongConsumer;

/**
 * This class represents all methods which are connected to the
 * native counterpart CEF.
 * The visibility of this class is "package".
 */
class CefFrame_N extends CefNativeAdapter implements CefFrame {
    CefFrame_N() {}

    @Override
    public void dispose() {
        cleanNativeResources(CefFrame_N::disposeNative);
    }

    @Override
    public String getIdentifier() {
        try {
            return N_GetIdentifier(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public String getURL() {
        try {
            return N_GetURL(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public String getName() {
        try {
            return N_GetName(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isMain() {
        try {
            return N_IsMain(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isValid() {
        try {
            return N_IsValid(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isFocused() {
        try {
            return N_IsFocused(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public CefFrame getParent() {
        try {
            return N_GetParent(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public void executeJavaScript(String code, String url, int line) {
        try {
            N_ExecuteJavaScript(getNativeRef(null), code, url, line);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void undo() {
        try {
            N_Undo(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void redo() {
        try {
            N_Redo(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void cut() {
        try {
            N_Cut(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void copy() {
        try {
            N_Copy(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void paste() {
        try {
            N_Paste(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    protected LongConsumer getDisposer() {
        return CefFrame_N::disposeNative;
    }

    private static void disposeNative(long handle) {
        if (handle == 0) return;
        new CleanupInvoker(handle).dispose();
    }

    private static final class CleanupInvoker extends CefFrame_N {
        private CleanupInvoker(long handle) {
            super();
            setNativeHandleUnsafe(handle);
        }

        public void dispose() {
            try {
                N_Dispose(getNativeRef(null));
            } catch (UnsatisfiedLinkError ule) {
                ule.printStackTrace();
            }
        }

        @Override
        protected LongConsumer getDisposer() {
            return null;
        }
    }

    public void selectAll() {
        try {
            N_SelectAll(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    protected final native void N_Dispose(long self);
    protected final native String N_GetIdentifier(long self);
    protected final native String N_GetURL(long self);
    protected final native String N_GetName(long self);
    protected final native boolean N_IsMain(long self);
    protected final native boolean N_IsValid(long self);
    protected final native boolean N_IsFocused(long self);
    protected final native CefFrame N_GetParent(long self);
    protected final native void N_ExecuteJavaScript(long self, String code, String url, int line);
    protected final native void N_Undo(long self);
    protected final native void N_Redo(long self);
    protected final native void N_Cut(long self);
    protected final native void N_Copy(long self);
    protected final native void N_Paste(long self);
    protected final native void N_SelectAll(long self);
}
