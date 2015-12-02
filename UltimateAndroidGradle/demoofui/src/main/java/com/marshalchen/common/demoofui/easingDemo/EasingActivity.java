package com.marshalchen.common.demoofui.easingDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.easing.BaseEasingMethod;
import com.marshalchen.common.uimodule.easing.Glider;
import com.marshalchen.common.uimodule.easing.Skill;
import  com.nineoldandroids.animation.AnimatorSet;
import  com.nineoldandroids.animation.ObjectAnimator;


public class EasingActivity extends Activity {
    private ListView mEasingList;
    private EasingAdapter mAdapter;
    private View mTarget;

    private DrawView mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easing_activity_my);
        mEasingList = (ListView) findViewById(R.id.easing_list);
        mAdapter = new EasingAdapter(this);
        mEasingList.setAdapter(mAdapter);
        mTarget = findViewById(R.id.target);
        mHistory = (DrawView) findViewById(R.id.history);
        mEasingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mHistory.clear();
                Skill s = (Skill) view.getTag();
                AnimatorSet set = new AnimatorSet();
                mTarget.setTranslationX(0);
                mTarget.setTranslationY(0);
                set.playTogether(
                        Glider.glide(s, 1200, ObjectAnimator.ofFloat(mTarget, "translationY", 0, dipToPixels(EasingActivity.this, -(160 - 3))), new BaseEasingMethod.EasingListener() {
                            @Override
                            public void on(float time, float value, float start, float end, float duration) {
                                mHistory.drawPoint(time, duration, value - dipToPixels(EasingActivity.this, 60));
                            }
                        })
                );
                set.setDuration(1200);
                set.start();
            }
        });

    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_animations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}