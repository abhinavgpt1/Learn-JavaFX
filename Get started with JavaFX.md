Get Started with JavaFX
-----------------------
(optional) 1. install latest java version.

(optional) 2. install latest scene builder.

NOTE: Javafx came with Java 8, but now you need to follow https://openjfx.io/openjfx-docs/#install-java

I'll follow non-modular manual way for now in IntelliJ
1. Install JavaFX SDK 24 and Java 24.
2. Create JavaFX Project in IntelliJ.
3. Set SDK to Java 24. Also, keep a check of language level >= 18.
4. Under File > Project Structure > Libraries, add the JavaFX SDK.
5. (IMP) While running the Application, provide VM Options.
```
--module-path "C:\Users\username\Downloads\javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml
```

NOTE: VM Options (passed to JVM) != Program args (passed to application)

Some warnings may come due to Java and JavaFX latest versions, you supress them by adding following:
* --enable-native-access=ALL-UNNAMED
	* https://stackoverflow.com/questions/79725728/how-to-suppress-restricted-method-called-java-lang-systemload-warning-when
* --sun-misc-unsafe-memory-access=allow
	* https://stackoverflow.com/questions/79525654/errors-after-updating-java-23-and-javafx-22-to-java-24-and-javafx-24
* --enable-native-access=javafx.graphics
	* No Reference

#### Final VM Options:
----------------------
```
--module-path "C:\Users\username\Downloads\javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=ALL-UNNAMED --sun-misc-unsafe-memory-access=allow --enable-native-access=javafx.graphics
```

Tip: 
* comment file module-info.java for non-modular project.
  * https://openjfx.io/openjfx-docs/#next-steps:~:text=For%20a%20non%2Dmodular%20project%2C%20you%20can%20remove%20the%20module%2Dinfo.java%20file.
* You can set your installed version of scene builder to view FXML in IntelliJ.
  * https://www.jetbrains.com/help/idea/opening-fxml-files-in-javafx-scene-builder.html#open_files_in_scene_builder_app
