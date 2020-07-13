# equals 平常使用的时候经常会报空指针异常 比如 null.equals("...")
所以要么改成  "...".equals(null) 
推荐使用 Objects.equals(null,"...") 这个源码里面避免了空指针异常  直接返回false

# 浮点类型的数据还是用得比较多  
    # 阿里巴巴 手册规范     浮点数之间的等值判断，基本数据类型不能用==来比较，
                            包装数据类型不能用 equals 来判断    具体原因是 精度缺失
    # 比如      float a = 1.0f - 0.9f;
                float b = 0.9f - 0.8f;
                System.out.println(a);// 0.100000024
                System.out.println(b);// 0.099999964
                System.out.println(a == b);// false
                
    # 解决办法 使用 BigDecimal 来定义浮点数的值，再进行浮点数的运算操作
                BigDecimal a = new BigDecimal("1.0");
                BigDecimal b = new BigDecimal("0.9");
                BigDecimal c = new BigDecimal("0.8");
                BigDecimal x = a.subtract(b);// 0.1
                BigDecimal y = b.subtract(c);// 0.1
                System.out.println(x.equals(y));// true 
    # BigDecimal 的大小比较  返回 -1 表示小于，0 表示 等于， 1表示 大于。
                BigDecimal a = new BigDecimal("1.0");
                BigDecimal b = new BigDecimal("0.9");
                System.out.println(a.compareTo(b));// 1
    # BigDecimal 保留几位小数     通过 setScale方法设置保留几位小数以及保留规则。保留规则有挺多种，不需要记，IDEA会提示。
                BigDecimal m = new BigDecimal("1.255433");
                BigDecimal n = m.setScale(3,BigDecimal.ROUND_HALF_DOWN);
                System.out.println(n);// 1.255
                
     # 注意
        在使用BigDecimal时，为了防止精度丢失，推荐使用它的 BigDecimal(String) 构造方法来创建对象
             