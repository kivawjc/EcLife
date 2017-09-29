package weifen.com.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 2017/9/19.
 */
public class ParallaxScrollView extends ScrollView {
    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int maxHeight;
    private ImageView imageView, headerImageView;
    private int orignalHeight;//ImageView最初的高度

    public void setParallaxImageView(final ImageView imageView) {
        this.imageView = imageView;

        //设定最大高度
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                orignalHeight = imageView.getHeight();
                Log.e("tag", "orignalHeight: " + orignalHeight);
                int drawableHeight = imageView.getDrawable().getIntrinsicHeight();//图片的高度
                maxHeight = orignalHeight > drawableHeight ?
                        orignalHeight * 2 : drawableHeight;
            }
        });

    }

    /**
     * 在listview滑动到头的时候执行，可以获取到继续滑动的距离和方向
     * deltaX：继续滑动x方向的距离
     * deltaY：继续滑动y方向的距离     负：表示顶部到头   正：表示底部到头
     * maxOverScrollX:x方向最大可以滚动的距离
     * maxOverScrollY：y方向最大可以滚动的距离
     * isTouchEvent: true: 是手指拖动滑动     false:表示fling靠惯性滑动;
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

//		Log.e("tag", "deltaY: "+deltaY   +   "  isTouchEvent:"+isTouchEvent);
        if (deltaY < 0 && isTouchEvent) {
            //表示顶部到头，并且是手动拖动到头的情况
            //我们需要不断的增加ImageView的高度
            if (imageView != null) {
                int newHeight = imageView.getHeight() - deltaY / 3;
                if (newHeight > maxHeight) newHeight = maxHeight;

                imageView.getLayoutParams().height = newHeight;
                imageView.requestLayout();//使ImageView的布局参数生效
            }

        }
        if(!isTouchEvent&&deltaY<0){//惯性滑动的时候
            if (headerImageView != null) {
                headerImageView.clearAnimation();
            }
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //需要将ImageView的高度缓慢恢复到最初高度
            ValueAnimator animator = ValueAnimator.ofInt(imageView.getHeight(), orignalHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    //获取动画的值，设置给imageview
                    int animatedValue = (Integer) animator.getAnimatedValue();

                    imageView.getLayoutParams().height = animatedValue;
                    imageView.requestLayout();//使ImageView的布局参数生效
                }
            });
            animator.setInterpolator(new OvershootInterpolator(5));//弹性的插值器
            animator.setDuration(350);
            animator.start();
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 监听滚动的时候的位置变化
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //t>0
        if(t>0&&t<imageView.getHeight()){
            //计算移动的距离占有效范围的百分比
            float fraction=t*1.0f/headerImageView.getBottom();
            startHeaderImageViewAnimation(fraction);
        }
//        Log.d("tag", "onScrollChanged: "+t);
    }

    private void startHeaderImageViewAnimation(float fraction) {
        ViewCompat.setScaleX(headerImageView,1-fraction);
        ViewCompat.setScaleY(headerImageView,1-fraction);
        //设置透明
        ViewCompat.setAlpha(headerImageView,1-fraction);
    }

    public void setHeaderImageView(ImageView headerImageView) {
        this.headerImageView = headerImageView;
    }


}
