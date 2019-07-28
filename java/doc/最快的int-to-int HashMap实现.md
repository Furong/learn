### 最快的int-to-int HashMap实现

#### 1.什么是哈希

散列查找也称为哈希查找。它既是一种查找方法，又是一种存储方法，称为散列存储。散列存储的内存存放形式也称为散列表。散列技术是一种尽可能不通过比较操作而直接得到记录的存储位置的检索方法，是一种计算式查找法。通过构造散列函数来得到待查关键字的地址，比如，要找关键字为k的元素，则只需要求出函数值H(k)，H(k)为给定的散列函数，函数值代表关键字k在存储区中的地址，而存储区为一块连续的内存单元，可用一个一维数组（或链表）来表示。

#### 2. JDK HashMap实现



JDK HashMap的不足：在某些场景下，读写慢；占用空间大（TODO 调查一下Java对象在内存中的布局，表示一个对象会占用哪些空间）。

#### 3.构造一个int-to-int HashMap

Int-to-int HashMap是指key为int类型，value也为int类型的HashMap。它采用线性探测来解决Hash冲突。

线性探测的计算方式：

```java
initial = Math.abs( key % array.length );
nextIdx = ( prevIdx + 1 ) % array.length;
```

线性探测的优化：

线性探测冲突后，会导致查找链非常长，因此需要对线性探测进行优化。

```java
private static final int INT_PHI = 0x9E3779B9;
 
public static int phiMix( final int x ) {
    final int h = x * INT_PHI;
    return h ^ (h >> 16);
}
```

表示哪些位置是使用过的，哪些位置是没有使用过的

最简单的做法是创建一个标记数组m_used来表示表示哪些位置是使用过的，哪些位置是没有使用过的。

原始版本：

```java
    /** Keys */
    private int[] m_keys;
    /** Values */
    private int[] m_values;
    /** Occupied? */
    private boolean[] m_used;
    

```







版本2：

用位运算代替取模运算：

自己写代码举例子

版本3:



用更少的存储空间



实验benchmark





为方便对数据的查找，用数组作为存放 值 的容器，
通过哈希函数（散列函数）将 键（key）转换为索引，
映射到数组对应的地址，
通过对 索引 的搜索，实现对 值 的查找



这篇文章将一步一步地介绍最新的hash map实现中一些技巧。在这篇文章的最后，你将获得最快的int-to-int hash map的Java实现。

open indexing开放索引

大多数现代哈希映射都基于开放索引的思想。这是什么意思？你的map基于键的数组（值将始终放在匹配的数组索引处，因此暂时忘记它们）。必须在每个map操作的键的数组中找到你的键。它是如何实现的？

首先，您需要在数组中的初始查找位置。它可以通过将键映射到该范围内的整数的任何函数来计算`[0, array.length - 1]`。键值通常通过方法映射到整数`hashCode`。这里最简单的功能可能是`Math.abs(key.hashCode() % array.length)`（请记住，`%`结果可能是负面的）。

如您所知，将大量键集映射为一小组整数值意味着您可能最终会遇到一些冲突（它们称为哈希冲突） - 不同键的初始函数的结果相同。通过尝试将另一个函数应用于原始数组索引来解决冲突。最简单的这类功能是`(prevIdx + 1) % array.length`。这些函数有一个要求 - 如果在循环中应用，它们应覆盖整个集合或阵列单元，以便您可以使用整个阵列容量。如果数组长度是另一个素数，则此类函数的另一个示例是将索引递增一个素数。



#### 参考链接

<http://java-performance.info/implementing-world-fastest-java-int-to-int-hash-map/>

