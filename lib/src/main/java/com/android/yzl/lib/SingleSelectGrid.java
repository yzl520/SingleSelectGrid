package com.android.yzl.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yzl on 2016/5/27.
 */
public class SingleSelectGrid extends ViewGroup implements View.OnClickListener {

    private final int defaultColumnNum = 3;
    private int mColumnNum;
    private int hDivider;
    private int vDivider;
    private int itemHeight;
    private View lastSelectItem;
    private SingleSelectGridAdapter adapter;
    private ViewGroup.LayoutParams mmLp;

    public SingleSelectGrid(Context context) {
        super(context);
    }

    public SingleSelectGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public SingleSelectGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SingleSelectGrid);
        try {
            mmLp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mColumnNum = ta.getInt(R.styleable.SingleSelectGrid_columnNum, defaultColumnNum);
            hDivider = ta.getDimensionPixelSize(R.styleable.SingleSelectGrid_hDivider, 0);
            vDivider = ta.getDimensionPixelSize(R.styleable.SingleSelectGrid_vDivider, 0);
            itemHeight = ta.getDimensionPixelSize(R.styleable.SingleSelectGrid_itemHeight, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getDefaultSize(0, widthMeasureSpec);

        int childWidth = getChildWidth(widthSize);
        if(itemHeight <= 0){
            itemHeight = childWidth;
        }
        int childWidthSpec = View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY);
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(itemHeight, View.MeasureSpec.EXACTLY);
        measureChildren(childWidthSpec, childHeightSpec);

        int extraRow = getChildCount() % mColumnNum == 0 ? 0 : 1;
        int rowCount = getChildCount() / mColumnNum + extraRow;
        int heightSize = itemHeight * rowCount + vDivider * (rowCount - 1);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, childLeft, childTop;
        int childFreeSpace = getChildWidth(getMeasuredWidth());
        for (int i = 0; i < getChildCount(); i++) {
            row = i / mColumnNum;
            col = i % mColumnNum;

            View child = getChildAt(i);
            childLeft = hDivider * col + col * childFreeSpace;
            childTop = vDivider * row + row * itemHeight;
            child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
        }
    }

    private int getChildWidth(int parentWidth){
        return (int) ((parentWidth - hDivider * (mColumnNum - 1)) * 1F / mColumnNum * 1F);
    }

    public void setAdapter(SingleSelectGridAdapter adapter) {
        this.adapter = adapter;
        if(adapter == null){
            return;
        }
        removeAllViews();
        for(int i = 0; i< adapter.getCount(); i++){
            View view = adapter.getView(i, this);
            view.setTag(i);
            view.setOnClickListener(this);
            addView(view, mmLp);
        }
    }

    @Override
    public void onClick(View v) {
        boolean select = v.isSelected();

        if(lastSelectItem != null && !lastSelectItem.equals(v)){
            lastSelectItem.setSelected(false);
        }

        adapter.onSelect((Integer) v.getTag(), v, lastSelectItem);
        if(select){
            return;
        }
        v.setSelected(!select);
        lastSelectItem = v;
    }

    public interface SingleSelectGridAdapter{
        int getCount();
        View getView(int pos, ViewGroup parent);
        void onSelect(int pos, View v, View lastSelectItem);
    }

}
