package com.zhu2chu.all.admin.index;

import java.io.File;

import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.zhu2chu.all.admin.bus.AdminRoutes;
import com.zhu2chu.all.bus.controller.SuperController;
import com.zhu2chu.all.bus.router.URIMapping;

/**
 * 2017年6月26日 23:11:36
 * 后端首页控制者
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/admin/index", routeClass=AdminRoutes.class, viewPath="/tests")
public class IndexController extends SuperController {


}
