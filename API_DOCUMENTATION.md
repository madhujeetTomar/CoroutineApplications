# Coroutine Applications API Documentation

## Project Overview

**Package:** `com.mj.coroutineapplications`  
**Version:** 1.0  
**Min SDK:** 21  
**Target SDK:** 33  
**Build Tool:** Gradle 7.0.2  
**Kotlin Version:** 1.6.10  
**Coroutines Version:** 1.6.3  

This Android application demonstrates the usage of Kotlin Coroutines for asynchronous programming, featuring timeout handling, concurrent execution, and context switching between background and main threads.

## Table of Contents

1. [MainActivity](#mainactivity)
2. [Public Methods](#public-methods)
3. [Private Methods](#private-methods)
4. [UI Components](#ui-components)
5. [Resources](#resources)
6. [Build Configuration](#build-configuration)
7. [Testing](#testing)
8. [Usage Examples](#usage-examples)

---

## MainActivity

### Class Definition

```kotlin
class MainActivity : AppCompatActivity()
```

**Package:** `com.mj.coroutineapplications`  
**Extends:** `AppCompatActivity`  
**Purpose:** Main entry point of the application demonstrating coroutine usage patterns

### Properties

#### Public Properties

None - all properties are internal to the class.

#### Internal Properties

| Property | Type | Description |
|----------|------|-------------|
| `tvResult` | `TextView` | Displays the results of coroutine operations |
| `clickMe` | `Button` | Triggers the coroutine demonstration |

---

## Public Methods

### onCreate(savedInstanceState: Bundle?)

```kotlin
override fun onCreate(savedInstanceState: Bundle?)
```

**Purpose:** Initializes the activity and sets up UI components and event listeners.

**Parameters:**
- `savedInstanceState: Bundle?` - Saved state from previous instance (nullable)

**Behavior:**
1. Sets the content view to `activity_main` layout
2. Initializes UI components (`tvResult` and `clickMe`)
3. Sets up click listener for the button
4. Launches coroutine demonstration on button click

**Example Usage:**
```kotlin
// Called automatically by Android framework
// No direct invocation required
```

---

## Private Methods

### fakeApiRequest()

```kotlin
private suspend fun fakeApiRequest()
```

**Purpose:** Demonstrates coroutine timeout handling and sequential execution with timeout.

**Features:**
- Uses `withTimeoutOrNull(1900)` for timeout handling
- Executes two sequential API calls
- Updates UI with results or timeout message
- Switches context between IO and Main threads

**Execution Flow:**
1. Calls `result()` and updates UI
2. Calls `result2()` and updates UI
3. If operations exceed 1900ms, displays timeout message

**Example:**
```kotlin
// Internal usage within onClick listener
CoroutineScope(Dispatchers.IO).launch {
    fakeApiRequest()
}
```

### fakeApiRequest1()

```kotlin
private suspend fun fakeApiRequest1()
```

**Purpose:** Demonstrates concurrent coroutine execution using `async` and `awaitAll`.

**Features:**
- Concurrent execution of multiple suspend functions
- Uses `async` for parallel processing
- Waits for all operations to complete with `awaitAll`

**Execution Flow:**
1. Creates two async coroutines for `result()` and `result2()`
2. Waits for both to complete
3. Updates UI with combined results

**Example:**
```kotlin
// Alternative implementation (currently commented out)
private suspend fun fakeApiRequest1() {
    withContext(Dispatchers.IO) {
        val result = async { result() }
        val result2 = async { result2() }
        val deferred = awaitAll(result, result2)
        setResult(deferred)
    }
}
```

### setResult(result: String)

```kotlin
private suspend fun setResult(result: String)
```

**Purpose:** Updates the UI with a single result string.

**Parameters:**
- `result: String` - The text to append to the current display

**Behavior:**
- Switches to Main dispatcher for UI updates
- Appends new result to existing text with newline separator
- Thread-safe UI updates

**Context Switching:**
```kotlin
withContext(Dispatchers.Main) {
    val newText = tvResult.text.toString() + "\n$result"
    tvResult.text = newText
}
```

### setResult(deferred: List<String>)

```kotlin
private suspend fun setResult(deferred: List<String>)
```

**Purpose:** Updates the UI with multiple results from concurrent operations.

**Parameters:**
- `deferred: List<String>` - List of results from concurrent coroutines

**Behavior:**
- Switches to Main dispatcher for UI updates
- Combines all results into a single display string
- Thread-safe UI updates

**Usage:**
```kotlin
withContext(Dispatchers.Main) {
    val newText = "${deferred[0]} ${deferred[1]}"
    tvResult.text = newText
}
```

### result()

```kotlin
private suspend fun result(): String
```

**Purpose:** Simulates a long-running API call or background operation.

**Returns:** `String` - Always returns "Result"

**Behavior:**
- Introduces 5-second delay using `delay(5000)`
- Simulates network or database operation
- Non-blocking suspension

**Example:**
```kotlin
val apiResult = result() // Takes 5 seconds to complete
println(apiResult) // Prints: "Result"
```

### result2()

```kotlin
private suspend fun result2(): String
```

**Purpose:** Simulates another long-running API call or background operation.

**Returns:** `String` - Always returns "Result2"

**Behavior:**
- Introduces 5-second delay using `delay(5000)`
- Simulates network or database operation
- Non-blocking suspension

**Example:**
```kotlin
val apiResult = result2() // Takes 5 seconds to complete
println(apiResult) // Prints: "Result2"
```

---

## UI Components

### Layout Structure

**File:** `app/src/main/res/layout/activity_main.xml`

#### Root Container
```xml
<androidx.constraintlayout.widget.ConstraintLayout>
```
- **Width:** `match_parent`
- **Height:** `match_parent`
- **Layout System:** ConstraintLayout

#### TextView (tvResult)
```xml
<TextView android:id="@+id/tvResult">
```

**Properties:**
- **ID:** `@+id/tvResult`
- **Width:** `wrap_content`
- **Height:** `wrap_content`
- **Default Text:** "Hello World!"
- **Position:** Centered in parent
- **Purpose:** Displays coroutine operation results

**Constraints:**
- Bottom to bottom of parent
- Left to left of parent
- Right to right of parent
- Top to top of parent

#### Button (clickMe)
```xml
<Button android:id="@+id/button">
```

**Properties:**
- **ID:** `@+id/button`
- **Width:** `wrap_content`
- **Height:** `wrap_content`
- **Text:** "ClickMe"
- **Top Margin:** 100dp
- **Purpose:** Triggers coroutine demonstration

**Constraints:**
- Bottom to top of tvResult
- End to end of parent
- Start to start of parent
- Top to top of parent

---

## Resources

### Strings

**File:** `app/src/main/res/values/strings.xml`

| String ID | Value | Usage |
|-----------|-------|-------|
| `app_name` | "CoroutineApplications" | Application name |
| `loading` | "Loading..." | Loading state indicator |

**Example Usage:**
```kotlin
tvResult.text = getString(R.string.loading)
```

### Application Configuration

**File:** `app/src/main/AndroidManifest.xml`

#### Application Properties
- **Package:** `com.mj.coroutineapplications`
- **Allow Backup:** `true`
- **Icon:** `@mipmap/ic_launcher`
- **Round Icon:** `@mipmap/ic_launcher_round`
- **RTL Support:** `true`
- **Theme:** `@style/Theme.CoroutineApplications`

#### MainActivity Configuration
- **Name:** `.MainActivity`
- **Exported:** `true`
- **Launch Intent:** `MAIN` action, `LAUNCHER` category

---

## Build Configuration

### Root Build Configuration

**File:** `build.gradle`

#### Build Dependencies
```gradle
dependencies {
    classpath "com.android.tools.build:gradle:7.0.2"
    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
}
```

#### Repositories
- Google Maven Repository
- Maven Central

### App Module Configuration

**File:** `app/build.gradle`

#### Android Configuration
```gradle
android {
    compileSdk 33
    defaultConfig {
        applicationId "com.mj.coroutineapplications"
        minSdk 21
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }
}
```

#### Key Dependencies
```gradle
dependencies {
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3"
    
    // Android Core
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.6.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Kotlin Standard Library
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10"
}
```

---

## Testing

### Unit Tests

**File:** `app/src/test/java/com/mj/coroutineapplications/ExampleUnitTest.kt`

#### ExampleUnitTest Class

```kotlin
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
```

**Purpose:** Basic unit test example  
**Framework:** JUnit  
**Execution:** Local development machine

### Instrumented Tests

**File:** `app/src/androidTest/java/com/mj/coroutineapplications/ExampleInstrumentedTest.kt`

#### ExampleInstrumentedTest Class

```kotlin
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mj.coroutineapplications", appContext.packageName)
    }
}
```

**Purpose:** Context validation test  
**Framework:** AndroidJUnit4  
**Execution:** Android device/emulator

---

## Usage Examples

### Basic Application Flow

1. **Launch Application**
   ```kotlin
   // MainActivity automatically created by Android system
   // onCreate() initializes UI components
   ```

2. **Trigger Coroutine Demo**
   ```kotlin
   // User clicks "ClickMe" button
   clickMe.setOnClickListener {
       tvResult.text = getString(R.string.loading)
       CoroutineScope(Dispatchers.IO).launch {
           fakeApiRequest()
       }
   }
   ```

3. **Execute Coroutines with Timeout**
   ```kotlin
   private suspend fun fakeApiRequest() {
       withContext(Dispatchers.IO) {
           val job = withTimeoutOrNull(1900) {
               val result1 = result()      // 5 seconds
               setResult(result1)
               val result2 = result2()     // 5 seconds
               setResult(result2)
           }
           if (job == null) {
               setResult("Cancelled Job due to TimeOut")
           }
       }
   }
   ```

### Concurrent Execution Example

```kotlin
// Alternative implementation for parallel execution
private suspend fun executeParallel() {
    withContext(Dispatchers.IO) {
        val deferred1 = async { result() }
        val deferred2 = async { result2() }
        val results = awaitAll(deferred1, deferred2)
        setResult(results)
    }
}
```

### Context Switching Pattern

```kotlin
// Background work
withContext(Dispatchers.IO) {
    val data = performNetworkCall()
    
    // Switch to Main thread for UI updates
    withContext(Dispatchers.Main) {
        updateUI(data)
    }
}
```

### Error Handling with Timeout

```kotlin
val result = withTimeoutOrNull(timeoutMillis) {
    longRunningOperation()
}

if (result == null) {
    handleTimeout()
} else {
    handleSuccess(result)
}
```

---

## Best Practices Demonstrated

1. **Proper Context Switching:** Always switch to `Dispatchers.Main` for UI updates
2. **Timeout Handling:** Use `withTimeoutOrNull` for operations that might hang
3. **Concurrent Execution:** Use `async` and `awaitAll` for parallel operations
4. **Resource Management:** Proper coroutine scope management
5. **Thread Safety:** Ensure UI updates happen on the main thread

---

## Dependencies and Requirements

### Minimum Requirements
- **Android SDK:** API 21 (Android 5.0)
- **Kotlin:** 1.6.10
- **Gradle:** 7.0.2

### Key Libraries
- **Kotlinx Coroutines Core:** 1.6.3
- **Kotlinx Coroutines Android:** 1.6.3
- **AndroidX Core KTX:** 1.9.0
- **AppCompat:** 1.4.1
- **Material Design:** 1.6.0
- **ConstraintLayout:** 2.1.4

---

## Installation and Setup

1. **Clone the repository**
2. **Open in Android Studio**
3. **Sync Gradle dependencies**
4. **Run on device or emulator**

```bash
./gradlew assembleDebug
./gradlew installDebug
```

---

*This documentation covers all public APIs, functions, and components in the Coroutine Applications project. For additional implementation details, refer to the source code files.*