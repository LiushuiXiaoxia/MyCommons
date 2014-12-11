package org.liushui.mycommons.android.demo.common;


import java.io.Serializable;

/**
 */
public class CommonExtraParam implements Serializable {

    public String fragmentTitle;

    public Class<? extends CommonFragment> fragmentClass;

    public CommonExtraParam() {
    }

    public CommonExtraParam(Class<? extends CommonFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    public CommonExtraParam(String title, Class<? extends CommonFragment> fragmentClass) {
        this.fragmentTitle = title;
        this.fragmentClass = fragmentClass;
    }

    public boolean validate() {
        return fragmentClass != null;
    }

    public String toString() {
        return "CommonExtraParam{" +
                "fragmentTitle='" + fragmentTitle + '\'' +
                ", fragmentClass=" + fragmentClass +
                '}';
    }
}