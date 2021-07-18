package lu.letsfruit.eshop.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import lu.letsfruit.eshop.Model.Cart;
import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.Model.ShopAdapterItem;
import lu.letsfruit.eshop.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> cartList;
    private final DeleteListener listener;

    public interface DeleteListener {
        void deleteListener(Cart cart);
    }

    public CartAdapter(Context context, List<Cart> cartList, DeleteListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cart_product_row, parent, false);
        return new CartAdapter.ViewHolder(inflater);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.title.setText(cart.getProduct().getName());
        holder.quantity.setText(cart.getQuantity() + " pièces");
        holder.price.setText(cart.getQuantity() * cart.getProduct().getPrice() + "€");

        holder.delete.setOnClickListener(c -> {
            listener.deleteListener(cart);
            cartList.remove(position);
            this.notifyItemRemoved(position);
            this.notifyItemRangeChanged(position, cartList.size());
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView quantity;
        TextView price;
        MaterialButton delete;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cart_product_title);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            price = itemView.findViewById(R.id.cart_product_price);
            delete = itemView.findViewById(R.id.cart_delete);


        }
    }
}
