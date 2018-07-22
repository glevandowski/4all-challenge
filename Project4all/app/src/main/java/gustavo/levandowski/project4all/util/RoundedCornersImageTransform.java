package gustavo.levandowski.project4all.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.squareup.picasso.Transformation;

/***
 * Classe para modificar formato de imagens solicitadas na api pelo picasso. obs: de forma dinâmica
 * @author Gustavo Levandowski
 */
public class RoundedCornersImageTransform implements Transformation {

    //region Propriedades
    private int size;
    private int width;
    private int height;
    private float resource;
    private Bitmap squaredBitmap;
    private Bitmap bitmap;
    private BitmapShader shader;
    private Canvas canvas;
    private Paint paint;
    //endregion

    //region Create Bitmap/receiving properties
    @Override
    public Bitmap transform(Bitmap source) {
        this.properties(source);
        this.initComponents(source);
        this.logicSquaredBitMap(source);
        this.rounded(source);

        return bitmap;
    }
    private void properties(Bitmap source){
        size = Math.min(source.getWidth(), source.getHeight());
        width = (source.getWidth() - size) / 2;
        height = (source.getHeight() - size) / 2;
    }
    //endregion

    //region initalComponents
    private void initComponents(Bitmap source){
        bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        squaredBitmap = Bitmap.createBitmap(source, width, height, size, size);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    }
    //endregion

    //region Logical / roundedImage /key
    private void logicSquaredBitMap(Bitmap source){
        if (squaredBitmap != source) {
            source.recycle();
        }
    }
    private void rounded(Bitmap source){
        paint.setShader(shader);
        paint.setAntiAlias(true);
        resource = size / 2f;
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), resource, resource, paint);
        squaredBitmap.recycle();
    }

    @Override
    public String key() {
        return "rounded_corners_image";
    }
    //endregion
}