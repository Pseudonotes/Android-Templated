# Android-Templated
An android template for me


# Android App Template with CI/CD, Firebase Distribution & Slack Notifications

This Android project template helps you build multi-environment apps with:

- Build flavors: `dev`, `staging`, `prod`
- Local & CI secrets support (`secrets.properties` + GitHub secrets)
- Automated APK & AAB builds via GitHub Actions
- Firebase App Distribution integration
- Slack build status notifications
- Unit testing support


## Project Structure

```
.github/workflows/
├── build-flavors.yaml         # Manual APK builds for flavors
├── gradle-build.yml           # PR builds & unit tests
└── release-to-firebase.yml    # Firebase AAB release for staging/prod

app/build.gradle.kts           # Secrets loading + productFlavors setup
secrets.properties             # (Optional) Local secrets file (not committed)
```

---

## Getting Started

### 1. Clone the Project

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME
```

---

### 2. Configure GitHub Secrets

Go to **Settings > Secrets and variables > Actions > Repository secrets**, then add:

| Secret Name                | Description                                       |
|----------------------------|---------------------------------------------------|
| ENV                        | Default env override (e.g., `dev`, `staging`)     |
| FIREBASE_APP_ID            | Your Firebase App ID                              |
| FIREBASE_SERVICE_ACCOUNT   | Firebase service account JSON as a single-line string |
| SLACK_WEBHOOK              | Slack Incoming Webhook URL                        |
| SIGNING_KEY (optional)     | Base64 encoded keystore file                      |

To encode your `release.keystore` file:

```bash
base64 release.keystore
```

Copy the output and save it as the value for the `SIGNING_KEY` secret.

---

### 3. Create `secrets.properties` (Optional)

For local development, you can create a `secrets.properties` file in your root project directory:

```properties
ENV=dev
```

> This file is read by the build script and overrides environment variables locally.  
> **Do not commit this file** – add it to your `.gitignore`.

---

### 4. Product Flavors

Defined in `app/build.gradle.kts`:

```kotlin
productFlavors {
    create("dev") {
        dimension = "env"
        buildConfigField("String", "ENV", "\"dev\"")
    }
    create("staging") {
        dimension = "env"
        buildConfigField("String", "ENV", "\"staging\"")
    }
    create("prod") {
        dimension = "env"
        buildConfigField("String", "ENV", "\"prod\"")
    }
}
```

The value is resolved using the `getSecret()` function, which checks `secrets.properties` first, then `System.getenv()`.

---

## GitHub Workflows

### 1. Build APK with Environment

**Path:** `.github/workflows/build-flavors.yaml`  
**Trigger:** Manual (from GitHub Actions)

- Choose a flavor (`dev`, `staging`, `prod`)
- Builds the APK
- Uploads the APK as an artifact
- Posts result to Slack

---

### 2. Gradle CI Build

**Path:** `.github/workflows/gradle-build.yml`  
**Trigger:** Pull Requests to `main` and manual trigger

- Builds APK for selected flavor
- Runs unit tests
- Posts result to Slack

---

### 3. Release to Firebase

**Path:** `.github/workflows/release-to-firebase.yml`  
**Trigger:** Manual  
**Inputs:** `staging` or `prod`

- Builds AAB file for selected flavor
- Uploads AAB to Firebase App Distribution
- Uploads artifact to GitHub

---

## Local Development

To build and test locally:

```bash
# Build Debug APK
./gradlew assembleDevDebug

# Run unit tests
./gradlew testDevDebugUnitTest

# Clean project
./gradlew clean
```

---

## Tips

- Use `BuildConfig.ENV` to access the environment at runtime.
- Ensure JDK 17 is installed locally to match GitHub Actions.
- You can change environment dynamically using GitHub secrets or local `secrets.properties`.

---

## Troubleshooting

| Problem                     | Solution                                                             |
|-----------------------------|----------------------------------------------------------------------|
| Firebase upload fails       | Ensure `FIREBASE_SERVICE_ACCOUNT` is valid JSON string               |
| AAB not found               | Double-check flavor name and AAB path in workflow                    |
| Docker permission error     | Run: `sudo usermod -aG docker $USER && newgrp docker`                |
| Missing environment secrets | Ensure secrets are correctly set in GitHub repo                      |

---

## License

This project is maintained by [Your Name or Org].  
Licensed under the MIT License.

---

## Contributing

Feel free to fork this repo, open issues, or submit PRs to improve build automation, flavor support, or integrations.

