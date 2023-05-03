package com.example.zeeshanhussain.qwicklyapppart1;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Favourties2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavtAdapter adapter;
    private List<ShoeData> shoeDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourties2);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.favt2_recyclerView);
        shoeDataList = new ArrayList<>();
        adapter = new FavtAdapter(this, shoeDataList);



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


    }

/*
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int ScrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (ScrollRange == -1) {
                    ScrollRange = appBarLayout.getTotalScrollRange();

                }
                if (ScrollRange + verticalOffset == 0) {


                } else if (isShow) {

                }
            }
        });
    }
    */

    private void prepareAlbums() {
        int[] products = new int[]{
                R.drawable.product_1,
                R.drawable.product_2,
                R.drawable.product_3,
                R.drawable.product_4
        };
        ShoeData s1 = new ShoeData("Price is 2400 Rs", "PT-24000", "Mens Wear","Abbasi1", products[0]);
        shoeDataList.add(s1);
        ShoeData s2 = new ShoeData("Price is 2000 Rs", "FT-24000", "Mens Cotton","Abbasi 2", products[1]);
        shoeDataList.add(s2);
        ShoeData s3 = new ShoeData("Price is 8000 Rs", "PGT-24000", "Mens Casual","Abbasi 3", products[2]);
        shoeDataList.add(s3);
        ShoeData s4 = new ShoeData("Price is 3000 Rs", "S-24000", "Mens Special Wear","Abbasi 4", products[3]);
        shoeDataList.add(s4);

        adapter.notifyDataSetChanged();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int SpanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int SpanCount, int spacing, boolean includeEdge) {
            this.SpanCount = SpanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            int column = position % SpanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / SpanCount;
                outRect.right = (column + 1) * spacing / SpanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < SpanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;


            } else {
                outRect.left = column * spacing / SpanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / SpanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= SpanCount) {
                    outRect.top = spacing; // item top
                }


            }
        }

    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));

    }


}
