package com.software.tongji.easygo.basic;

public interface BaseView<P extends BasePresenter>{
    void setPresenter(P presenter);

    boolean isActive();
}
