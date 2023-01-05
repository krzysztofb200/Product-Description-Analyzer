package com.example.project_ver1.ui.all_products;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_ver1.AllProducts;
import com.example.project_ver1.AllProductsRVAdapter;
import com.example.project_ver1.PromoCodes;
import com.example.project_ver1.PromoCodesRVAdapter;
import com.example.project_ver1.R;
import com.example.project_ver1.ui.promo_codes.PromoCodesFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllProductsFragment extends Fragment {

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link com.example.project_ver1.ui.promo_codes.PromoCodesFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

    private RecyclerView recyclerView;
    AllProductsRVAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database

    // Initializing the ImageView
    //ImageView rImage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromoCodesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.project_ver1.ui.all_products.AllProductsFragment newInstance(String param1, String param2) {
        com.example.project_ver1.ui.all_products.AllProductsFragment fragment = new com.example.project_ver1.ui.all_products.AllProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_products, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference().child("products");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_all_products);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<AllProducts> options
                = new FirebaseRecyclerOptions.Builder<AllProducts>()
                .setQuery(mbase, AllProducts.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new AllProductsRVAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    public void onResume(){
        super.onResume();
        mbase = FirebaseDatabase.getInstance().getReference().child("products");

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<AllProducts> options
                = new FirebaseRecyclerOptions.Builder<AllProducts>()
                .setQuery(mbase, AllProducts.class)
                .build();
        adapter = new AllProductsRVAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}