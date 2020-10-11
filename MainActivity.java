package taylorhelene09gmail.cardviewp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    private ArrayList layouts;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Button btnSkip, btnNext, btnGotIt;
    private prefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Checking for first time launch - before calling setContentView()
        prefManager = new prefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        models = new ArrayList<>();
        models.add(new Model(R.drawable.occasionci, "Special Occasion", "Bloom will make it happen"));
        models.add(new Model(R.drawable.exploreci, "Explore", "Search and explore our gift packages"));
        models.add(new Model(R.drawable.purchaseci, "Purchase", "Complete the purchase and bloom will take it from there"));
        models.add(new Model(R.drawable.delcirc, "Delivery", "Our bloom agent will deliver your gift and read out your special message"));

        adapter = new Adapter(models, this);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnGotIt = (Button) findViewById(R.id.btnOrder);
        imageView = (ImageView) findViewById(R.id.imageview);


        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                int pageWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
                int pageHeight = viewPager.getHeight();
                int paddinfLeft = viewPager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (viewPager.getScrollX() + paddinfLeft)) / pageWidth;
                final float normalizedposition = Math.abs(Math.abs(transformPos) - 1);
                page.setAlpha(normalizedposition + 0.5f);
                int max = -pageHeight / 10;
                if (transformPos < -1) {
                    page.setTranslationY(0);
                } else if (transformPos <= 1) {
                    page.setTranslationY(max * (1 - Math.abs(transformPos)));
                } else {
                    page.setTranslationY(0);
                }
            }
        });
        viewPager.setBackgroundResource(R.drawable.lay);


        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchHomeScreen();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched

                onPage();
            }
        });


    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(MainActivity.this, another.class));
        finish();
    }

    public void onPage() {

        int pos=adapter.getCount();
        if (pos < (adapter.getCount() +1)) {
            viewPager.setCurrentItem(pos);
        } else {
            launchHomeScreen();
        }


    };





}
