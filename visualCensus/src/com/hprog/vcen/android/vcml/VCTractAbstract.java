package com.hprog.vcen.android.vcml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class VCTractAbstract implements VCMLParser {
	static final String VCROOT = "vcroot";
    static final String STATEFP = "statefp";
    static final String COUNTYFP = "countyfp";
    static final String TRACTCE = "tractce";
    static final String BLKGRPCE = "blkgrpce";
    static final String BLOCKCE = "blockce";
    static final String TRACTBOUNDRY = "tractboundry";
    static final String VERTICE = "vertice";
    static final String LAT = "lat";
    static final String LNG = "lng";

    static int NUMBER_OF_VERTICES;
	
    final URL serverUrl;

    protected VCTractAbstract(String feedUrl){
        try {
            this.serverUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return serverUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
