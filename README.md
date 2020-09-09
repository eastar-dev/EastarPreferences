[![Release](https://jitpack.io/v/eastar-dev/EastarPreferences.svg)](https://jitpack.io/#eastar-dev/EastarPreferences)

## How...

### Gradle with jitpack

#### Add it in your root build.gradle at the end of repositories:
```javascript
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### Add the dependency
```javascript
dependencies {
    kapt 'com.github.eastar-dev.EastarPreferences:pref_annotation_generator:1.0.7'
    implementation 'com.github.eastar-dev.EastarPreferences:pref_annotation:1.0.7'
}
```
#### Your code
```javascript
@Pref(defaultSharedPreferences = true)
data class PPSharedPreferences(
        //@formatter:off
        val boolean1                      : Boolean,
        val string2                       : String,
        val int3                          : Int,
        val androidId                     : String,
        val appUuid                       : String
        ...
        //@formatter:on
) {
    // your init value optinal
    companion object {
        fun create(context: Context) {
            val pref = PreferenceManager.getDefaultSharedPreferences(context)
            pref.registerOnSharedPreferenceChangeListener { _, key -> Log.w(key, pref.all[key].toString().replace("\n", "_")) }
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            pref.edit(true) { putString("androidId", androidId) }
            pref.edit(true) { putString("appUuid", UUID(androidId.hashCode().toLong(), Build.MODEL.hashCode().toLong()).toString()) }
        }
    }
}

```

## How used
if

```javascript
data class xxxSharedPreferences{
        val string2 : String,
}
```

```javascript
	xxx.string2 = "string" //set
	val text = xxx.string2 //get
	xxx.getString2("str")  //get with default value
```


## License 
 ```code
Copyright 2019 eastar Jeong

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
