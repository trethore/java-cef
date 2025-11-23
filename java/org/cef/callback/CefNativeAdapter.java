package org.cef.callback;

import java.util.function.LongConsumer;

import org.cef.misc.CefCleanup;

public class CefNativeAdapter implements CefNative {
    // Used internally to store a pointer to the CEF object.
    protected long N_CefHandle = 0;
    private final CefCleanup.Registration cleanup = new CefCleanup.Registration();

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
        LongConsumer disposer = getDisposer();
        if (disposer != null) {
            cleanup.register(this, nativeRef, disposer);
        }
    }

    /**
     * Assign the native reference without registering a cleaner. Intended for
     * internal one-shot disposer helpers that must not self-register.
     */
    protected void setNativeHandleUnsafe(long nativeRef) {
        N_CefHandle = nativeRef;
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    /**
     * Subclasses can override to supply a native disposer. When present, the
     * disposer will be registered with a Cleaner when the native reference is
     * set. The disposer must only capture values required to release the native
     * handle.
     */
    protected LongConsumer getDisposer() {
        return null;
    }

    /**
     * Executes the registered cleanup action immediately (typically from an
     * explicit dispose method). If no cleanup was registered the provided
     * disposer will be executed directly.
     */
    protected void cleanNativeResources(LongConsumer disposer) {
        long handle = N_CefHandle;
        cleanup.clean(handle, disposer);
        N_CefHandle = 0;
    }
}
