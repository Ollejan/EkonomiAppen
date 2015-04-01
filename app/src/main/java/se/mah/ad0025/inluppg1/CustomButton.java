package se.mah.ad0025.inluppg1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Klass som ärver Button. Ändrar om designen på en vanlig knapp.
 * @author Jonas Dahlström 5/10-14
 */
public class CustomButton extends Button {
    private Paint paint;

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(paint == null) {
            paint = new Paint();
            paint.setShader(new RadialGradient(20, 20, 10, 0x00BBFF, Color.WHITE, Shader.TileMode.MIRROR));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(paint);
        super.onDraw(canvas);
    }
}
