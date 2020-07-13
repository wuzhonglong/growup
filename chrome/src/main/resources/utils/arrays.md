# 经常用到Arrays.asList(...)    但是有很多坑必须知道
String[] arr = {"A","B","C"}
List<String> list = Arrays.asList(arr);     <=> List<String> list = Arrays.asList("A","B","C")
上面两者效果是一样的

# 注意  上面那种方式得到的并不是真正的java.util.ArrayList    
#       而是java.util.Arrays 的一个内部类,这个内部类并没有实现集合的修改方法或者说并没有重写这些方法
# 所以阿里巴巴开发手册 才会规定对这样的list 不能使用集合的修改方法（add/remove/clear）
# 还有一点  如果是 int[] arr = {1,2,3}     这样的基本类型数组 使用asList  返回的list中并不是数组的元素  而是这个数组对象本身
#   所以get(1)的时候会报下标越界错误   get(0) 会得到数组对象  解决办法 使用包装类

#######解决办法
一、List list = new ArrayList<>(Arrays.asList("A","B","C"))     这样就可以使用修改那些方法了
二、
    Integer [] myArray = { 1, 2, 3 };
    List myList = Arrays.stream(myArray).collect(Collectors.toList());
    //基本类型也可以实现转换（依赖boxed的装箱操作）
    int [] myArray2 = { 1, 2, 3 };
    List myList = Arrays.stream(myArray2).boxed().collect(Collectors.toList());