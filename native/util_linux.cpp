// Copyright (c) 2015 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

#include "util.h"

#include <X11/Xlib.h>
#undef Success

#include "include/base/cef_callback.h"

#include "critical_wait.h"
#include "jni_util.h"
#include "temp_window.h"
#include <utility>

namespace util {

namespace {

void X_XMoveResizeWindow(unsigned long browserHandle,
                         int x,
                         int y,
                         unsigned int width,
                         unsigned int height) {
  ::Display* xdisplay = (::Display*)TempWindow::GetDisplay();
  XMoveResizeWindow(xdisplay, browserHandle, 0, 0, width, height);
  XFlush(xdisplay);
}

}  // namespace

// This function is called by LifeSpanHandler::OnAfterCreated().
void AddCefBrowser(CefRefPtr<CefBrowser> browser) {
  // TODO(jcef): Implement this function stub to do some platform dependent
  // tasks for the browser reference like registering mouse events.

  UNUSED(browser);
}

// This function is called by LifeSpanHandler::DoClose().
void DestroyCefBrowser(CefRefPtr<CefBrowser> browser) {
  browser->GetHost()->CloseBrowser(true);
}

void SetWindowBounds(CefWindowHandle browserHandle,
                     const CefRect& contentRect) {
  X_XMoveResizeWindow(browserHandle, contentRect.x, contentRect.y,
                      contentRect.width, contentRect.height);
}

void SetWindowSize(CefWindowHandle browserHandle, int width, int height) {
  X_XMoveResizeWindow(browserHandle, 0, 0, width, height);
}

void SetParent(CefWindowHandle browserHandle,
               CefWindowHandle parentHandle,
               base::OnceClosure callback) {
  ::Display* xdisplay = (::Display*)TempWindow::GetDisplay();
  if (parentHandle != 0)
    XReparentWindow(xdisplay, browserHandle, parentHandle, 0, 0);
  XMapWindow(xdisplay, browserHandle);
  XFlush(xdisplay);

  if (callback)
    std::move(callback).Run();
}

void SetParentSync(CefWindowHandle browserHandle,
                   CefWindowHandle parentHandle,
                   CriticalWait* waitCond,
                   base::OnceClosure callback) {
  SetParent(browserHandle, parentHandle, std::move(callback));
  if (waitCond)
    waitCond->Signal();
}

}  // namespace util
