package com.zhu2chu.all.test;

import javax.sql.DataSource;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.zhu2chu.all.bus.h2.H2Dialect;
import com.zhu2chu.all.bus.h2.H2ServerPlugin;
import com.zhu2chu.all.bus.h2.H2TypeMapping;

public class ThreeXGenerator {

    public static void main(String[] args) {
        // BaseModel的包名
        String baseModelPack = "com.zhu2chu.all.bus.model.base";
        // BaseModel文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/zhu2chu/all/bus/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.zhu2chu.all.bus.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPack, baseModelOutputDir, modelPackageName,
                modelOutputDir);
        // 设置数据库方言
        generator.setDialect(new H2Dialect());
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(false);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(true);
        //指定类型映射
        generator.setTypeMapping(new H2TypeMapping());
        // 生成
        generator.generate();
        LogKit.info("魔导生成完成。");
    }

    public static DataSource getDataSource() {
        Prop p = PropKit.use("global.secret");
        String dbtype = p.get("dbtype");
        new H2ServerPlugin().start();
        DruidPlugin druidPlugin = new DruidPlugin(p.get(dbtype+".jdbcurl"), p.get(dbtype+".username"),
                p.get(dbtype+".password"));
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

}
