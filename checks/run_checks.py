"""Run a module's behavior check against its current sources.

Usage:  py -3 checks/run_checks.py <module>            compile the module's Kotlin (and any Java still
                                                       there), then run checks/<module>/*Check.java
        py -3 checks/run_checks.py <module> --java     compile only the Java sources (the baseline)

The check is a Java program, so it tests the module the way Java callers use it. Exit code 0 means the
checks passed. It needs javac, java and (for Kotlin) kotlinc on the PATH.
"""

import os
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path

REPO = Path(__file__).resolve().parent.parent


def tool(name: str) -> str:
    found = shutil.which(name)
    if not found:
        raise SystemExit(f"{name} not found on the PATH")
    return found


def run(cmd: list, label: str) -> None:
    done = subprocess.run(cmd, capture_output=True, text=True, encoding="utf-8", errors="replace")
    if done.returncode != 0:
        lines = [ln for ln in (done.stdout + done.stderr).splitlines() if ln.strip()]
        print(f"{label} failed:\n  " + "\n  ".join(lines[:15]))
        raise SystemExit(1)


def kotlin_stdlib() -> str:
    home = os.environ.get("KOTLIN_HOME")
    base = Path(home) if home else Path(tool("kotlinc")).resolve().parent.parent
    for candidate in (base / "lib" / "kotlin-stdlib.jar", base / "kotlinc" / "lib" / "kotlin-stdlib.jar"):
        if candidate.exists():
            return str(candidate)
    raise SystemExit("kotlin-stdlib.jar not found; set KOTLIN_HOME")


def main() -> int:
    args = [a for a in sys.argv[1:] if not a.startswith("--")]
    java_only = "--java" in sys.argv[1:]
    if len(args) != 1:
        print(__doc__)
        return 2
    module = args[0]
    source_dir = REPO / "app" / "src" / "main" / "java" / "com" / "example" / module
    check_files = sorted((REPO / "checks" / module).glob("*Check.java"))
    if not source_dir.is_dir() or not check_files:
        print(f"no module folder or no check for {module!r}")
        return 2

    with tempfile.TemporaryDirectory(prefix="module-check-") as scratch:
        classes = Path(scratch) / "classes"
        harness = Path(scratch) / "harness"
        classes.mkdir()
        harness.mkdir()
        java_sources = [str(p) for p in sorted(source_dir.glob("*.java"))]
        kotlin_sources = [] if java_only else [str(p) for p in sorted(source_dir.glob("*.kt"))]
        stdlib = [] if java_only else [kotlin_stdlib()]

        if kotlin_sources:
            run([tool("kotlinc"), "-nowarn", "-d", str(classes), *kotlin_sources, *java_sources], "Kotlin compile")
            if java_sources:   # a partly converted module: the Java that is left compiles against the Kotlin
                run([tool("javac"), "-proc:none", "-cp", os.pathsep.join([str(classes), *stdlib]),
                     "-d", str(classes), *java_sources], "Java compile")
        else:
            run([tool("javac"), "-proc:none", "-d", str(classes), *java_sources], "Java compile")

        classpath = os.pathsep.join([str(classes), *stdlib])
        run([tool("javac"), "-proc:none", "-cp", classpath, "-d", str(harness), *map(str, check_files)],
            "Check compile (a Java caller no longer compiles against the module)")
        for check in check_files:
            done = subprocess.run([tool("java"), "-cp", os.pathsep.join([str(harness), classpath]), check.stem],
                                  capture_output=True, text=True, encoding="utf-8", errors="replace")
            print(done.stdout.strip() or done.stderr.strip())
            if done.returncode != 0:
                return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
