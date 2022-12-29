package com.example.project_ver1.ui.shopping_list;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.project_ver1.AddShoppingListActivity;
import com.example.project_ver1.R;
import com.example.project_ver1.RVAdapter;
import com.example.project_ver1.ShopListDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingList extends Fragment {

    RVAdapter rvAdapter;
    ShopListDB db;
    RecyclerView shopListView;
    List<com.example.project_ver1.ShoppingList> lists;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingList.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingList newInstance(String param1, String param2) {
        ShoppingList fragment = new ShoppingList();
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
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        lists.clear();
        db = new ShopListDB(getContext());
        lists = db.getLists();
        db.close();
        rvAdapter = new RVAdapter(getContext(), lists);
        shopListView.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        // Otwieranie bazy danych i pobieranie z niej wszystkich list
        db = new ShopListDB(getContext());
        lists = db.getLists();
        db.close();

        // Dodawanie kazdej z list do widoku recycler view
        shopListView = (RecyclerView) view.findViewById(R.id.shopListView);
        shopListView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdapter = new RVAdapter(getContext(), lists);
        shopListView.setAdapter(rvAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        // Po kliknieciu przycisku z plusem otwiera sie okno dodawania nowej listy zakupow
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddShoppingListActivity.class);
                startActivity(intent);
            }
        });
    }
}