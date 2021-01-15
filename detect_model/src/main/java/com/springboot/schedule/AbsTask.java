package com.springboot.schedule;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/14 14:43
 */
public abstract class AbsTask implements Task{

    private boolean runable = true;

    @Override
    public boolean runable() {
        return runable;
    }

    @Override
    public void stop(){
        runable = false;
    }

    @Override
    public void start(){
        runable = true;
    }
}
