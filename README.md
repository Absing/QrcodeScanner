# QrcodeScanner

## 使用google mobile vision 写的用于二维码、条形码扫描的软件，依赖Google play service


* mobile vison与zxing不同，mobile vison的使用**依赖Google play service**。也就是说如果你的手机上没有安装Google play service，是不能运行的。但是如果你的手机上安装了service，mobile vision应该是一个比zxing更好的选择。

* 关于兼容zxing标准，可以查看[该类](https://github.com/zxing/zxing/blob/master/android/src/com/google/zxing/client/android/Intents.java)中的 __Scan__ 类,，里面有各种参数。
---
## 感谢以下来源项目：
> 1. [googlesamples / android-vision](https://github.com/googlesamples/android-vision)
> 2. [CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
> 3. [zxing/zxing](https://github.com/zxing/zxing/tree/master/android)
> 4. [objectbox/objectbox-java](https://github.com/objectbox/objectbox-java)