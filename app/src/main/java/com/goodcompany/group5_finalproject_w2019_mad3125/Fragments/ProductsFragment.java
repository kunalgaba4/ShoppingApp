package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.BaseActivity;
import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.ProductsAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Listeners.ProductSelectListener;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ReadJSONUtils;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements ProductSelectListener {

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    Unbinder unbinder;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.sl_about)
    ShadowLayout slAbout;
    @BindView(R.id.Logout)
    TextView Logout;
    @BindView(R.id.sl_Logout)
    ShadowLayout slLogout;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    private Context mContext;
    private ProductsAdapter productsAdapter;
    private String jsonString;
    private ArrayList<ProductsModal> productsModals;
    private ProductDetailsFragment productDetailsFragment;
    private OrderDetailsFragment orderDetailsFragment;

    public ProductsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDatafromJson();
        setupProductsAdapter();
    }

    private void loadDatafromJson() {
        jsonString = ReadJSONUtils.loadJSONFromAsset(mContext, "products.json");
        Gson gson = new Gson();
        productsModals = new ArrayList<>();
        Type founderListType = new TypeToken<ArrayList<ProductsModal>>() {
        }.getType();
        productsModals = gson.fromJson(jsonString, founderListType);
    }

    private void setupProductsAdapter() {
        productsAdapter = new ProductsAdapter(mContext, productsModals, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(productsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onProductSelected(int position) {
        productDetailsFragment = new ProductDetailsFragment();
        Bundle b = new Bundle();
        ProductsModal productsModal = productsModals.get(position);
        b.putSerializable("product", productsModal);
        productDetailsFragment.setArguments(b);
        ((BaseActivity) getActivity()).addFragment(R.id.parent, productDetailsFragment, "details", "details", true);

    }

    @Override
    public void onProductDelete(int position) {

    }

    @OnClick({R.id.sl_about, R.id.sl_Logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sl_about:
                break;
            case R.id.sl_Logout:
                OrdersFragment ordersFragment = new OrdersFragment();
                ((BaseActivity) getActivity()).addFragment(R.id.parent, ordersFragment, "cart", "cart", true);
                break;
        }
    }
}
