package com.hprog.vcen.android.vcml;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.google.android.maps.GeoPoint;

public class VCTractBean extends VCMLBasicBean {
	private String statefp;
	private String countyfp;
	private String tractce;
	private String blkgrpce;
	private String blockce;
	private List<GeoPoint> boundryVertices;
	private GeoPoint clickPnt;
	
	public String getStatefp() {
		return statefp;
	}
	public void setStatefp(String statefp) {
		this.statefp = statefp;
	}
	public String getCountyfp() {
		return countyfp;
	}
	public void setCountyfp(String countyfp) {
		this.countyfp = countyfp;
	}
	public String getTractce() {
		return tractce;
	}
	public void setTractce(String tractce) {
		this.tractce = tractce;
	}
	public String getBlkgrpce() {
		return blkgrpce;
	}
	public void setBlkgrpce(String blkgrpce) {
		this.blkgrpce = blkgrpce;
	}
	public String getBlockce() {
		return blockce;
	}
	public void setBlockce(String blockce) {
		this.blockce = blockce;
	}
	public List<GeoPoint> getBoundryVertices() {
		if (boundryVertices == null) {
			boundryVertices = new ArrayList<GeoPoint>();
		}
		return boundryVertices;
	}
	public void setBoundryVertices(List<GeoPoint> boundryVertices) {
		this.boundryVertices = boundryVertices;
	}	
	public void addBoundryVertice(Double lat, Double lon) {
		GeoPoint gpnt = new GeoPoint((int)(lat * 1E6),(int)(lon * 1E6) );
		getBoundryVertices().add(gpnt);
	}
	public GeoPoint getPnt() {
		return clickPnt;
	}
	public void setRequestClickPnt(GeoPoint click) {
		clickPnt = click;
	}
}
