package com.hprog.vcen.android.view;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CVOverlayBuilder extends Overlay {
	private ArrayList<GeoPoint>vertices;
    private RectF bounds;
	
	public CVOverlayBuilder(ArrayList<GeoPoint> vertices) {
		this.vertices = vertices;
	}
	
	@Override
	public void draw(Canvas canvas, MapView map, boolean shadow) {
		Paint cvPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Projection proj = map.getProjection();
		
		Path p = new Path();
		int i = 0;
		int j = vertices.size();
		GeoPoint gpt = vertices.get(i);
		Point pt = new Point();
		proj.toPixels(gpt, pt);
		p.moveTo(pt.x,pt.y);
		i++;
		while (i < j) {
			gpt = vertices.get(i);
			proj.toPixels(gpt, pt);
			p.lineTo(pt.x,pt.y);
            i++;
		}		
		p.close();
		
		bounds = new RectF();
		p.computeBounds(bounds, true);
		
		//this.drawAt(canvas, p, oval.centerX(), oval.centerY(), false);
		cvPaint.setStyle(Paint.Style.FILL);
		cvPaint.setColor(Color.argb(50, 45, 96, 189));
		canvas.drawPath(p, cvPaint);

		cvPaint.setColor(Color.argb(255, 28, 28, 126));		
		cvPaint.setStrokeWidth(6);
		cvPaint.setTextSize(25);
		cvPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText("10101",(int)bounds.centerX(),(int)bounds.centerY(), cvPaint);
	}
	
	public Point getCenter() {
		Point pnt = new Point();
		pnt.x = (int)bounds.centerX();
		pnt.y = (int)bounds.centerY();
        return pnt; 
	}
	
	public boolean dispatchMotionEvent(MotionEvent ev) { 
        return false; 
	} 

}
