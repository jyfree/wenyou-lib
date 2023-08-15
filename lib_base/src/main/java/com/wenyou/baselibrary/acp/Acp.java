package com.wenyou.baselibrary.acp;

/**
 * @description
 * @date: 2021/12/16 11:28
 * @author: jy
 */
public class Acp {

    private AcpManager acpManager;

    private Acp() {
        acpManager = new AcpManager();
    }

    public static synchronized Acp getInstance() {
        return AcPermissionHolder.instance;
    }

    private static class AcPermissionHolder {
        private static final Acp instance = new Acp();
    }

    public AcpManager getAcpManager() {
        return acpManager;
    }
}
