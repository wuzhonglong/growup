# 首先导入依赖（注意经测试boot2.1.0以上集成swagger2会启动报错）
        <!--经测试 springboot整合swagger2 boot版本超过2.1.0会报错 启动失败-->
        <!--swagger-u和swagger2最好版本保持一致-->
        <!-- swagger-ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger.version}</version>
        </dependency>
        <!-- swagger2 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger.version}</version>
        </dependency>
# 然后配置swagger 配置类上注解EnableSwagger2 尽量不要加在启动类上 
# 不同的接口可以进行分组
# Environment 这个可以控制环境
# apis 可以控制扫描方式
# paths 可以过滤接口
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket fyDateApi(Environment environment){
        Profiles profile = Profiles.of("test");
        boolean b = environment.acceptsProfiles(profile);   //判断当前开发环境是否为 dev
        return new Docket(DocumentationType.SWAGGER_2)              //swagger版本
                .groupName("非遗数据接口")                               //分组名
                .apiInfo(fyDateApiInfo())                           //api描述
                .enable(b)                                          //是否开启swagger
                .select()                                           //开启扫描  接下来直到 biuld都是扫描规范
//                .apis(RequestHandlerSelectors.basePackage("com.wisesoft.cdfyshow.controller")) //绝对路径包扫描
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))     //扫描所有方法加了ApiOperation接口描述的接口
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))               //扫描所有类上加了接口类描述的类
                .paths(PathSelectors.any())   //过滤路径 --- 表示所有
//                .paths(PathSelectors.ant("/data/**"))   //过滤路径 --- 指定条件（）
                .build();
    }

    @Bean
    public Docket userApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户接口")
                .apiInfo(userApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/user/delete/**"))
                .build();
    }

    public ApiInfo fyDateApiInfo(){
        return new ApiInfoBuilder()
                .title("fydata")
                .description("非遗数据查询接口")
                .version("1.0")
                .contact(new Contact("wzl","http://www.baidu.com","1742182887@qq.com")) //设置作者
                .build();
    }
    public ApiInfo userApiInfo(){
        return new ApiInfoBuilder()
                .title("user")
                .description("用户接口")
                .version("1.0")
                .build();
    }
}
# 然后就是注解
# 最后 重点  swagger 不能作为校验  他只是一个给别人看的接口文档  参数校验还是必须用spring的体系
//@Api(value = "DataController", description = "非遗数据查询接口")
@RestController
@RequestMapping("/data")
@Validated          // 参数校验
public class DataController {

    @Autowired
    private DataService dataService;


    /**
     * @description: 左侧饼图 所有店铺的类型占比  总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiIgnore // 加了这个注解 不会生成swagger文档
    @GetMapping(value = "/shopTypeScale")
    public CommonResult<Map<String, Object>> shopTypeScale(@RequestParam(required = false) String cityName) {
        Map<String, Object> map = dataService.shopTypeScale(cityName);
        return CommonResult.success(map);
    }

    /**
     * @description: 左侧矩形方块 非遗等级、网销开设方式、贫困级别占比     总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiOperation(value = "查询非遗等级、店铺类型、贫困等级", notes = "总览、市州界面通用接口")
    @GetMapping(value = "/leftRectScale")
    public CommonResult<Map<String, Object>> leftRectScale(
            @ApiParam(name = "cityName", value = "城市名")
            @RequestParam(required = false) String cityName) {
        return CommonResult.success(dataService.leftRectScale(cityName));
    }

    /**
     * @description: 左侧线上占比柱状图     总览页面不传参   市州页面传参  比如  cityName = "宜宾市"
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @ApiOperation(value = "查询个平台商铺占比", notes = "总览、市州界面通用接口")
    @GetMapping(value = "/leftBarScale")
    public CommonResult<List<Map<String, Object>>> leftBarScale(
            @ApiParam(name = "cityName", value = "城市名")
            @RequestParam(required = false) String cityName) {
        List<Map<String, Object>> maps = dataService.leftBarScale(cityName);
        return CommonResult.success(maps);
    }

    /**
     * @description: 市州分布图
     * @author: wzl
     * @createTime: 2020/6/3
     */
    @GetMapping(value = "/rightMapDistribution")
    public CommonResult<List<Map<String, Object>>> rightMapDistribution() {
        return CommonResult.success(dataService.rightMapDistribution());
    }

    /**
     * @description: 简介 - 平台 - 传承人
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @ApiOperation(value = "店铺简介",notes = "包括传承人信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",value = "店铺ID",paramType = "path",dataType = "Integer",required = true,defaultValue = "10"),
            @ApiImplicitParam(name = "num",value = "需要返回的传承人数量",paramType = "query",dataType = "Integer",defaultValue = "2")
            // paramType = path(@PathVariable这种resul风格适用)、query(请求头中的参数,拼接再url的那种?name=zhangsan)、header(请求头参数 类是于cookie之类)
            //              、form(表单类型的参数)、body(请求体中的参数) 一般用的多的就是前两者
            // dataType = ""  默认是String
            // required = boolean   默认是false 和@RequestParam注解的默认值刚好相反  所以用的时候注意点
    })
    @GetMapping(value = "/shopDetail/{id}")
    public CommonResult<Map<String, Object>> shopDetail(@PathVariable Integer id, @RequestParam(required = false) Integer num) {
        Map<String, Object> shopInfo = dataService.shopDetail(id, num);
        return CommonResult.success(shopInfo);
    }

    /**
     * @description: 店铺信息列表 cityName = "宜宾市"  required = true    这儿需要返回店铺ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @ApiOperation(value = "店铺信息列表",notes = "店铺信息列表")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "cityName",value = "城市名",paramType = "query",required = true)})
    @GetMapping(value = "/shopInfos")
    public CommonResult<List<Map<String, Object>>> shopInfos(@RequestParam(required = false) String cityName) {
        return null;
//        return CommonResult.success(dataService.shopInfos(cityName));
    }


    /**
     * @description: 单个（所有）店铺 销量、营业额统计 以及非遗类型销量统计    单个 需要传店铺ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/saleStat")
    public CommonResult<Map<String, Object>> saleStat(
            @RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "cityName", required = false) String cityName) {
        return CommonResult.success(dataService.saleStat(shopId, cityName));
    }

    /**
     * @description: 平台销量、营业额统计      必须要传 店铺 ID
     * @author: wzl
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/platSaleStat")
    public CommonResult<List<Map<String, Object>>> platSaleStat(Integer shopId) {
        List<Map<String, Object>> dataList = dataService.platSaleStat(shopId);
        return CommonResult.success(dataList);
    }

    /**
     * @description: 地区分布
     * @author: eshen
     * @createTime: 2020/6/4
     */
    @GetMapping(value = "/addrDistribution")
    public CommonResult<Map<String, Object>> addrDistribution() {
        return CommonResult.success(dataService.addrDistribution());
    }

}

@ApiModel(value = "用户类", description = "用户vo")
@Getter
@Setter
public class User {
//    @NotNull(message = "不能为null")
    // 这里设置required不生效 不知道为啥  接口层的可以生效
    @ApiModelProperty(name = "username",value = "姓名",  required = true, example = "张三")
    private String username;

    @ApiModelProperty(name = "age", value = "年龄", dataType = "Integer", example = "18")
    private Integer age;
}

