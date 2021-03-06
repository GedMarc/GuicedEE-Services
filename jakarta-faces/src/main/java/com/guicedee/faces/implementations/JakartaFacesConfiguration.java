package com.guicedee.faces.implementations;

import com.guicedee.guicedinjection.GuiceConfig;
import com.guicedee.guicedinjection.interfaces.IGuiceConfigurator;

public class JakartaFacesConfiguration implements IGuiceConfigurator {

    @Override
    public GuiceConfig configure(GuiceConfig config) {
        return config.setPathScanning(true);
    }
}
