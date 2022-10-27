package com.nepxion.discovery.common;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.List;

import com.nepxion.discovery.common.util.PluginInfoUtil;

public class PluginInfoTest {
    public static void main(String[] args) {
        String result = "[ID=discovery-guide-gateway][UID=20221026-220729-881-6282-394-753][T=gateway][P=Nacos][H=192.168.31.237:5001][V=1.0][R=default][E=default][Z=default][G=discovery-guide-group][A=false][TID=775868f091ec26d][SID=696bb7770c01037e]";
        System.out.println(PluginInfoUtil.assembleSingle(result, (List<String>) null));
        System.out.println(PluginInfoUtil.assembleSingle(result, (String) null));
        System.out.println(PluginInfoUtil.assembleSingle(result, Arrays.asList("ID", "V", "ABC")));
        System.out.println(PluginInfoUtil.assembleSingle(result, "ID,V,ABC"));

        System.out.println(PluginInfoUtil.substringSingle(result, "ID"));
        System.out.println(PluginInfoUtil.substringSingle(result, "V"));
        System.out.println(PluginInfoUtil.substringSingle(result, "ABC"));

        String resultAll = "[ID=discovery-guide-gateway][UID=20221026-220729-881-6282-394-753][T=gateway][P=Nacos][H=192.168.31.237:5001][V=1.0][R=default][E=default][Z=default][G=discovery-guide-group][A=false][TID=775868f091ec26d][SID=696bb7770c01037e] -> [ID=discovery-guide-service-a][UID=20221026-220728-411-0772-370-576][T=service][P=Nacos][H=192.168.31.237:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][A=true][TID=775868f091ec26d][SID=96fcc77d7d5e7767] -> [ID=discovery-guide-service-b][UID=20221026-220727-655-5472-259-437][T=service][P=Nacos][H=192.168.31.237:4001][V=1.0][R=qa][E=env1][Z=zone1][G=discovery-guide-group][A=false][TID=775868f091ec26d][SID=f80c1d9e20b6bdb9]";
        System.out.println(PluginInfoUtil.assembleAll(resultAll, (List<String>) null));
        System.out.println(PluginInfoUtil.assembleAll(resultAll, (String) null));
        System.out.println(PluginInfoUtil.assembleAll(resultAll, Arrays.asList("ID", "V", "ABC")));
        System.out.println(PluginInfoUtil.assembleAll(resultAll, "ID,V,ABC"));

        System.out.println(PluginInfoUtil.extractAll(resultAll, (List<String>) null));
        System.out.println(PluginInfoUtil.extractAll(resultAll, (String) null));
        System.out.println(PluginInfoUtil.extractAll(resultAll, Arrays.asList("ID", "V", "ABC")));
        System.out.println(PluginInfoUtil.extractAll(resultAll, "ID,V,ABC"));
    }
}