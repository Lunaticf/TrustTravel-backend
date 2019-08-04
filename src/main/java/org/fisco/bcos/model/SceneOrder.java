package org.fisco.bcos.model;

import lombok.Data;

@Data
public class SceneOrder {
    private String addr;
    private String province;    //省
    private String city;        //市
    private String name;      //景点名字
    private int price;       //门票
    private String ota;
    private int flag;

    public SceneOrder() {

    }
}
