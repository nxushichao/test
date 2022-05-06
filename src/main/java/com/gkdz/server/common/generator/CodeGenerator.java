package com.gkdz.server.common.generator;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * 代码生成器：https://github.com/baomidou/generator/blob/develop/mybatis-plus-generator/src/main/java/com/baomidou/mybatisplus/generator/SimpleAutoGenerator.java
 *
 * @author duanluan
 */
public class CodeGenerator {

  /**
   * 读取控制台输入内容
   */
  private static final Scanner SCANNER = new Scanner(System.in);

  /**
   * 控制台输入内容读取并打印提示信息
   *
   * @param message 提示信息
   * @return
   */
  public static String scannerNext(String message) {
    System.out.println(message);
    String nextLine = SCANNER.nextLine();
    if (StringUtils.isBlank(nextLine)) {
      // 如果输入空行继续等待
      return SCANNER.next();
    }
    return nextLine;
  }

  protected static <T> T configBuilder(IConfigBuilder<T> configBuilder) {
    return null == configBuilder ? null : configBuilder.build();
  }

  public static void main(String[] args) {
    String moduleName = "dev";
    String serverName = "";

    String projectPath;
    if (StrUtil.isBlank(serverName)) {
      projectPath = System.getProperty("user.dir");
    } else {
      projectPath = System.getProperty("user.dir") + "/" + serverName;
    }
    String name = System.getProperty("user.name");
    Dict dict = YamlUtil.loadByPath(projectPath + "/src/main/resources/application-dev.yml");
    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> spring = (LinkedHashMap) dict.get("spring");
    LinkedHashMap<String, LinkedHashMap<String, Object>> datasource = spring.get("datasource");
    LinkedHashMap<String, Object> druid = datasource.get("druid");
    String url = (String) druid.get("url");
    String username = (String) druid.get("username");
    String password = (String) druid.get("password");

    // 自定义模板，key 为 自定义的包名:Entity 后缀，value 为模板路径
    Map<String, String> customFile = new HashMap<>();
    customFile.put("dto:SaveDto", "/template/generator/entityDto.java");
    //    customFile.put("dto:UpdateDto", "/template/generator/entityDto.java");
    customFile.put("dto:PageDto", "/template/generator/PageDto.java");
    customFile.put("vo:InfoVo", "/template/generator/entityVo.java");
    //    customFile.put("vo:PageVO", "/template/generator/entityVO.java");


    // 代码生成器
    new AutoGenerator(configBuilder(new DataSourceConfig.Builder(url, username, password)))
            // 全局配置
            .global(configBuilder(new GlobalConfig.Builder()
                    // 覆盖已生成文件，默认 false
                    //                    .fileOverride()
                    // 禁用打开生成目录
                    .disableOpenDir()
                    // 输出目录，默认 windows: D://  linux or mac: /tmp
                    .outputDir(projectPath + "/src/main/java")
                    // 作者，默认无
                    .author(name)
                    // 注释时间（@since），默认 yyyy-MM-dd
                    //                    .commentDate("")
                    // 开启 swagger 模式，默认 false
                    .enableSwagger().dateType(DateType.ONLY_DATE) // 时间策略  默认值: DateType.TIME_PACK

            ))
            // 包配置
            .packageInfo(configBuilder(new PackageConfig.Builder()
                    // 模块名
                    .moduleName(moduleName)
                    // 实体包名
                    .entity("entity")
                    // 父包名
                    .parent("com.gkdz.server.modules")))
            // 自定义配置
            .injection(configBuilder(new InjectionConfig.Builder().beforeOutputFile(new BiConsumer<TableInfo, Map<String, Object>>() {
                      @Override
                      public void accept(TableInfo tableInfo, Map<String, Object> stringObjectMap) {
                        // 不启用 @TableName 注解
                        // tableInfo.setConvert(false);

                        // 自定义 Mapper XML 生成目录
                        ConfigBuilder config = (ConfigBuilder) stringObjectMap.get("config");
                        Map<OutputFile, String> pathInfoMap = config.getPathInfo();
                        pathInfoMap.put(OutputFile.xml, projectPath + "/src/main/resources/mapper/" + moduleName + "/");
                        stringObjectMap.put("config", config);
                      }
                    })
                    // 自定义文件，比如 VO
                    .customFile(customFile)))
            // 策略配置
            .strategy(configBuilder(new StrategyConfig.Builder()
                    // 表名
                    .addInclude(scannerNext("请输入表名（英文逗号分隔）：").split(",")).addTablePrefix("t_", "c_", "tb_") // 设置过滤表前缀
                    .controllerBuilder().enableRestStyle().enableHyphenStyle().entityBuilder().enableLombok().addTableFills(new Column("create_time", FieldFill.INSERT)).addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE)).addTableFills(new Column("create_by", FieldFill.INSERT)).addTableFills(new Column("update_by", FieldFill.INSERT_UPDATE)).addTableFills(new Column("create_user", FieldFill.INSERT)).addTableFills(new Column("update_user", FieldFill.INSERT_UPDATE)).enableTableFieldAnnotation().versionColumnName("version").versionPropertyName("version").logicDeleteColumnName("deleted").logicDeletePropertyName("deleted").serviceBuilder().formatServiceFileName("%sService").mapperBuilder().superClass(BaseMapper.class).enableMapperAnnotation()))

            // 模板配置
            .template(configBuilder(new TemplateConfig.Builder()
                    // 自定义模板：https://github.com/baomidou/generator/tree/develop/mybatis-plus-generator/src/main/resources/templates
                    .entity("/template/generator/entity.java").mapper("/template/generator/mapper.java").service("/template/generator/service.java").serviceImpl("/template/generator/serviceImpl.java").controller("/template/generator/controller.java")))

            // 执行并指定模板引擎
            .execute(new MyFreemarkerTemplateEngine());
    // .execute(new FreemarkerTemplateEngine());
  }
}


