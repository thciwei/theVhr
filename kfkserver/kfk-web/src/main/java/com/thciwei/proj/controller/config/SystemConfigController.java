package com.thciwei.proj.controller.config;

import com.thciwei.proj.bean.Hr;
import com.thciwei.proj.bean.Menu;
import com.thciwei.proj.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单数据接口")
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
 @Autowired
    MenuService menuService;
 @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
return menuService.getMenusByHrId();
 }

}
