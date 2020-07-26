package com.mypro.controller.seckill;

import com.alibaba.fastjson.JSON;
import com.mypro.common.core.controller.BaseController;
import com.mypro.common.utils.SecurityUtils;
import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.testItemParam;
import com.mypro.system.service.ISecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 * @author houhaotong
 */
@Controller
@RequestMapping("/seckill/item")
public class SecItemController extends BaseController {

    @Autowired
    ISecService secService;

    @GetMapping()
    public String itemIndex(){
        return "/seckill/item";
    }

    @GetMapping("/all")
    @ResponseBody
    public String allItem(){
        List<SecItem> items = secService.selectAll();
        String s = JSON.toJSONString(items);
        return s;
    }

    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable Long itemId, ModelMap map){
        SecItem item=secService.selectItemByItemId(itemId);
        map.addAttribute("item",item);
        return "/seckill/detail";
    }

    @PostMapping("/{itemId}")
    @ResponseBody
    public String itemBuy(@PathVariable Long itemId){
        String loginName = SecurityUtils.getCurrentUserName(getRequest());
        if (secService.kill(itemId,loginName)){
            return "秒杀成功！";
        }
        return "秒杀失败";
    }

    /**
     * 测试下单功能
     */
    @PostMapping(value = "/testBuy",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String testBuy(@RequestBody testItemParam param){
        if (secService.testKillV2(param.getItemId(),param.getUserId())){
            return "秒杀成功！";
        }
        return "秒杀失败";
    }
}
