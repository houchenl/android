package com.hc.chart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hc.chart.util.FixPair;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {
	
	private RectF mChartAreaRectF = null;
	private RectF mAnnulusAreaRectF = null;
	private RectF mFgAreaRectF = null;
	private int mFgColor = 0;
	private float mStartAngle = -90;
	private float mAnnulusWidth = 20;
	private float mBgEdgeWidth = 5;

	private Paint mPaint = null;

	private List<FixPair<Integer, Integer>> mLstPercentAndColor = new ArrayList<>();
	
	private int mBgColor = 0;

	public PieChartView(Context context) {
		super(context);
		init();
	}

	public PieChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PieChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	
	private void init() {
		if (mPaint == null) {
			mPaint = new Paint();
		}
	}

	public void setData(List<FixPair<Integer, Integer>> lst) {
		mLstPercentAndColor.clear();
		mLstPercentAndColor.addAll(lst);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mChartAreaRectF == null) {
			forceAdjustLayers();
		}

		doDraw(canvas);
	}

	private void forceAdjustLayers() {
		float left = getPaddingLeft() + 1;
		float top = getPaddingTop() + 1;
		float right = getMeasuredWidth() - getPaddingRight() - 1;
		float bottom = getMeasuredHeight() - getPaddingBottom() - 1;
		mChartAreaRectF = new RectF(left, top, right, bottom);

		prepareBeforeDraw();
	}

	private void prepareBeforeDraw() {
		float left = mChartAreaRectF.left + mBgEdgeWidth;
		float top = mChartAreaRectF.top + mBgEdgeWidth;
		float right = mChartAreaRectF.right - mBgEdgeWidth;
		float bottom = mChartAreaRectF.bottom - mBgEdgeWidth;

		mAnnulusAreaRectF = new RectF(left, top, right, bottom);

		left = left + mAnnulusWidth;
		top = top + mAnnulusWidth;
		right = right - mAnnulusWidth;
		bottom = bottom - mAnnulusWidth;

		mFgAreaRectF = new RectF(left, top, right, bottom);
	}

	private void doDraw(Canvas canvas) {
		mPaint.setColor(mBgColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawArc(mChartAreaRectF, 0, 360, true, mPaint);

		float tStartAngle = mStartAngle;
		for(int i = 0; i < mLstPercentAndColor.size(); i++)
		{
			FixPair<Integer, Integer> tPair = mLstPercentAndColor.get(i);
			mPaint.setColor(tPair.getSecond());
			float sweepAngel = tPair.getFirst() / 100f * 360;
			canvas.drawArc(mAnnulusAreaRectF, tStartAngle, sweepAngel, true, mPaint);
			tStartAngle += sweepAngel;
		}

		mPaint.setColor(mFgColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawArc(mFgAreaRectF, 0, 360, true, mPaint);
	}

	public void setBgColor(int bgColor) {
		this.mBgColor = bgColor;
	}

	public void setFgColor(int fgColor) {
		this.mFgColor = fgColor;
	}

	public void setStartAngle(float startAngle) {
		this.mStartAngle = startAngle;
	}

	public void setBgEdgeWidth(float bgEdgeWidth) {
		this.mBgEdgeWidth = bgEdgeWidth;
	}

	public void setAnnulusWidth(float annulusWidth) {
		this.mAnnulusWidth = annulusWidth;
	}
	
}
