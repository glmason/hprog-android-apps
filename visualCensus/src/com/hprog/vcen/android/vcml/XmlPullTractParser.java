package com.hprog.vcen.android.vcml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import org.xmlpull.v1.XmlPullParserFactory;

public class XmlPullTractParser extends VCTractAbstract {	
	public XmlPullTractParser(String serverURL) {
		super(serverURL);
	}

	@Override
	public VCMLBasicBean parse(){
		   VCTractBean currentTract = null;
	         XmlPullParserFactory factory = null;
			try {
				factory = XmlPullParserFactory.newInstance();
			} catch (XmlPullParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         factory.setNamespaceAware(false);
	         XmlPullParser parser = null;
			try {
				parser = factory.newPullParser();
			} catch (XmlPullParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        try {
	            // auto-detect the encoding from the stream
	            parser.setInput(this.getInputStream(), null);
	            int eventType = parser.getEventType();
	            
	            boolean done = false;
	            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
	                String name = null;
	                switch (eventType){
	                    case XmlPullParser.START_DOCUMENT:
	                    	currentTract = new VCTractBean();
	                        break;
	                    case XmlPullParser.START_TAG:
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase(STATEFP)){
	                            currentTract.setStatefp(parser.nextText());   
	                        } else if (name.equalsIgnoreCase(COUNTYFP)) {
	                            currentTract.setCountyfp(parser.nextText());
	                        } else if (name.equalsIgnoreCase(TRACTCE)) {
	                            currentTract.setTractce(parser.nextText());
	                        } else if (name.equalsIgnoreCase(BLKGRPCE)) {
	                            currentTract.setBlkgrpce(parser.nextText());
	                        } else if (name.equalsIgnoreCase(BLOCKCE)) {
	                            currentTract.setBlockce(parser.nextText());   
	                        } else if (name.equals(VERTICE)) {
	                        	Double gplat = new Double(parser.getAttributeValue(null, LAT));
	                        	Double gplng = new Double(parser.getAttributeValue(null, LNG));
	                        	currentTract.addBoundryVertice(gplat, gplng);
	                        }    
	                        break;
	                    case XmlPullParser.END_TAG:
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase(VCROOT)){
	                            done = true;
	                        }
	                        break;
	                }
	                eventType = parser.next();
	            }
	            
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	        return currentTract;   	
	}

}