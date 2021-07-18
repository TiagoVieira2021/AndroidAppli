package lu.letsfruit.eshop.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.R;

public class ShopTopMenuAdapter extends RecyclerView.Adapter<ShopTopMenuAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> categories;
    private final ClickListener listener;

    public interface ClickListener {
        void onClickCategoryListener(Category category);
    }

    public ShopTopMenuAdapter(Context context, List<Category> categories, ClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_shop_top_menu_item, parent, false);
        return new ShopTopMenuAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.title.setText(category.getName());

        holder.title.setOnClickListener((View view) -> {
            listener.onClickCategoryListener(category);
        });

        holder.title.setOnLongClickListener((View view) -> {
            Toast.makeText(context, "Long one", Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.topMenu);
        }
    }
}
