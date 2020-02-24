package com.example.my_core.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_core.R;
import com.example.my_core.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

//自定义控件 上传图片
public class AutoPhotoLayout extends LinearLayout {

    //当前是第几个图片
    private int mCurrentNum = 0;
    //最多能放多少张图片
    private int mMaxNum = 0;
    //每行存放多少张图片
    private int mMaxLineNum = 0;
    //要删除的图片
    private int mDeleteId = 0;
    //选中的图片
    private ImageView mTargetImageView = null;
    //图片之间的空隙
    private int mImageMargin = 0;
    private LayoutParams mParams = null;
    private LatteDelegate mDelegate = null;
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private IconTextView mIconAdd = null;
    private float mIconSize = 0;
    //存储所有的View 一行行进行存储
    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    //存储每一行的高度
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

    //防止多次的测量和布局过程
    //Layout 布局过程中 onMeasure()会进行多次测量 会造成效率低下 内存占用过高
    private boolean mIsOnceInitOnMeasure = false;
    private boolean mIsOnceInitOnLayout = false;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);


    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        typedArray.recycle();
    }

    public final void setDelegate(LatteDelegate delegate){
        this.mDelegate = delegate;
    }

    //剪裁图片
    public final void onCropTarget(Uri uri){
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImageView);
    }

    //创建新的图片
    private void createNewImageView(){
        mTargetImageView = new ImageView(getContext());
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要删除的图片id
                mDeleteId = v.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if(window != null){
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                    //删除图片
                    window.findViewById(R.id.dialog_image_clicked_btn_delete).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //得到要删除的图片
                            final ImageView deleteImageView = findViewById(mDeleteId);
                            //设置图片逐渐消失的动画
                            final AlphaAnimation animation = new AlphaAnimation(1,0);
                            animation.setDuration(500);
                            animation.setRepeatCount(0);
                            animation.setFillAfter(true);
                            animation.setStartOffset(0);
                            deleteImageView.setAnimation(animation);
                            animation.start();
                            AutoPhotoLayout.this.removeView(deleteImageView);
                            mCurrentNum -= 1;
                            //当数目达到上限时 隐藏按钮 不足时显示
                            if(mCurrentNum < mMaxNum){
                                mIconAdd.setVisibility(VISIBLE);
                            }
                            mTargetDialog.cancel();
                        }
                    });

                    //取消操作
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTargetDialog.cancel();
                        }
                    });
                }
            }
        });
        //添加子View的时候传入位置
        this.addView(mTargetImageView,mCurrentNum);
        mCurrentNum++;
        //当添加数目大于mMaxNum 时，自动隐藏添加按钮
        if(mCurrentNum >= mMaxNum){
            mIconAdd.setVisibility(GONE);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素个数 即 添加框的个数
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            //测量子View 的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获取 间距值
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //获取子View 占据的宽度 测量子View的宽度 加上左右两边的边距 = 所占据的宽度
            final int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //获取子View 占据的高度 测量子View的高度 加上上下两边的边距 = 所占据的高度
            final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //未换行 叠加行宽
                lineWidth += childWidth;
                //得到当前最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //每行 添加到最后一个图片的时候
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                //判断是否超过最大拍照限制
                height += lineHeight;
            }
        }
        //如果超过最大照片限制 就不再拍照 并且消失IconAdd
        //设置测量值的方法
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingBottom() + getPaddingTop()
        );
        //设置一行所有图片的宽高
        final int imageSizeLen = sizeWidth / mMaxLineNum;
        //只初始化 一次
        if (!mIsOnceInitOnMeasure) {
            mParams = new LayoutParams(imageSizeLen, imageSizeLen);
            mIsOnceInitOnMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //将之前的View 清除掉
        ALL_VIEWS.clear();
        //当前的ViewGroup宽度
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        if(!mIsOnceInitOnLayout){
            mLineViews = new ArrayList<>();
            mIsOnceInitOnLayout = true;
        }
        final int cCount = getChildCount();
        for(int i = 0; i< cCount ; i ++){
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            //如果需要换行
            if(childWidth + lineWidth + lp.leftMargin + lp.rightMargin >width - getPaddingLeft() - getPaddingRight()){
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的Views
                ALL_VIEWS.add(mLineViews);
                //重置宽和高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置VIEWS集合
                mLineViews.clear();
            }
            //一行的 高度 和宽度变换
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight,lineHeight+lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }
        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);
        //设置子View位置
         int left = getPaddingLeft();
         int top = getPaddingTop();
        //行数
        final int lineNum = ALL_VIEWS.size();
        for(int i = 0 ; i < lineNum ; i ++){
            //当前行所有的View
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for(int j= 0;j<size;j++){
                final View child = mLineViews.get(j);
                //判断child的状态
                if(child.getVisibility() == GONE){
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //设置子View的边距
                final int lc = left+lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth() - mImageMargin;
                final int bc = tc + child.getMeasuredHeight();
                //为子View进行布局
                child.layout(lc,tc,rc,bc);
                left += child.getMeasuredWidth() +lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        mIsOnceInitOnLayout = false;
    }

    private void initAddIcon() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.border_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.startCameraWithCheck();
            }
        });
        this.addView(mIconAdd);
    }

    //Inflate 完成之后才调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }
}
