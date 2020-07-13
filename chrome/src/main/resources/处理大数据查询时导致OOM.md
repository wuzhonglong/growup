# 大数据查询时 如果直接一次性从数据库查询千万级数据到jvm中 必定会OOM
	解决办法有几种 比较常用的一种 流查询
	public void maintainTagByUser(Date startTime, Date endTime) {
        List<WlztkYhUser> users = new ArrayList<>();
        String sql = "select * from wlztk_yh_user where xodbCreateTime between ? and ?";
        mysqlJdbcTemplate.query(connection -> {
            // ResultSet.TYPE_FORWARD_ONLY 设置游标 向前取数据 取一个释放一个内存
            // ResultSet.CONCUR_READ_ONLY 只读  不允许更改结果集中的值
            PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setObject(1, DateFormatUtils.format(startTime, "yyyy-MM-dd"));
            preparedStatement.setObject(2, DateFormatUtils.format(endTime, "yyyy-MM-dd"));
            // 设置避免OOM 一次取的大小
            preparedStatement.setFetchSize(Integer.MIN_VALUE);
            // 设置结果集处理方向 ResultSet.FETCH_FORWARD 表示从第一条开始到最后 ResultSet.FETCH_REVERSE表示从最后一条开始到第一行
            // 这个设置可以控制游标的方向
            preparedStatement.setFetchDirection(ResultSet.FETCH_FORWARD);
            return preparedStatement;
        }, result -> {
            while (result.next()) {
                WlztkYhUser user = new WlztkYhUser();
                user.setId(result.getString("ID"));
                user.setIdcard(result.getString("IDCARD"));
                user.setSex(result.getString("SEX"));
                user.setAge(result.getInt("AGE"));
                user.setProvinceId(result.getString("PROVINCE_ID"));
                user.setProfession(result.getString("PROFESSION"));
                users.add(user);
                if (users.size() == 1000) {
                    dataCollectorService.collector(users);
                    users.clear();
                }
            }
        });
        if (CollectionUtils.isNotEmpty(users)) {
            dataCollectorService.collector(users);
        }
    }