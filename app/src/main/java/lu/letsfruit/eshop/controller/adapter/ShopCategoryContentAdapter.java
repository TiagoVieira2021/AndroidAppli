package lu.letsfruit.eshop.controller.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.Model.Product;
import lu.letsfruit.eshop.Model.ShopAdapterItem;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.controller.LoginActivity;
import lu.letsfruit.eshop.controller.ShopActivity;
import lu.letsfruit.eshop.service.CartService;

public class ShopCategoryContentAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<T> items;
    private final ClickCategoryListener listener;
    private final Context context;

    private RecyclerView recyclerView;

    private static final int ITEM_TYPE_CATEGORY = 1;
    private static final int ITEM_TYPE_PRODUCT = 2;

    private final int[] images = {
            R.drawable.viande,
            R.drawable.poisson,
            R.drawable.legumes,
            R.drawable.non_alimentaire,
            R.drawable.conserves,
            R.drawable.legumes
    };

    public interface ClickCategoryListener {
        void onClickCategoryListener(ShopAdapterItem category);
    }

    public ShopCategoryContentAdapter(Context context, List<T> items, ClickCategoryListener listener) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_CATEGORY) {
            View inflater = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.shop_category_block, parent, false);
            return new CategoryViewHolder(inflater);
        } else {
            View inflater = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.shop_product_block, parent, false);
            return new ProductViewHolder(inflater);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ITEM_TYPE_CATEGORY) {

            Category category = (Category) items.get(position);
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;

            categoryViewHolder.title.setText(category.getName());
            categoryViewHolder.image.setImageResource(images[position]);
            categoryViewHolder.image.setVisibility(View.VISIBLE);

            categoryViewHolder.image.setOnClickListener(v -> listener.onClickCategoryListener(category));

        } else {

            Product product = (Product) items.get(position);
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;

            productViewHolder.title.setText(product.getName());
            productViewHolder.image.setImageResource(images[position]);
            productViewHolder.image.setVisibility(View.VISIBLE);
            productViewHolder.description.setText(product.getDescription());
            productViewHolder.price.setText(String.format(Locale.FRANCE, "%.2f €", product.getPrice()));
            productViewHolder.addToCart.setOnClickListener(v -> listener.onClickCategoryListener(product));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof Category ? ITEM_TYPE_CATEGORY : ITEM_TYPE_PRODUCT;
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        View view;

        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            view = itemView;
            title = itemView.findViewById(R.id.category_title);
            image = itemView.findViewById(R.id.category_image);

        }

        public void setImage(int url) {
            image = itemView.findViewById(R.id.category_image);
            //Glide.with(context).load(R.string.url_spring + "img/" + url).into(image);
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView description;
        View view;
        ScrollView scrollView;
        TextView price;
        MaterialButton addToCart;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            view = itemView;
            title = itemView.findViewById(R.id.product_title);
            description = itemView.findViewById(R.id.product_description);
            image = itemView.findViewById(R.id.product_image);
            scrollView = itemView.findViewById(R.id.product_scrollview);
            price = itemView.findViewById(R.id.product_price);
            addToCart = itemView.findViewById(R.id.addToCart);

            initEvents();
        }

        @SuppressLint("ClickableViewAccessibility")
        private void initEvents() {
            scrollView.setOnTouchListener((v, event) -> {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });

        }
    }
}

