// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.Date;

class CefDownloadItem_N extends CefNativeAdapter implements CefDownloadItem {
    CefDownloadItem_N() {}

    @Override
    public boolean isValid() {
        try {
            return N_IsValid(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isInProgress() {
        try {
            return N_IsInProgress(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isComplete() {
        try {
            return N_IsComplete(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isCanceled() {
        try {
            return N_IsCanceled(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public long getCurrentSpeed() {
        try {
            return N_GetCurrentSpeed(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getPercentComplete() {
        try {
            return N_GetPercentComplete(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public long getTotalBytes() {
        try {
            return N_GetTotalBytes(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public long getReceivedBytes() {
        try {
            return N_GetReceivedBytes(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public Date getStartTime() {
        try {
            return N_GetStartTime(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public Date getEndTime() {
        try {
            return N_GetEndTime(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public String getFullPath() {
        try {
            return N_GetFullPath(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public int getId() {
        try {
            return N_GetId(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getURL() {
        try {
            return N_GetURL(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public String getSuggestedFileName() {
        try {
            return N_GetSuggestedFileName(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public String getContentDisposition() {
        try {
            return N_GetContentDisposition(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public String getMimeType() {
        try {
            return N_GetMimeType(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    protected final native boolean N_IsValid(long self);
    protected final native boolean N_IsInProgress(long self);
    protected final native boolean N_IsComplete(long self);
    protected final native boolean N_IsCanceled(long self);
    protected final native long N_GetCurrentSpeed(long self);
    protected final native int N_GetPercentComplete(long self);
    protected final native long N_GetTotalBytes(long self);
    protected final native long N_GetReceivedBytes(long self);
    protected final native Date N_GetStartTime(long self);
    protected final native Date N_GetEndTime(long self);
    protected final native String N_GetFullPath(long self);
    protected final native int N_GetId(long self);
    protected final native String N_GetURL(long self);
    protected final native String N_GetSuggestedFileName(long self);
    protected final native String N_GetContentDisposition(long self);
    protected final native String N_GetMimeType(long self);
}
