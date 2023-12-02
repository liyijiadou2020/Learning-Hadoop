
# 参考链接
https://zhuanlan.zhihu.com/p/32859134



Scala 和 Java 的关系是密不可分的。Scala 的发明人 马丁·奥德斯基就是JVM的javac编译器的编写者。

为什么学 Scala？因为我在学 Hadoop Spark 的时候发现老师的代码都是用 Scala 写的，我看不懂>< 因此需要急速入门一下。

咳咳，对于大多数人来说学习Scala的原因也跟我是一样的。Spark的底层是使用Scala编写的，因此学习Spark的话scala是绕不开的。

Scala 可以说是基于 Java，改进了Java，引进了函数式编程。以下是Scala和Java、JVM之间的关系：

![image.png](https://raw.githubusercontent.com/liyijiadou2020/picrepo/master/202312011650771.png)

Scala 的特性：
1. 以 JVM 为基础
2. 多范式: 结合了 面向对象 和 函数式编程

- [x] 在 Windows 环境下配置 Scala 开发环境
- [ ] 在 Linux 下配置Scala开发环境

---

## 1 基础
### 1.1 var 和 val
val：value，是一个常量，其声明的数值不可变
`val answer = 8 * 5`

var：variable，是一个变量，其声明的值可以变
```scala
var age = 1
age = 2 // ok
```
鼓励使用val，因为程序中大多数的值已经确定都不需要改变。
可以选择指定变量的类型。例如
```scala
val greeting: String = "Hello"
```

### 1.2 方法的调用
调用对象上的方法和Java基本没什么区别。如果方法没有参数，可以不用`()`  来完成调用。
```scala
"Hello".intersect("World")

"acdb".sorted // 将返回字符串"abcd"
```

#### 1.2.1 apply 方法
如果 s 是一个字符串
```scala
s = "Hello"
```
那么 `s(i)` 将返回改字符串的第 i 个字符。这背后的实现原理是一个名为 apply 的方法。
```scala
def apply(n: Int) : Char
```



## 2 控制结构和函数
- if 表达式
- 块表达式
- Scala 特有的 for 循环

### 2.1 if 表达式
在 Scala 中 if/else 表达式有值，这个值就是跟在 if 或者 else 之后的表达式的值：
```scala
if (x>0) 1 else -1
```

### 2.2 块表达式
在 Scala 中 { } 块中最后一个表达式的值就是该块的值
```scala
val distance = {
	val dx = x - x0
	val dy = y - y0
	sqrt(dx * dx + dy * dy)
}
```

### 2.3 Scala 特有的 for 循环
 Scala 中 while 和 do 循环与 Java相同。但是 Scala 中没有与 for 对应的结构。如果你需要这样的循环，有两个选择：
 
 1) 使用 `while`
 2) 使用 `for (i <- 表达式)` 语法结构
让变量 i 遍历右边的表达式的所有值
```sacla
val s = "Hello"
var sum = ""

for (i <- 0 to s.length - 1)
	sum += s(i)

```

在本例中，事实上我们不需要使用下标。可以直接遍历:
```scala
var sum = ""
for (ch <- "Hello") sum += ch
```

#### 2.3.1 高级 for 循环
1. 可以以`变量<-表达式` 提供多个生成器
2. 每个生成器可以带上“守卫”
3. for 推导式：如果 for 循环以 yield 开始，则该循环会构造出一个集合，每次迭代生产集合中的一个值

### 2.4 函数
Scala 中的函数和方法有区别：方法对对象进行操作，而函数不对对象操作。

函数的定义：

`def abs (x: Double) = if (x >= 0) x else -x`

必须给出所有参数的类型。返回类型可以选择不指定，因为Scala 编译器可以通过 `=` 右侧推断出来。

函数体需要多个表达式来完成，可以用代码块。代码块的最后一个表达式的值就是函数的返回值。

注意：不需要用return！

### 2.5 过程
不返回值的函数在 Scala 中叫做”过程“（procedure）。由于过程不返回任何值，所以我们可以省去`=` 。
```scala
def box(s: String) { // 仔细看，没有=号
	val border = "-" * (s.length + 2)
	print(f"$border%n|$s|%n$border%n")
}
```
另一种表示函数没有返回值的方法是声明返回值是一个Unit：
```scala
def box(s: String): Unit = {
	...
}
```


