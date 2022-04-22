package com.karl.vendingmachine;

import com.karl.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author karl
 */
public class App {

    public static void main(String[] args) {
        
        ApplicationContext appContext
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        VendingMachineController controller
                = appContext.getBean(
                        "controller", VendingMachineController.class);
        
        controller.startApp();
    }
}
