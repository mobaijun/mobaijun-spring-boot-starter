package com.mobaijun.mybatis.plus.methods;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InsertIgnoreByBatch
 * 类描述：批量插入忽略
 *
 * @author MoBaiJun 2022/5/7 16:28
 */
public class InsertIgnoreByBatch extends BaseInsertBatch {

    protected InsertIgnoreByBatch() {
        super("insertIgnoreByBatch");
    }

    protected InsertIgnoreByBatch(String methodName) {
        super(methodName);
    }

    @Override
    protected String getSql() {
        return "<script>insert ignore into %s %s values %s</script>";
    }
}
