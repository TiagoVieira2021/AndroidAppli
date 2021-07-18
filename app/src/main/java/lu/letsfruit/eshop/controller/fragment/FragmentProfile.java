package lu.letsfruit.eshop.controller.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import lu.letsfruit.eshop.Model.Image;
import lu.letsfruit.eshop.Model.User;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.service.UserService;
import lu.letsfruit.eshop.utils.FileUtils;
import lu.letsfruit.eshop.utils.RequestManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener {

    private View view;

    private SwitchMaterial switchMaterial;
    private LinearLayout changePwLayout;
    private TextInputEditText firstname, lastname, email, password, passwordConfirm;
    private MaterialButton modify;
    private Button photoNotSelected;
    private ImageView profilePhoto, addPhoto;
    private MaterialCardView cardPhoto;

    private final User user = new User();

    private static final int PERMISSION_REQUEST_CODE = 200;

    ActivityResultLauncher<Intent> selectImageActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        UserService.getInstance().getConnectedUser(
                requireActivity(),
                this::setUserData
        );

        initViews();
        initEvents();

        selectImageActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result != null && result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        String base64 = "";
                        try {
                            base64 = FileUtils.fromUriToBase64(requireActivity(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        user.setImage(new Image(base64));
                        profilePhoto.setImageURI(selectedImage);
                        cardPhoto.setVisibility(View.VISIBLE);
                        photoNotSelected.setVisibility(View.GONE);
                    }
                });

    }

    private void setUserData(User user) {
        this.user.setId(user.getId());
        firstname.setText(user.getFirstname());
        lastname.setText(user.getLastname());
        email.setText(user.getLogin());
    }

    private void initEvents() {
        switchMaterial.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        modify.setOnClickListener(this);
    }

    private void initViews() {
        switchMaterial = view.findViewById(R.id.profile_switch);
        changePwLayout = view.findViewById(R.id.profile_change_pw);
        firstname = view.findViewById(R.id.profile_edit_firstname);
        lastname = view.findViewById(R.id.profile_edit_lastname);
        email = view.findViewById(R.id.profile_edit_email);
        password = view.findViewById(R.id.profile_edit_password);
        passwordConfirm = view.findViewById(R.id.profile_edit_password_confirm);
        addPhoto = view.findViewById(R.id.imageView_profile_photo);
        modify = view.findViewById(R.id.profile_modify_btn);
        profilePhoto = view.findViewById(R.id.profile_selected_image);
        photoNotSelected = view.findViewById(R.id.profile_not_selected);
        cardPhoto = view.findViewById(R.id.profile_card_photo);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.profile_switch) {
            if (changePwLayout.getVisibility() == View.GONE) {
                changePwLayout.setVisibility(View.VISIBLE);
            } else {
                changePwLayout.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.imageView_profile_photo) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            selectImageActivity.launch(gallery);
        } else if (v.getId() == R.id.profile_modify_btn) {
            prepareUserData();
        }
    }

    private void prepareUserData() {
        user.setFirstname(Objects.requireNonNull(firstname.getText()).toString());
        user.setLastname(Objects.requireNonNull(lastname.getText()).toString());
        user.setLogin(Objects.requireNonNull(email.getText()).toString());
        user.setPassword(Objects.requireNonNull(password.getText()).toString());
        user.setConfirmPassword(Objects.requireNonNull(passwordConfirm.getText()).toString());


        boolean error = false;

        if (switchMaterial.isChecked()) {
            if (user.getPassword() == null || user.getConfirmPassword() == null) {
                showSnackBar(false, "Entrez le nouveau mot de passe");
                error = true;
            } else if (!user.getConfirmPassword().equals(user.getPassword())) {
                showSnackBar(false, "Les mots de passe ne sont pas identiques");
                error = true;
            } else if (user.getPassword().length() < 6) {
                showSnackBar(false, "Mot de passe doit contenir au minimum 6 caractères");
                error = true;
            }
        }

        if (!error) {
            UserService.getInstance().updateProfile(
                    requireActivity(),
                    user,
                    user -> {
                        password.setText("");
                        passwordConfirm.setText("");

                        showSnackBar(true, "Profile mis à jour");
                    }
            );

        }

    }

    private void showSnackBar(boolean success, String message) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);


        if (success) {
            snack.setBackgroundTint(getResources().getColor(R.color.purple_500));
        } else {
            snack.setBackgroundTint(getResources().getColor(R.color.error));
        }

        TextView tv = snack.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snack.show();
    }
}