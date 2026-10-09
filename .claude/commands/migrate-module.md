---
description: Migrate a Java module to Kotlin with the java-to-kotlin-android skill, verify it, and prepare a PR
argument-hint: <module path> [--run-tests "<command>"] [--push]
---

Use the java-to-kotlin-migrator agent to migrate this module: $ARGUMENTS

Follow the agent's steps exactly: branch, convert file by file, run the verification gate, and prepare
the PR text. Do not push or open a PR unless the arguments include `--push` or I approve it after you
show me the gate result.
