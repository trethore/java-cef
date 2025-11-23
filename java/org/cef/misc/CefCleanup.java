// Copyright (c) 2025 The Chromium Embedded Framework Authors.
// All rights reserved. Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package org.cef.misc;

import java.lang.ref.Cleaner;
import java.util.function.LongConsumer;

/**
 * Utility for registering native resource cleanup using {@link Cleaner} instead
 * of finalization. Keeps the cleanup action separate from the referent to avoid
 * retaining the object graph.
 */
public final class CefCleanup {
    private static final Cleaner CLEANER = Cleaner.create();

    private CefCleanup() {}

    public static Cleaner.Cleanable register(Object referent, long handle, LongConsumer disposer) {
        return CLEANER.register(referent, new CleanupAction(handle, disposer));
    }

    private record CleanupAction(long handle, LongConsumer disposer) implements Runnable {
        @Override
        public void run() {
            if (handle != 0) {
                disposer.accept(handle);
            }
        }
    }

    /**
     * Small helper that stores registration state per instance.
     */
    public static final class Registration {
        private Cleaner.Cleanable cleanable;

        public void register(Object referent, long handle, LongConsumer disposer) {
            if (cleanable == null && handle != 0) {
                cleanable = CefCleanup.register(referent, handle, disposer);
            }
        }

        public void clean(long handle, LongConsumer disposer) {
            if (cleanable != null) {
                cleanable.clean();
                cleanable = null;
            } else if (handle != 0) {
                disposer.accept(handle);
            }
        }
    }
}
