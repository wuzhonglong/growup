# 匿名内部类 (可以自定义函数接口)     LambdaTest1
注意有参 和 无参  有返回和无返回的使用
# 常用的函数接口使用（既然是接口  那么就会想到匿名内部类 也就会想到lambda格式简化）
   # 集合 java8新增的方法  LambdaTest2
   Collection	removeIf() spliterator() stream() parallelStream() forEach()
   List	        replaceAll() sort()+以上继承来的
   Map	        getOrDefault() forEach() replaceAll() putIfAbsent() remove() replace() computeIfAbsent() computeIfPresent() compute() merge()
    foreach  比如list.foreach(s ->{
            sout(s.length);
        })  具体看代码演示
        
   # Stream API
   中间操作	concat() distinct() filter() flatMap() limit() map() peek() skip() sorted() parallel() sequential() unordered()
   结束操作	allMatch() anyMatch() collect() count() findAny() findFirst() forEach() forEachOrdered() max() min() noneMatch() reduce() toArray()
   没有结束操作的中间操作都没有意义
   比如list.stream.filter() 如果后面不加结束操作 并不会进行过滤