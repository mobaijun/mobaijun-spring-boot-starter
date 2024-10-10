# 通用自定义翻译组件

1. ### 快速开始

> pom 引入依赖

~~~xml

<dependency>
    <!-- https://central.sonatype.com//artifact/com.mobaijun/translation-spring-boot-starter -->
    <groupId>com.mobaijun</groupId>
    <artifactId>translation-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
~~~

2. ### 实现 `com.mobaijun.translation.service.TranslationInterface` 接口

~~~java
public interface TranslationInterface<T> {

    /**
     * 翻译
     *
     * @param key   需要被翻译的键(不为空)
     * @param other 其他参数
     * @return 返回键对应的值
     */
    T translation(Object key, String other);
}
~~~

3. 实现翻译业务

~~~java
@Component
@RequiredArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NAME)
public class CreateByTranslationImpl implements TranslationInterface<String> {

    // 组件接口
    private final SysUserService remoteUserService;

    @Override
    public String translation(Object key, String other) {
        if (key == null) {
            return "";
        }
        return remoteUserService.selectUserIdByUserName((Long) key);
    }
}
~~~

4. 实体对象增加翻译标识符

~~~java
@Getter
@Setter
@ToString
@Schema(title = "车辆排队管理表视图对象")
public class VehicleQueueVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    private Long id;

    @Schema(title = "更新人名称")
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "updateBy")
    private String updateByName;

    @Schema(title = "创建人名称")
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "createBy")
    private String createByName;
}
~~~

> TransConstant 为自定义的标识常量