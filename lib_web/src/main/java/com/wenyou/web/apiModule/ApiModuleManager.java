package com.wenyou.web.apiModule;


import com.wenyou.web.utils.WebLogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description api方法管理
 * @date: 2021/12/16 10:40
 * @author: jy
 */
public class ApiModuleManager implements IApiModuleManager {
    private Map<String, IApiModule> apiModuleMap = new HashMap<String, IApiModule>();

    public ApiModuleManager() {
        /*
         * 数据API在此静态注册
         * UI上下文相关API在ACT动态注册
         * */
    }

    @Override
    public void addModule(IApiModule apiModule) {
        if (apiModule.moduleName() != null && apiModule.moduleName().length() > 0) {
            apiModuleMap.put(apiModule.moduleName(), apiModule);
        } else {
            WebLogUtils.INSTANCE.w("ApiModuleManager", "invalid module name, skip mapping.");
        }
    }

    @Override
    public void removeModule(IApiModule apiModule) {
        apiModuleMap.remove(apiModule);
        apiModule.release();
    }

    @Override
    public void removeModuleByName(String name) {
        IApiModule apiModule = apiModuleMap.get(name);
        if (apiModule != null) {
            removeModule(apiModule);
        }
    }

    @Override
    public IApiModule getModule(String name) {
        return apiModuleMap.get(name);
    }

    public void release() {
        for (Map.Entry<String, IApiModule> entry :
                apiModuleMap.entrySet()) {
            entry.getValue().release();
        }
        apiModuleMap.clear();
    }
}
