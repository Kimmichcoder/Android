# 小黑盒陪玩android项目 

 **注意！！！:smile: 
"@Hack android development\gradle\wrapper\gradle-7.2-bin.zip"不存在！（体积太大了没上传，@是存放的路径，自己到官网下载）** 

这是一个基于 Java 的陪玩应用程序，提供了一系列的功能和工具来支持用户进行陪玩活动。以下是项目的主要组件和功能。
项目帮助：


- Compile Sdk Version:Android 12L（API 32）
- 
- Build Tools Version:用于编译和构建项目的 Build Tools 版本;
- 该版本是 Gradle 构建系统使用的 Build Tools 版本。
- 
- Source Compatibility and Target Compatibility:
- Java 版本为 1.8，项目的源代码和字节码是兼容 Java 8
- 
- Retain information about dependencies in the apk:
- 指定是否在 APK 文件中保留关于依赖项的信息。
- 




1. **Application ID:**
   - `com.example.myexamproject` 是应用程序的唯一标识符

2. **Application ID Suffix:**
   - 这个配置项允许你在应用程序 ID 的末尾添加后缀

3. **Version Code:**
   - `Version Code` 是用于在 Android 设备上唯一标识应用程序版本的数字

4. **Version Name:**
   - `Version Name` 是用户可读的版本号，通常由开发者指定

5. **Version Name Suffix:**
   - 这个配置项允许你在版本名称的末尾添加后缀

6. **Target SDK Version:**
   - `Target SDK Version` 是指定应用程序编译和目标的 Android SDK 版本

7. **Min SDK Version:**
   - `Min SDK Version` 指定应用程序可以运行的最低 Android 版本

8. **ProGuard Files:**
   - 这是指定在构建过程中用于混淆和缩小代码的 ProGuard 文件的配置

9. **Manifest Placeholders:**
   - 这个配置项允许你在 AndroidManifest.xml 文件中使用占位符

10. **Multi Dex Enabled:**
    - 这个配置项指定是否启用 MultiDex 支持

11. **Resource Configs:**
    - 这个配置项允许你指定应用程序支持的资源配置（例如语言、屏幕密度等）

12. **Test Instrumentation Runner Class Name:**
    - 这个配置项指定用于运行 Android 测试的测试运行器的类名


以上这些配置项可以在 `build.gradle` 文件中找到，通常是在应用程序模块的 `build.gradle` 文件中。在 Android Studio 中，你可以在 "Build Variants" 窗口中查看并修改这些配置。

### Utils
- 提供了一些通用的工具类，用于处理常见任务和功能

### IdGenerator
- 生成唯一的标识符，用于在应用程序中唯一标识实体

### LocationHelper
- 处理位置相关的功能，可能用于显示用户的位置信息等

### MD5Utils
- 提供 MD5 加密功能

### MyDBHelper 和 MySQLiteOpenHelper
- 数据库帮助类，用于管理应用程序的本地数据库

### StudentDbHelper
- 信息数据库帮助类

### Activity
- 包含了应用程序的各种活动（Activity），例如登录、注册、主界面等

## Res 核心

### drawable
- 存放应用程序使用的图片和图标

### mipmap
- 存放应用程序的启动图标

### raw
- 存放应用程序需要直接访问的原始资源

### values 和 values-night
- 存放资源文件，如颜色、字符串等

## 如何运行

1. 克隆项目到本地：
    ```bash
    git clone https://github.com/your-username/your-project.git
    ```

2. 打开 Android Studio，选择 "Open an existing Android Studio project"，导航到项目目录并选择 `build.gradle` 文件。

3. 等待 Gradle 同步完成，然后运行应用程序。

## 功能和用法

- 详细描述应用程序的主要功能和使用方法。

## 贡献

请阅读 [CONTRIBUTING.md](CONTRIBUTING.md) 了解如何贡献到项目。

## 许可证

该项目采用 [MIT 许可证](LICENSE)。查阅 [LICENSE.md](LICENSE.md) 文件了解更多详细信息。

## 致谢

    项目开发由Qinsanma和Kimmichcoder制作完成

    同时也是android开发作业

    感谢帮助的人和软件，同时也感谢老师的付出
