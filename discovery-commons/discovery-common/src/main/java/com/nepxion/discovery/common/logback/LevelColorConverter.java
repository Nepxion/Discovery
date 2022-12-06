package com.nepxion.discovery.common.logback;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

// Refer to color definition with https://logback.qos.ch/manual/layouts.html#coloring
// Windows终端默认不能显示ANSI颜色，需要在注册表HKEY_CURRENT_USER\Console中新建一个DWORD类型的值VirtualTerminalLevel，数值为1
public class LevelColorConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            // ERROR等级为红色
            case Level.ERROR_INT:
                return LogbackConstant.RED_FG;
            // WARN等级为黄色
            case Level.WARN_INT:
                return LogbackConstant.YELLOW_FG;
            // INFO等级为蓝色
            case Level.INFO_INT:
                return LogbackConstant.GREEN_FG;
            // DEBUG等级为绿色
            case Level.DEBUG_INT:
                return LogbackConstant.BLUE_FG;
            // 其他为默认颜色
            default:
                return LogbackConstant.DEFAULT_FG;
        }
    }
}