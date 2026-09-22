# Upgrade Plan: dam2627java (20260922145221)

- **Generated**: 2026-09-22 14:55
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A (not exposed by version-control status)

## Available Tools

**JDKs**
- JDK 11: not available (baseline will be skipped)
- JDK 21: `C:\Program Files\Java\jdk-21.0.11\bin` (installed, not required for target)
- JDK 25: **<TO_BE_INSTALLED>** (required by upgrade and final validation)
- JDK 26: `C:\Program Files\Java\jdk-26.0.1\bin` (installed, not required; Java 25 is the latest LTS)

**Build Tools**
- Maven 3.9.16: `C:\tools\apache-maven\bin`
- Maven Wrapper: not present

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260922145221
- Run tests before and after the upgrade: true
- Auto-execution: enabled by user request

## Upgrade Goals

- Java runtime and compilation target: 25 (latest LTS)

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| --------------------- | ------- | ---------------------- | ---------------- |
| Java | 11 | 25 | User requested Java 25 LTS |
| Maven | 3.9.16 | 3.9+ | Compatible with Java 25; no upgrade required |
| maven-compiler-plugin | 3.8.0 | 3.11+ | Current version is old; newer plugin is recommended for Java 25 release handling |
| JavaFX controls/fxml | 13 | 13 | No source incompatibility identified; retain to minimize scope |
| javafx-maven-plugin | 0.0.6 | 0.0.6 | No direct Java 25 incompatibility identified |
| MySQL Connector/J | 9.3.0 | 9.3.0 | No change required |
| Gson | 2.10.1 | 2.10.1 | No change required |

## Derived Upgrades

- Install JDK 25 because the target runtime is not currently available.
- Set Maven compiler `source`, `target`, and `release` values to 25 so main and test compilation use the target runtime.
- Upgrade `maven-compiler-plugin` to 3.14.1 because the existing 3.8.0 predates Java 25 and is not a suitable compatibility baseline.
- No Kotlin, Spring, Jakarta EE, CI/CD, or test sources are present; no related migration is required.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| `pom.xml` | `maven.compiler.source` | 11 | upgrade | 25 | Set project source level to Java 25 |
| `pom.xml` | `maven.compiler.target` | 11 | upgrade | 25 | Emit Java 25 bytecode |
| `pom.xml` | `maven-compiler-plugin` | 3.8.0 | upgrade | 3.14.1 | Modern compiler plugin compatibility for Java 25 |
| `pom.xml` | `maven-compiler-plugin` release | 11 | upgrade | 25 | Compile against Java 25 APIs and language level |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|-----------------|--------|
| None | N/A | No Java 11-specific APIs or internal JDK imports found | No source changes | The source uses JavaFX, JDBC, and standard Java APIs compatible with Java 25 |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|-----------------|--------|
| None | N/A | No application Java-version configuration found | No change | Runtime target is controlled by Maven properties and compiler release |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|-----------------|
| None | N/A | No CI/CD configuration files found | No change |

### Risks & Warnings

- **JavaFX 13 on Java 25**: JavaFX remains pinned at 13 and may expose runtime compatibility issues despite successful compilation. **Mitigation**: run the full Maven test lifecycle and perform a packaged/runtime launch check where the environment permits; only upgrade JavaFX if verification identifies a concrete incompatibility.
- **No test sources**: the project contains no `src/test` files, so the test phase can only prove that Maven completes successfully with zero discovered tests. **Mitigation**: validate compilation and inspect the application entry point/runtime packaging.
- **Database/UI runtime dependencies**: MySQL connectivity and JavaFX display behavior require external runtime conditions not available to Maven compilation. **Mitigation**: preserve existing dependency versions and report any environment-specific runtime limitation.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Provision the requested Java 25 runtime before changing the build target.
  - **Changes to Make**: Install JDK 25 and verify it is discoverable.
  - **Verification**: List JDKs; Java 25 installation path is available.

- Step 2: Setup Baseline
  - **Rationale**: Establish pre-upgrade build/test status when the current JDK is available.
  - **Changes to Make**: None.
  - **Verification**: Skipped because JDK 11 is not installed; baseline cannot be executed.

- Step 3: Upgrade Maven Java Target
  - **Rationale**: Change the project compiler configuration to Java 25 and use a compiler plugin suitable for the target release.
  - **Changes to Make**: Apply all Dependency Changes listed above in `pom.xml`.
  - **Verification**: `mvn clean test-compile -q` using JDK 25; main and test compilation succeeds.

- Step 4: Final Validation
  - **Rationale**: Confirm the upgraded project builds and its complete test lifecycle passes.
  - **Changes to Make**: Resolve any Java 25 compilation or test failures found during validation.
  - **Verification**: `mvn clean test -q` using JDK 25; 100% of discovered tests pass, with zero tests expected in this repository.

- Step 5: CVE Validation & Fix
  - **Rationale**: Check direct dependencies after the upgrade and remediate any reported vulnerabilities.
  - **Changes to Make**: Upgrade only vulnerable direct dependencies or managed versions required by the scan.
  - **Verification**: Rebuild and re-scan; no remediable CVEs remain.

- Step 6: Summary and Cleanup
  - **Rationale**: Record verification, coverage, risks, and final upgrade status.
  - **Changes to Make**: Generate the upgrade summary and remove temporary artifacts created by the workflow where appropriate.
  - **Verification**: All completed steps and success criteria are recorded.
