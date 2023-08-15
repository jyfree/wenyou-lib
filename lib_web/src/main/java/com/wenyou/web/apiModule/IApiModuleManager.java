package com.wenyou.web.apiModule;

/**
 * @description api管理接口
 * @date: 2021/12/16 10:41
 * @author: jy
 */
public interface IApiModuleManager {
    public void addModule(IApiModule apiModule);

    public void removeModule(IApiModule apiModule);

    public void removeModuleByName(String name);

    public IApiModule getModule(String name);
}
