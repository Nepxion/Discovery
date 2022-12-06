package com.nepxion.discovery.common.logback;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import org.apache.commons.lang3.StringUtils;

// Refer to color definition with https://logback.qos.ch/manual/layouts.html#coloring
// Windows终端默认不能显示ANSI颜色，需要在注册表HKEY_CURRENT_USER\Console中新建一个DWORD类型的值VirtualTerminalLevel，数值为1
public class MdcColorConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String ansiColor = event.getMDCPropertyMap().get(LogbackConstant.ANSI_COLOR);

        return StringUtils.isNotEmpty(ansiColor) ? ansiColor : LogbackConstant.DEFAULT_FG;
    }
}