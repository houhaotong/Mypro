package com.mypro.controller.seckill;

import com.alibaba.fastjson.JSON;
import com.mypro.common.core.controller.BaseController;
import com.mypro.common.utils.SecurityUtils;
import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.SecOrderAndUserInfo;
import com.mypro.system.domain.SysUser;
import com.mypro.system.mapper.ISecOrderMapper;
import com.mypro.system.service.ISecService;
import com.mypro.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀订单 控制器
 * @author houhaotong
 */
@Controller
@RequestMapping("/seckill/order")
public class SecOrderController extends BaseController {

    @Autowired
    ISecService secService;

    @Autowired
    ISysUserService userService;

    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable String orderId, ModelMap map){
        SecOrderAndUserInfo order = secService.selectOrderByOrderId(orderId);
        if (order!=null){
            //判断当前登录用户是否和订单用户一致
            SysUser user = userService.selectUserByUserId(order.getUserId());
            if (SecurityUtils.getCurrentUserName(getRequest()).equals(user.getLoginName())) {
                SecItem item = secService.selectItemByItemId(order.getItemId());
                map.addAttribute("order", order);
                map.addAttribute("item", item);
                return "/seckill/order/detail";
            }
        }
        return "/seckill/error";
    }

    @GetMapping
    public String orderList(){
        return "/seckill/order/order";
    }

    @GetMapping("/all")
    @ResponseBody
    public String getAllOrder(){
        Long userId = userService.selectUserIdByLoginName(SecurityUtils.getCurrentUserName(getRequest()));
        List<SecOrderAndUserInfo> orders= secService.selectOrdersByUserId(userId);
        String s = JSON.toJSONString(orders);
        return s;
    }

    @PostMapping("/pay")
    @ResponseBody
    public String orderPay(@RequestParam("orderId") String orderId){
        if (orderId!=null) {
            if (secService.orderPay(orderId)) {
                return "订单支付成功";
            }
        }
        return "订单支付失败";
    }
}
