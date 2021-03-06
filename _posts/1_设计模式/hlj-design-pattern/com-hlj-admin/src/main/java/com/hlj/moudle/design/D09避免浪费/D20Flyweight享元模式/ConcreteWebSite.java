package com.hlj.moudle.design.D09避免浪费.D20Flyweight享元模式;

/**
 * @author HealerJean
 * @ClassName ConcreteWebSite
 * @date 2019/8/21  10:24.
 * @Description
 */
public class ConcreteWebSite extends AbstractWebSite {

    private String name ;

    public ConcreteWebSite(String name) {
        this.name = name;
    }

    @Override
    public void operate(String message) {
        System.out.println("网站信息：" + message);
    }
}
