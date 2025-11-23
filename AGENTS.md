# Repository Guidelines

## Project Structure & Module Organization
- `java/org/cef`: Public Java API and helpers; manifest files live in `java/manifest`.
- `java/tests`: JUnit 5 suites under `tests.junittests.*`; resources copied into `out/<platform>`.
- `native`: C++ JNI bindings and platform glue built with CMake; shared macros in `cmake/`.
- `third_party`: Vendored deps (CEF resources, JogAmp jars, JUnit console launcher).
- `tools`: Build/test utilities (`make_distrib.*`, `make_jar.*`, `run_tests.*`, `fix_style.*`).
- Outputs: native libs in `jcef_build/native/<Debug|Release>`; Java artifacts in `out/<platform>/jcef.jar` and `jcef-tests.jar`.

## Build, Test, and Development Commands
- Native (example Linux Debug): `mkdir -p jcef_build && cd jcef_build && cmake -G "Ninja" -DCMAKE_BUILD_TYPE=Debug .. && ninja`.
- Java jars (requires `JAVA_HOME` or `JDK_17`, plus `OUT_PATH` and `OUT_NAME`): `ant -f build.xml jar` → writes `jcef.jar` and `jcef-tests.jar` into `$OUT_PATH`.
- Generate JNI headers after native API changes: `tools/make_all_jni_headers.sh`.
- Run sample app (after building): `tools/run.sh <linux64|macosx64|win64> <Debug|Release>`.

## Testing Guidelines
- Prereqs: native binaries in `jcef_build/native/<Config>` and compiled Java classes in `out/<platform>`.
- Run all JUnit 5 suites: `tools/run_tests.sh <platform> <Debug|Release>`; extra args are passed to the JUnit console runner.
- Test classes follow `ClassNameTest` under `tests.junittests`; ensure explicit cleanup of JNI-backed resources in tests.
- Target deterministic tests; avoid UI timing/network dependencies when possible.

## Coding Style & Naming Conventions
- Formatting enforced via `tools/fix_style.sh` (or `.bat`): clang-format for C/C++/ObjC/Java, YAPF for Python.
- Follow Chromium style: spaces (no tabs), brace on same line, ~100-column target. C++ constants use `kCamelCase`; Java types use `UpperCamelCase`, members/methods `lowerCamelCase`; prefer explicit `dispose()`/`Cleaner` over `finalize`.
- Add only purposeful comments—short, clear, and located directly above the code they explain.
- New APIs should keep Java packages under `org.cef.*` and include JNI comment blocks near native mappings.

## Commit & Pull Request Guidelines
- Recent commits use concise, capitalized imperatives (e.g., “Add GLFW/LWJGL event handling…”). Keep subject <= 72 chars; explain rationale, risk, and platform/toolchain tested in the body.
- Reference issues when relevant (`Fixes #123`), and note whether `tools/run_tests.sh` passed for your platform/config.
- PRs should describe user-visible changes, build/test status, and any impact on bundled CEF/JogAmp versions; attach screenshots only when UI behavior changes.

## Security & Configuration Tips
- Keep the build directory named `jcef_build`; several scripts rely on it.
- Set `OUT_*` env vars when using Ant; do not commit generated binaries or modified third_party payloads without discussion.
- Pay attention to native resource ownership; prefer RAII/Cleaner patterns to avoid leaks in long-running embedders.
