package kunyu.healtheducator.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;

/**
 * Created by QuentinTsai on 17/04/2016.
 */
public class ImageViewHandler {

    public static void greyLock(ImageView imageView){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(cf);
        imageView.setAlpha(128);
    }

    public static void greyUnlock(ImageView imageView){
        imageView.setColorFilter(null);
        imageView.setAlpha(255);
    }
}
