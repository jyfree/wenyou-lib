package com.wenyou.baselibrary.acp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @description
 * @date: 2021/12/16 11:28
 * @author: jy
 */
public class AcpConstant {

    //manifest注册的权限集合
    static final Set<String> manifestPermissionSet = new HashSet<>();
    //拒绝授权集合
    static List<String> deniedPermissionList = new LinkedList<>();

}
