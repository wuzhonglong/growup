String[] arrayA = new String[] { "A", "B", "C", "D", "E", "F" };  
String[] arrayB = new String[] { "B", "D", "F", "G", "H", "K" };
List<String> A = Arrays.asList(arrayA);
List<String> B = Arrays.asList(arrayB);
# 并集
collectionUtils.union(A,B)
[A, B, C, D, E, F, G, H, K]
# 交集
collectionUtils.intersection(A,B)
[B, D, F]
# 交集的补集
collectionUtils.disjunction(A,B)
[A, C, E, G, H, K]
# 差集（前一个去掉后一个中所有相同项剩下的）
collectionUtils.subtract(A,B)
[A, C, E]

# 在一个集合中查找某个元素的个数
CollectionUtils.cardinality(Object obj,Collection coll)

# A中所有的与B中进行匹配  成功的放入一个新集合 并最后返回这个集合
CollectionUtils.retainAll(A,B)

# 移除
CollectionUtils.removeAll(A,B)

# 判断集合为空（empty 和 Null 都是属于空）collectionUtils.isEmpty(list)  
List<User> list1 = new ArrayList();     //true
List<User> list2 = null;                //true
List<User> list3 = new ArrayList();     //false
list3.add(new User());


# 判断集合是否相等  collectionUtils.isEqualCollection(A,B)
    不演示了  注意一点就行
        如果是基本数据类型   那么比较集合里面的值
        如果是引用数据类型   那么比较集合里面对象  对象不等 就是false

