package com.zhu2chu.all.bus.directive.assets;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Const;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.ParseException;
import com.jfinal.template.stat.Scope;

/**
 * 压缩jss、css指令
 * 此指令修令jfinal-assets.jar的AssetsDirective。
 * 目的为了支持传入指定的路径<br/>
 * 最初目的：
 * 为了将压缩后的js、css分别放到目录js、css里面，这样不至于每个页面的压缩文件都放在同一目录
 * 
 * 2017年9月15日 12:48:30
 * 感觉并不实用，在文件里引用外部资源时，不以/开头会加上输出的目录，以/开头又不好控制项目名
 * jfinal模块没法很好的处理压缩后的js文件，里面很#\$%之类的字符很多正则，所以就可能报错了
 * 
 * @author ThreeX
 *
 */
public class Assets extends Directive {

    private String filename;
    private String relativePath;//相对路径filename所在路径的路径

    @Override
    public void setExprList(ExprList exprList) {
        Expr[] exprArray = exprList.getExprArray();
        if (exprArray.length == 0) {
            throw new ParseException("#assets directive parameter cant not be null", location);
        }
        if (exprArray.length > 2) {
            throw new ParseException("#assets 指令最多只允许2个参数，当前传入参数"+exprArray.length+"个", location);
        }
        if (!(exprArray[0] instanceof Const)) {
            throw new ParseException("#assets first parameter must be constant", location);
        }
        if (exprArray.length > 1) {
            if (!(exprArray[1] instanceof Const)) {
                throw new ParseException("#assets second parameter must be constant", location);
            }
            this.relativePath = ((Const)exprArray[1]).getStr();
        }

        this.filename = ((Const)exprArray[0]).getStr();
    }

    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        try {
            String path = AssetsKit.getPath(filename, relativePath);
            write(writer, path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
