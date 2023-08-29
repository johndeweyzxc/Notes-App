## Instructions for compiling

If you want to compile this app on your own, you need to setup keystore.properties and the rest api server.

## Setup keystore properties and rest api server

To setup the keystore, create a file keystore.properties at the root project directory and put this:

```properties
    debugPassword=YOUR_KEYSTORE_PASSWORD,
    debugKeyPassword=YOUR_KEY_PASSWORD,
    debugKeyAlias=YOUR_KEY_ALIAS
    debugStoreFile=PATH_TO_YOUR_STORE_FILE
    
    releasePassword=YOUR_KEYSTORE_PASSWORD,
    releaseKeyPassword=YOUR_KEY_PASSWORD,
    releaseKeyAlias=YOUR_KEY_ALIAS
    releaseStoreFile=PATH_TO_YOUR_STORE_FILE
```

For rest api server please visit https://github.com/johndeweyzxc/Notes-App-Api

Then in NotesApp/app/src/main/java/com/johndeweydev/notesapp/utils/Constants.kt change the ip address and port.
```kotlin
    package com.johndeweydev.notesapp.utils

    class Constants {
    
        companion object {
            // Change this based on ip address and port of the rest api server
            const val BASE_URL = "https://192.168.1.200:9001"
        }
    }
```

## Generate a keystore 

For more information on how to generate a keystore please visit https://developer.android.com/studio/publish/app-signing