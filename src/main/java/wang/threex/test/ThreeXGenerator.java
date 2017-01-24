package wang.threex.test;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

public class ThreeXGenerator {

    public static void main(String[] args) {
        //BaseModel的包名
        String baseModelPack = "wang.threex.model.base";
        //BaseModel文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/wang/threex/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "wang.threex.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";
        
        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPack, baseModelOutputDir, modelPackageName, modelOutputDir);
        //设置数据库方言
        generator.setDialect(new MysqlDialect());
        //设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        //设置是否生成字典文件
        generator.setGenerateDataDictionary(true);
        //生成
        generator.generate();
    }
    
    public static DataSource getDataSource() {
        PropKit.use("database.properties");
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("username"), PropKit.get("password"));
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

}
