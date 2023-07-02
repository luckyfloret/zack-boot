package cn.hmg.zackblog;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.io.IOException;
import java.sql.Types;
import java.util.Collections;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-01 12:09
 * @description:
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String sourceCodePath = System.getProperty("user.dir") + "\\zack-code-generator\\src\\main\\java";
        String mapperFilePath = System.getProperty("user.dir") + "\\zack-code-generator\\src\\main\\resources";

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/zack-boot?useUnicode=true&&characterEncoding=UTF-8",
                        "root", "123456")
                .globalConfig(builder -> {
                    builder.author("hmg") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
//                            .outputDir("D:\\tools\\idea\\ProjectManager\\zack-boot\\zack-code-generator\\src\\main\\java")
                            .outputDir(sourceCodePath); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("cn.hmg.zackblog")
                            .moduleName("module.system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperFilePath + "\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(
                                    "system_dict_data", "system_dict_type",
                                    "system_login_log", "system_mail_account",
                                    "system_mail_record", "system_mail_template",
                                    "system_menu", "system_operate_log",
                                    "system_role", "system_role_menu",
                                    "system_user_role", "system_users"
                            ).addTablePrefix("system_")
                            .entityBuilder()
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .addIgnoreColumns("creator", "create_time", "updater", "update_time", "deleted")
                            .enableLombok()  //开启lombok
                            .addSuperEntityColumns("creator", "create_time", "updater", "update_time", "deleted")
                    ;// 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
