# C0-compiler

## 如何编译

在项目根目录下:

```shell script
mvn clean package
mv target/C0-compiler-1.0-SNAPSHOT-shaded.jar ./cc0
```

这样,会在项目根目录下得到名为`cc0`的jar包.

## 如何使用

```shell script
Usage:
  java -jar cc0 [options] input [-o file]
or 
  java -jar cc0 [-h]
Options:
  -s        将输入的 c0 源代码翻译为文本汇编文件
  -c        将输入的 c0 源代码翻译为二进制目标文件
  -h        显示关于编译器使用的帮助
  -o file   输出到指定的文件 file
```
 ## 完成了哪些内容

 

 ## UB
 
 - 如果函数没有`return`语句,那么自动返回该函数返回类型对应的`0`值.没有任何报错和警告.
 
 - 如果整数字面量超过最大值,自动取`int`最大值,没有任何报错和警告.