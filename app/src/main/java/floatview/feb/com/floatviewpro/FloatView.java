package floatview.feb.com.floatviewpro;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by lilichun on 2018/11/22.
 * 悬浮框
 */
public class FloatView extends View {

    private final static String TAG = FloatView.class.getSimpleName();
    private Context mContext;
    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;
    private View mContentView;
    private float mScreenX;
    private float mScreenY;
    private float mRelativeX;
    private float mRelativeY;
    private boolean bShow = false;

    public FloatView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wmParams == null) {
            wmParams = new WindowManager.LayoutParams();
        }
        mContext = context;
    }

    public void setLayout(int layout_id) {
        mContentView = LayoutInflater.from(MyApplication.getInstance()).inflate(layout_id, null);
        mContentView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mScreenX = event.getRawX();
                mScreenY = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mRelativeX = event.getX();
                        mRelativeY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updateViewPosition();
                        break;
                    case MotionEvent.ACTION_UP:
                        updateViewPosition();
                        mRelativeX = mRelativeY = 0;
                        break;
                }
                return true;
            }
        });
    }

    private void updateViewPosition() {
        wmParams.x = (int) (mScreenX - mRelativeX);
        wmParams.y = (int) (mScreenY - mRelativeY);
        wm.updateViewLayout(mContentView, wmParams);
    }

    public void show() {
        if (mContentView != null) {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            wmParams.format = PixelFormat.RGBA_8888;
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            wmParams.alpha = 0.5f;
            wmParams.gravity = Gravity.LEFT | Gravity.TOP;
            wmParams.x = 0;
            wmParams.y = 0;
            wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            // 显示自定义悬浮窗口
            wm.addView(mContentView, wmParams);
            bShow = true;
        }
    }

    public void close() {
        if (mContentView != null) {
            wm.removeView(mContentView);
            bShow = false;
        }
    }

    public boolean isShow() {
        return bShow;
    }

}
