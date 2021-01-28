package com.nepxion.discovery.console.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.taobao.text.Color;

public class ConsoleApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
            if (Boolean.valueOf(bannerShown)) {
                System.out.println("");
                System.out.println("╔═══╗");
                System.out.println("╚╗╔╗║");
                System.out.println(" ║║║╠╦══╦══╦══╦╗╔╦══╦═╦╗ ╔╗");
                System.out.println(" ║║║╠╣══╣╔═╣╔╗║╚╝║║═╣╔╣║ ║║");
                System.out.println("╔╝╚╝║╠══║╚═╣╚╝╠╗╔╣║═╣║║╚═╝║");
                System.out.println("╚═══╩╩══╩══╩══╝╚╝╚══╩╝╚═╗╔╝");
                System.out.println("                      ╔═╝║");
                System.out.println("                      ╚══╝");
                System.out.println("Nepxion Discovery - Console  v" + DiscoveryConstant.DISCOVERY_VERSION);
                System.out.println("");
            }*/

            LogoBanner logoBanner = new LogoBanner(ConsoleApplicationContextInitializer.class, "/com/nepxion/discovery/resource/logo.txt", "Welcome to Nepxion", 9, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green, Color.cyan }, true);

            NepxionBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":", DiscoveryConstant.DISCOVERY_VERSION, 0, 1), new Description(BannerConstant.PLUGIN + ":", "Console", 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
        }
    }
}