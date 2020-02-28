package com.cali;

public class DefaultSecurityManagerImpl extends SecurityManagerImpl {
    public DefaultSecurityManagerImpl() {

        /*
         * Calidoc actions. See com.cali.stdlib.CLang.java.
         */
        this.props.put("calidoc.file.getJson", true);
        this.props.put("calidoc.class.getJson", true);
    }
}
