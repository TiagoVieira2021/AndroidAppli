package lu.letsfruit.eshop.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.Model.Product;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.service.CartService;
import lu.letsfruit.eshop.service.CategoryService;
import lu.letsfruit.eshop.controller.adapter.ShopTopMenuAdapter;
import lu.letsfruit.eshop.controller.adapter.ShopCategoryContentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShop extends Fragment {


    private RecyclerView topMenu;
    private RecyclerView categoryContent;
    private Context context;
    private View view;
    private ProgressBar progressBar;

    private final CategoryService categoryService = CategoryService.getInstance();
    private final CartService cartService = CartService.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentShop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentShop newInstance(String param1, String param2) {
        FragmentShop fragment = new FragmentShop();
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
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        this.view = view;

        initViews();
        setRecyclersLayoutManager();
        fetchRecyclerViewData();
    }

    private void fetchRecyclerViewData() {
        categoryService.getMenuCategories(
                context,
                categories -> {
                    topMenu.setAdapter(
                            new ShopTopMenuAdapter(
                                    context,
                                    categories,
                                    this::setRecyclersAdapter
                            )
                    );
                    setRecyclersAdapter(categories.get(0));
                    progressBar.setVisibility(View.GONE);
                }
        );
    }

    private void setRecyclersAdapter(Category category) {
        if (!category.getSubCategories().isEmpty()) {
            categoryContent.setAdapter(
                    new ShopCategoryContentAdapter<>(
                            context,
                            category.getSubCategories(),
                            subCategory -> {
                                Toast.makeText(context, ((Category) subCategory).getName(), Toast.LENGTH_SHORT).show();
                            }
                    )
            );
        } else {
            categoryContent.setAdapter(
                    new ShopCategoryContentAdapter<>(
                            context,
                            category.getProducts(),
                            product -> {
                                cartService.addToCart(context, (Product) product, (p, quantity) -> {
                                    Toast.makeText(context,  p.getName() + " ajouté au panier\nQuantité: " + quantity, Toast.LENGTH_SHORT).show();
                                });
                            }
                    )
            );
        }
    }

    private void setRecyclersLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        topMenu.setLayoutManager(linearLayoutManager);
        categoryContent.setLayoutManager(new GridLayoutManager(context, 2));
    }

    private void initViews() {
        topMenu = view.findViewById(R.id.topMenu);
        categoryContent = view.findViewById(R.id.categoryContent);
        progressBar = view.findViewById(R.id.progressbar);
    }
}