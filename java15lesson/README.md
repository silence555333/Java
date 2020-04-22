## 注解
@Target 指定该注解针对的地方，有几种ElementType
ElementType.TYPE -类，接口
ElementType.FIELD ,-字段
ElementType.METHOD -方法
ElementType.PARAMETER ,-参数
ElementType.CONSTRUCTOR ,-构造方法
ElementType.LOCAL_VARIABLE,-局部变量
ElementType.ANNOTATION_TYPE ,-注解
ElementType.PACKAGE -包

@Retention 指定注解的保留域，有3种RetentionPolicy
RetentionPolicy.SOURCE -注解信息仅存于源代码级别，由编译器处理，处理完之后就不保留了
RetentionPolicy.CLASS -注解信息保留于类对应的class文件中
RetentionPolicy.RUNTIME -注解信息保留于class文件中，并且可由JVM读入，运行时使用

SOURCE 选项常用于代码自动生成

## 类加载过程
一个java 程序如何在JVM里面运行的
步骤1：类级别的工作

1.1 加载

加载阶段的工作是，把class文件字节流就按照虚拟机规范存储在方法区之中，然后在内存中创建一个java.lang.Class类对象

从硬件里面（文件系统或网络）把Test.class 找到，然后放到JVM中的方法区，存放的是二进制表示，如全名、静态代码、静态变量、方法定义、构造函数说明等等，
有的时候我们把类的定义也说成是类的元数据（Meta Data）。对每个加载的类，JVM会创建一个类对象（class object）

** 注意 ：类是代表类的，所以无论程序员创建了多少个对象实例，调用多少次，JVM中总是只有这一个类对象，到这个时候，类的定义和内存表达就出来了，但是并没有开始对象的创建**

1.2 链接

这个阶段执行类的链接过程，给类分配内存，如果内存不够，报出OutOfMemoryError错误
  他有下面的3个动作要做：
  1.2.1  验证：用于验证class文件是否合规。按照字节码的规范，class文件的格式是否正确，就是在这一步完成
  1.2.2 准备：这个阶段给类里面的静态变量分配内存，赋予默认值。
  1.2.3 解析：利用第一步的方法区，将符号引用转成直接内存引用
1.3初始化

  这个阶段完成类加载，把所有静态变量付初始值，执行静态代码块
  
   
 步骤2 ：对象级别的工作
 
 2.1 为对象在堆中分配内存，注意的是，实例字段包括自身定义的和从父类继承下来的。
 2.2 对实例内存进行零值初始化
 2.3 调用对象的构造函数
 
 ### 执行的过程
 
 1 静态代码最先执行，主程序静态代码->父类静态代码->子类静态代码
 2 执行主程序main()
 3 父类非静态代码->父类实例队形初始化->父类构造函数
 4 子类非静态代码->子类实例队形初始化->子类构造函数
 5 普通方法
 