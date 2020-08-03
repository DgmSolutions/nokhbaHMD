package slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.nokhba.nokhbahmd.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.raw.slide_avatars,
            R.raw.handwach,
            R.raw.mask,
            R.raw.stay_home,

    };

    int headings[] = {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.forth_slide_title,
    };

    int descs[] = {

            R.string.first_desc,
            R.string.secound_desc,
            R.string.third_desc,
            R.string.forth_desc,
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.animation_view_slider);
        TextView heading = view.findViewById(R.id.TitleViewSlider);
        TextView desc = view.findViewById(R.id.textViewSlider);

        lottieAnimationView.setAnimation(images[position]);
        heading.setText(headings[position]);
        desc.setText(descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
