<p align="center"><img src="https://github.com/ahmmedrejowan/RotaryKnob/blob/master/files/logo.svg?raw=true" width="100px" align="center"/></p>
<h1 align="center">Andrid Battery View</h1> 

<p align="center"> <a href="https://www.android.com"><img src="https://img.shields.io/badge/platform-Android-yellow.svg" alt="platform"></a>
 <a href="https://android-arsenal.com/api?level=21"><img src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat" alt="API"></a> <a href="https://jitpack.io/#ahmmedrejowan/RotaryKnob/"><img src="https://jitpack.io/v/ahmmedrejowan/RotaryKnob.svg" alt="JitPack"></a> <a href="https://github.com/ahmmedrejowan/RotaryKnob/blob/master/LICENSE"><img src="https://img.shields.io/github/license/ahmmedrejowan/RotaryKnob" alt="GitHub license"></a> </p>

<h3 align="center">A lightweight Battery Status View<b></b></h3>

 <p align="center"> <a href="https://github.com/ahmmedrejowan/RotaryKnob/issues"><img src="https://img.shields.io/github/issues/ahmmedrejowan/RotaryKnob" alt="GitHub issues"></a> <a href="https://github.com/ahmmedrejowan/RotaryKnob/network"><img src="https://img.shields.io/github/forks/ahmmedrejowan/RotaryKnob" alt="GitHub forks"></a> <a href="https://github.com/ahmmedrejowan/RotaryKnob/stargazers"><img src="https://img.shields.io/github/stars/ahmmedrejowan/RotaryKnob" alt="GitHub stars"></a> <a href="https://github.com/ahmmedrejowan/RotaryKnob/graphs/contributors"> <img src="https://img.shields.io/github/contributors/ahmmedrejowan/RotaryKnob" alt="GitHub contributors"></a>   </p>

## Table of Contents

<details>
<summary>Click to Expand</summary>

- [Purpose](#purpose)
- [Features](#features)
- [Demo](#demo)
- [Prerequisites](#prerequisites)
- [Dependency](#dependency)
- [Usage](#usage)
- [BroadcastReceiver](#broadcastReceiver)
- [Customization](#customization)
- [Attributes](#attribute)
- [Notes](#notes)
- [Contribute](#contribute)
- [License](#license)

</details>

## Purpose
The Advanced Battery View (ABV) library was born out of frustration with the time-consuming task of creating a battery view component for Android apps. Developed to avoid repetitive tasks and streamline development, ABV offers a simple solution for integrating a customizable battery view. It aims to save developers time and effort, enhancing user experience by providing a visually appealing representation of battery status without the hassle of reinventing the wheel.

## Features
- Lightweight
- Highly customizable
- Supports both Kotlin and Java
- Can be attached to BroadcastReceiver Intent

## Demo

| Normal  | Charging  | Warning                                                                                             | Critical |
|-------|--------------|-----------------------------------------------------------------------------------------------------|------|
|  ![Shot1](https://raw.githubusercontent.com/ahmmedrejowan/RotaryKnob/master/files/shot1.png)  |  ![Shot2](https://raw.githubusercontent.com/ahmmedrejowan/RotaryKnob/master/files/shot2.png) | ![Shot3](https://raw.githubusercontent.com/ahmmedrejowan/RotaryKnob/master/files/shot3.png) |  ![Shot4](https://raw.githubusercontent.com/ahmmedrejowan/RotaryKnob/master/files/shot4.png) 

## Prerequisites

### Kotlin DSL

<details open>
<summary>Kotlin DSL</summary>

``` Kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://jitpack.io")
        }
    }
}
```
</details>

<details>
<summary>Groovy DSL</summary>

``` groovy
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
</details>


## Dependency
Add this to your module's `build.gradle.kts` file (latest version <a href="https://jitpack.io/#ahmmedrejowan/RotaryKnob"><img src="https://jitpack.io/v/ahmmedrejowan/RotaryKnob.svg" alt="JitPack"></a>):

<details open>
<summary>Kotlin DSL</summary>

``` kotlin
dependencies {
    ...
    implementation("com.github.ahmmedrejowan:RotaryKnob:0.1")
}
```
</details>

<details>
<summary>Groovy DSL</summary>

``` groovy
dependencies {
    ...
    implementation 'com.github.ahmmedrejowan:RotaryKnob:0.1'
}
```
</details>

## Usage

### XML

``` XML 
<com.rejowan.abv.ABV
    android:id="@+id/abv"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:abvBatteryOrientation="portrait"
    app:abvRadius="10"
    app:abvSize="50"/>

```

### Kotlin

``` Kotlin
val abv = binding.abv
abv.size = 50
abv.mRadius = 10f
abv.chargeLevel = 50
abv.batteyOrientation = BatteryOrientation.PORTRAIT
abv.isCharging = false
```

## BroadcastReceiver
This is the most unique feature of the library. You can attach your broadcast receiver intent to this view and it'll automatically show the status of the device battery.

- Create a broadcast receiver variable.
- Register the receiver to the activity/fragment
- Unregister when destroyed
- Attach the intent from the receiver to the view

**Benefits**
- **You don't need to pass the charging level, status individually**
- **Automatically it'll get the charging level, stauts from the intent**
- **Minimal code with no issues**

Here is an example

``` Kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    private val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!=null){
                binding.abv.attachBatteryIntent(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }
}
```

## Customization

### XML

<details open>
<summary>XML Customization</summary>

``` XML 
<com.rejowan.abv.ABV
    android:id="@+id/abv"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    app:abvNormalBgColor="#86B6F6"
    app:abvNormalLevelColor="#4E94F1"
    app:abvWarningBgColor="#FFCF96"
    app:abvWarningLevelColor="#F5AD56"
    app:abvCriticalBgColor="#EF5350"
    app:abvCriticalLevelColor="#B71C1C"
    app:abvChargingBgColor="#89EC9E"
    app:abvChargingLevelColor="#4DD86C"
    app:abvChargeLevel="5"
    app:abvWarningChargeLevel="30"
    app:abvCriticalChargeLevel="10"
    app:abvIsCharging="false"
    app:abvRadius="10"
    app:abvBatteryOrientation="portrait"
    app:abvSize="50"
    app:abvChargingIcon="@drawable/ic_charge_abv"
    app:abvWarningIcon="@drawable/ic_warning_abv" />
```
</details>

### Kotlin

<details>
<summary>Kotlin Customization (click to Expand)</summary>

``` Kotlin
val abv = binding.abv
abv.normalBackgroundColor = ContextCompat.getColor(this, R.color.yourColor)
abv.normalLevelColor = ContextCompat.getColor(this, R.color.yourColor)
abv.warningBackgroundColor = ContextCompat.getColor(this, R.color.yourColor)
abv.warningLevelColor = ContextCompat.getColor(this, R.color.yourColor)
abv.criticalBackgroundColor = ContextCompat.getColor(this, R.color.yourColor)
abv.criticalLevelColor = ContextCompat.getColor(this, R.color.yourColor)
abv.chargingBackgroundColor = ContextCompat.getColor(this, R.color.yourColor)
abv.chargingLevelColor = ContextCompat.getColor(this, R.color.yourColor)
abv.chargingIcon = R.drawable.ic_charge_abv
abv.warningIcon = R.drawable.ic_warning_abv
abv.size = 50
abv.mRadius = 10f
abv.chargeLevel = 50
abv.warningChargeLevel = 30
abv.criticalChargeLevel = 10
abv.batteyOrientation = BatteryOrientation.PORTRAIT
abv.isCharging = false
```
</details>

### Java

<details>
<summary>Java Customization (click to Expand)</summary>

``` Java
ABV abv = binding.avb;
abv.setNormalBackgroundColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setNormalLevelColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setWarningBackgroundColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setWarningLevelColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setCriticalBackgroundColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setCriticalLevelColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setChargingBackgroundColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setChargingLevelColor(ContextCompat.getColor(this, R.color.yourColor));
abv.setChargingIcon(R.drawable.ic_charge_abv);
abv.setWarningIcon(R.drawable.ic_warning_abv);
abv.setSize(50);
abv.setRadius(10f);
abv.setChargeLevel(50);
abv.setWarningChargeLevel(30);
abv.setCriticalChargeLevel(10);
abv.setBatteryOrientation(BatteryOrientation.PORTRAIT);
abv.setCharging(false);

```
</details>


## Attribute

Full list of attributes available

| Attribute                | Format       | Description                               |
|--------------------------|--------------|-------------------------------------------|
| `abvNormalBgColor`       | color        | Background color for normal state         |
| `abvNormalLevelColor`    | color        | Level color for normal state              |
| `abvWarningBgColor`      | color        | Background color for warning state        |
| `abvWarningLevelColor`   | color        | Level color for warning state             |
| `abvCriticalBgColor`     | color        | Background color for critical state       |
| `abvCriticalLevelColor`  | color        | Level color for critical state            |
| `abvChargingBgColor`     | color        | Background color for charging state       |
| `abvChargingLevelColor`  | color        | Level color for charging state            |
| `abvChargeLevel`         | integer      | Level of charge                           |
| `abvWarningChargeLevel`  | integer      | Warning level of charge                   |
| `abvCriticalChargeLevel` | integer      | Critical level of charge                  |
| `abvIsCharging`          | boolean      | Indicates whether the device is charging  |
| `abvRadius`              | integer      | Radius of the battery view                |
| `abvBatteryOrientation`  | enum         | Orientation of the battery view           |
| `abvSize`                | integer      | Size of the battery view                  |
| `abvChargingIcon`        | reference    | Icon displayed when charging              |
| `abvWarningIcon`         | reference    | Icon displayed in warning state           |

## Notes
- The library is in its early stages, so there may be some bugs.
- If you find any bugs, please report them in the `Issues` tab.
- For Documentation, please visit the [Documentation](https://ahmmedrejowan.github.io/RotaryKnob/documentation/html/index.html)
- Sample app is available in the [app](https://github.com/ahmmedrejowan/RotaryKnob/tree/master/app) directory.

## Contribute
Please fork this repository and contribute back using [pull requests](https://github.com/ahmmedrejowan/RotaryKnob/pulls).

Any contributions, large or small, major features, bug fixes, are welcomed and appreciated.

Let me know which features you want in the future in `Request Feature` tab.

If this project helps you a little bit, then give a to Star ⭐ the Repo.

## License
* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2024 ahmmedrejowan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
