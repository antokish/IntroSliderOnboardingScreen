package ke.co.citesoft.introslideronboardingscreen;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ke.co.citesoft.introslideronboardingscreen.utilities.Utils;

public class PagerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Button mSkipBtn, mFinishBtn;
    private ImageButton mNextBtn;
    public TextView tvLabel,tvDesc;


    ImageView ind_0,ind_1,ind_2;
    ImageView[] indicators;

    int page = 0; //track page position

    CoordinatorLayout mCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

       // txt = findViewById(R.id.section_label);

        //initializing imageview variables
        ind_0 = findViewById(R.id.intro_indicator_0);
        ind_1 = findViewById(R.id.intro_indicator_1);
        ind_2 = findViewById(R.id.intro_indicator_2);

        mCoordinator = findViewById(R.id.main_content);

        indicators = new ImageView[]{ind_0,ind_1,ind_2};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);


        final int color1 = ContextCompat.getColor(this,R.color.csred);
        final int color2 = ContextCompat.getColor(this,R.color.csblack);
        final int color3 = ContextCompat.getColor(this,R.color.cslight);

        final int [] colorList = new int[]{color1,color2,color3};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        //initializing buttons
        mSkipBtn = findViewById(R.id.btn_skip);
        mFinishBtn = findViewById(R.id.btn_finish);
        mNextBtn = findViewById(R.id.btn_next);

        tvLabel = findViewById(R.id.section_label);
        tvDesc = findViewById(R.id.section_desc);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                /*
                color update
                 */

                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {

                page = position;
                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                }

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //setting buttons listeners
        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page +=1;
                mViewPager.setCurrentItem(page,true);
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                //update first time preference
                Utils.saveSharedSetting(PagerActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
            }
        });


    }//End onCreate

    void updateIndicators(int position) {

        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ImageView img;

        int[] bgs = new int[]{R.drawable.ic_cs_logo_white, R.drawable.ic_cs_logo_white, R.drawable.ic_cs_logo_shade};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView tvDesc = rootView.findViewById(R.id.section_desc);
            Button skip = rootView.findViewById(R.id.btn_skip);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            if (getArguments().getInt(ARG_SECTION_NUMBER)==3){
                textView.setTextColor(getResources().getColor(R.color.csred));
                tvDesc.setTextColor(getResources().getColor(R.color.csdark));
               // skip.setTextColor(getResources().getColor(R.color.csred));
            }else {
                textView.setTextColor(getResources().getColor(R.color.cslight));
                tvDesc.setTextColor(getResources().getColor(R.color.cslight));
               // skip.setTextColor(getResources().getColor(R.color.cslight));
            }

            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){

            switch (position){
                case 0:
                    return "Section 1";
                case 1:
                    return "Section 2";
                case 3:
                    return "Section 3";
            }
            return null;
        }
    }
}
