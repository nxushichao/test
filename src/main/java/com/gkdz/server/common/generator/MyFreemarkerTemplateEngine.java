package com.gkdz.server.common.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;

/**
 * 自定义模板引擎处理，用于生成 QO、VO 等
 */
public class MyFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

  @Override
  protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
    String entityName = tableInfo.getEntityName();
    String otherPath = this.getPathInfo(OutputFile.other);
    customFile.forEach((key, value) -> {
      // 根据自定义路径替换 other 路径
      String[] keys = key.split(":");
      if (keys.length > 1) {
        key = keys[1];
      }
      String fileName = String.format((keys.length > 1 ? otherPath.replace("\\other", "\\" + keys[0]) : otherPath) + File.separator + entityName + "%s.java", key);
      objectMap.put("fileNameSuffix", key);

      // 处理路由名，结尾加 s
      String[] controllerMappingHyphens = objectMap.get("controllerMappingHyphen").toString().split("-");
      if (controllerMappingHyphens.length > 1) {
        StringBuilder controllerMappingHyphenStr = new StringBuilder(controllerMappingHyphens[0]);
        for (int i = 1; i < controllerMappingHyphens.length; i++) {
          controllerMappingHyphenStr.append(StringUtils.capitalize(controllerMappingHyphens[i]));
        }
        objectMap.put("controllerMappingHyphen", controllerMappingHyphenStr.append("s").toString());
      }

      this.outputFile(new File(fileName), objectMap, value + ".ftl");
    });
  }
}

