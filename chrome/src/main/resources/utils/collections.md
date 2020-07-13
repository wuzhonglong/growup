# 有许多基本方法  但是很常用


# 排序 基本类型 如果是基本数据类型 就自然排序  如果是引用类型  必须自定义排序规则
Collections.sort(list);
Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if(user1.getAge() > user2.getAge()){
                    return -1;
                }else if(user1.getAge() < user2.getAge()){
                    return 1;
                }else {
                    return 0;
                }
            }
        });

# 打乱  做随机游戏的时候可能会用
Collections.Shuffling(list)

# 反转    比如上面的排序是降序  通过反转  直接就可以变成升序
Collections.reverse(list)

# 复制    将源列表复制到目标列表  并覆盖目标列表相同长度的元素  
Collections.copy(target,resource)

# 返回列表中最小的元素  如果是引用类型 需实现比较规则 当然有最小就会有最大
Collections.min(list, new Comparator<User>() {      
            @Override
            public int compare(User o1, User o2) {
                //....
                return 0;
            }
        });

# 交换两个索引位置的元素
Collections.swap(arrayList, 2, 5);

#   boolean replaceAll(List list, Object oldVal, Object newVal), 用新元素替换旧元素
#   int binarySearch(List list, Object key)//对List进行二分查找，返回索引，注意List必须是有序的
#   int frequency(Collection c, Object o)//统计元素出现次数

# Arrays.copyOf()
int[] h = { 1, 2, 3, 3, 3, 3, 6, 6, 6, };
int i[] = Arrays.copyOf(h, 6);
123333
Arrays.copyOfRange(h, 6, 11);
输出结果66600(h数组只有9个元素这里是从索引6到索引11复制所以不足的就为0)