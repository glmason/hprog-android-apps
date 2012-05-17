package com.hprog.vcen.android.view;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.hprog.vcen.android.R;


public class StatsDrawableView extends View{
		Context mContext;
		Object[] names = null;
		
		public StatsDrawableView(Context context) {
			super(context);
			mContext = context;
		}
		
		@Override
		protected void onDraw(Canvas canvas){
			canvas.drawColor(Color.DKGRAY);
			Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			myPaint.setStrokeWidth(3);
			RectF pie = new RectF(10,10,310,310);
			
			// TODO get an array of pei wedges
			myPaint.setColor(Color.RED);
			canvas.drawArc(pie, 0, (float)(360 * .28), true, myPaint);
			
			myPaint.setColor(Color.YELLOW);
			canvas.drawArc(pie, (float)(360 * .28), (float)(360 * .43), true, myPaint);
			
			myPaint.setColor(Color.GREEN);
			canvas.drawArc(pie, (float)(360 * .71), (float)(360 * .29), true, myPaint);
			//---------------------------
			
			canvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(), 
					R.drawable.vcicon), 184, 184, null);
			myPaint.setColor(Color.LTGRAY);
			
			myPaint.setStrokeWidth(6);
			canvas.drawText("Screen size:", 150, 250, myPaint);
			canvas.drawText("  Width = " + canvas.getWidth(), 150, 265, myPaint);
			canvas.drawText("  Height = " + canvas.getHeight(), 150, 280, myPaint);
			invalidate();
		}
	}
